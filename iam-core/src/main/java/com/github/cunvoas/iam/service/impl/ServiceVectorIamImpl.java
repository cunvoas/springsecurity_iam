package com.github.cunvoas.iam.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRawDelegate;
import com.github.cunvoas.iam.bo.IamRawRessource;
import com.github.cunvoas.iam.bo.IamRawRole;
import com.github.cunvoas.iam.bo.IamRawUser;
import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.bo.IamRole;
import com.github.cunvoas.iam.bo.delegation.IamDelegation;
import com.github.cunvoas.iam.ldap.IamLdapService;
import com.github.cunvoas.iam.ldap.IamLdapUser;
import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.entity.Role;
import com.github.cunvoas.iam.persistance.repositories.ApplicationRepository;
import com.github.cunvoas.iam.persistance.repositories.RoleRepository;
import com.github.cunvoas.iam.service.DashBoardMonitor;
import com.github.cunvoas.iam.service.ServiceIamDelegation;
import com.github.cunvoas.iam.service.ServiceIamRessource;
import com.github.cunvoas.iam.service.ServiceVectorIam;
import com.github.cunvoas.iam.service.mapper.MapperHelper;

/**
 * Service dédié à la consultation des vecteurs.
 * 
 * @author CUNVOAS
 */
@Service("ServiceVectorIam")
@DependsOn(value= {"ServiceIamRessource"})
public class ServiceVectorIamImpl implements ServiceVectorIam {
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceVectorIamImpl.class);

    @Resource
    private ApplicationRepository applicationRepository;

    @Resource
    private RoleRepository roleRepository;

    @Autowired
    private ServiceIamRessource serviceIamRessource;
    
    @Autowired
    private ServiceIamDelegation serviceIamDelegation;

    @Autowired
    private IamLdapService iamLdapService;
    
    @Autowired
    private DashBoardMonitor dashBoardMonitor;


    /**
     * @see com.github.cunvoas.iam.service.ServiceVectorIam#findIamRessourceByRole(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public IamRessource findIamRessourceByRole(String userId, String applicationCode, String roleCode) {
        IamRessource ressource = null;
        LOGGER.info("call findIamRessource code={}, user={}, role={}", applicationCode, userId, roleCode);
        Application application = applicationRepository.findByCode(applicationCode);
        
        IamApplication iamApplication = MapperHelper.map(application);
        if (application!=null) {
            Role role = roleRepository.findRoleByApplicationAndCode(application.getId(), roleCode);
            IamRole iamRole = MapperHelper.map(role);
            ressource = serviceIamRessource.getRessourceValueTree(iamApplication, iamRole);
        }
        return ressource;
    }

    /**
     * find the user roles for the application.
     * @param ldapUser
     * @param application
     * @return
     */
    private List<IamRole> findIamRessource(IamLdapUser ldapUser, Application application) {
        List<IamRole> iamRoles = null;
        if (application!=null && ldapUser!=null) {
            
            IamApplication iamApplication = MapperHelper.map(application);
            
            iamRoles = new ArrayList<IamRole>();
            
            List<String> ldapCodeRoles = new ArrayList<>();
            for (String roleLdap : ldapUser.getRolesList()) {
                if (roleLdap.startsWith(application.getCode())) {
                    // implementation based in LDAP on APP:ROLE
                    ldapCodeRoles.add(roleLdap.replaceAll(application.getCode()+":", ""));
                }
            }
            
            List<Role> appRoles = roleRepository.findByApplication(application);
            
            // filter on user roles
            List<Role> userRoles = new ArrayList<>() ;
            for (Role role : appRoles) {
                if (ldapCodeRoles.contains(role.getCode())) {
                    userRoles.add(role);
                }
            }
            
            // populate BO
            for (Role role : userRoles) {
                IamRole iamRole = MapperHelper.map(role);
                IamRessource ressource = serviceIamRessource.getRessourceValueTree(iamApplication, iamRole);
                iamRole.setRessource(ressource);
                iamRoles.add(iamRole);
            }
        }
        return iamRoles;
    }
    /**
     * @see com.github.cunvoas.iam.service.ServiceVectorIam#findIamVector(java.lang.String, java.lang.String)
     */
    public List<IamRole> findIamRessource(String userId, String rqApplication) {
        
        
        LOGGER.info("call findIamRessource code={}, user={}", rqApplication, userId);

        List<IamRole> iamRoles = null;
        
        Application application = applicationRepository.findByCode(rqApplication);
        if (application!=null) {
            // do the LDAP call only if application exists
            IamLdapUser ldapUser = iamLdapService.find(userId, rqApplication);
            
            if (ldapUser!=null) {
                // get roles defined in LDAP for this application
                iamRoles = findIamRessource(ldapUser, application);
                
            } //ldapUser
        } // application

        return iamRoles;
    }

    
    /**
     * @see com.github.cunvoas.iam.service.ServiceVectorIam#findIamRawVector(java.lang.String, java.lang.String)
     */
    @Override
    public IamRawUser findIamRawVector(String userId, String applicationCode) {
        long timer=0L;
        if (LOGGER.isInfoEnabled()) {
            timer = System.nanoTime();
            LOGGER.info("call findIamRawVector code={}, user={}", applicationCode, userId);
        }
        
        IamRawUser user = new IamRawUser();
        
        Application application = applicationRepository.findByCode(applicationCode);
        if (application!=null) {
            // do the LDAP call only if application exists
            IamLdapUser ldapUser = iamLdapService.find(userId, applicationCode);
            
            if (ldapUser!=null) {
                boolean nonExpired = !ldapUser.isActive();
                
                //FIXME : arrival and departure not present in ApacheDS
                nonExpired = true;
                
                user.setAccountNonExpired(nonExpired);
                user.setAccountNonLocked(nonExpired);
                user.setCredentialsNonExpired(nonExpired);
                user.setEnabled(nonExpired);
                user.setUsername(ldapUser.getUid());
                user.setFirstname(ldapUser.getFirstName());
                user.setLastname(ldapUser.getLastName());
                
                List<IamRole> roles = findIamRessource(ldapUser, application);
                populateRawRole(user, roles, null);
                
                // Delegation 
                List<IamDelegation> delegations = serviceIamDelegation.findDelegatedForVector(userId);
                if (CollectionUtils.isNotEmpty(delegations)) {
                	for (IamDelegation iamDelegation : delegations) {
// delegations.clear()
// FIXME               		populateRawRole(user, iamDelegation.getRoles(), iamDelegation);
					}
                }
                
            } else {
                dashBoardMonitor.incrementIamVectorRefused();
            }
        } else {
            dashBoardMonitor.incrementIamVectorRefused();
        }
        
        
        if (LOGGER.isInfoEnabled()) {
            timer =   Double.valueOf((System.nanoTime()-timer)/1E3).longValue(); 
            LOGGER.info("call findIamRawVector code={}, user={}, duration={} µs", applicationCode, userId, timer);
        }
        return user;
    }
    
    /**
     * Population des roles.
     * @param user
     * @param roles
     * @param delegation
     */
    private void populateRawRole(final IamRawUser user, final List<IamRole> roles, IamDelegation delegation) {
    	if (CollectionUtils.isNotEmpty(roles)) {
            for (IamRole iamRole : roles) {
                IamRawRole role = new IamRawRole(iamRole);
                if (delegation!=null) {
                	IamRawDelegate rawDelegate = new IamRawDelegate();
                	rawDelegate.setDelegateId(delegation.getId());
                	rawDelegate.setDelegateUid(delegation.getUidDelegator());
                	rawDelegate.setEndDate(delegation.getEnd());
                	IamLdapUser ldapDelegator = iamLdapService.find(delegation.getUidDelegator());
                	rawDelegate.setDelegateDescription(ldapDelegator.getFullName());
                	role.setDelegation(rawDelegate);
                }
                
                List<IamRessource> iamRessources = MapperHelper.asList(iamRole.getRessource());
                for (IamRessource iamRessource : iamRessources) {
                    if (!iamRessource.isNode()) {
                        role.addRessources(new IamRawRessource(iamRessource));
                    }
                }
                
                user.getRoles().add(role);
            }
            dashBoardMonitor.incrementIamVextor(user.getUsername());
        } else {
            dashBoardMonitor.incrementIamVectorRefused();
        }
    }


    /**
     * @see com.github.cunvoas.iam.service.ServiceVectorIam#findForConstants(java.lang.String)
     */
    @Override
    public List<IamRessource> findForConstants(String applicationCode) {
        return serviceIamRessource.findRessourceLeaf(applicationCode);
    }


    /**
     * Setter for serviceIamRessource.
     * @param serviceIamRessource the serviceIamRessource to set
     */
    public void setServiceIamRessource(ServiceIamRessource serviceIamRessource) {
        this.serviceIamRessource = serviceIamRessource;
    }


}
