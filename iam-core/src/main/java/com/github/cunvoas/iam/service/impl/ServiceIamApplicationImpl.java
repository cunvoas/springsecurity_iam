package com.github.cunvoas.iam.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamDashboard;
import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.bo.IamRole;
import com.github.cunvoas.iam.exception.IamException;
import com.github.cunvoas.iam.exception.IamExceptionContants;
import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.entity.RessourceValeur;
import com.github.cunvoas.iam.persistance.entity.RessourceValeurPK;
import com.github.cunvoas.iam.persistance.entity.Role;
import com.github.cunvoas.iam.persistance.entity.Valeur;
import com.github.cunvoas.iam.persistance.repositories.ApplicationRepository;
import com.github.cunvoas.iam.persistance.repositories.RessourceRepository;
import com.github.cunvoas.iam.persistance.repositories.RessourceValeurRepository;
import com.github.cunvoas.iam.persistance.repositories.RoleRepository;
import com.github.cunvoas.iam.service.DashBoardMonitor;
import com.github.cunvoas.iam.service.ServiceIamApplication;
import com.github.cunvoas.iam.service.ServiceIamManagement;
import com.github.cunvoas.iam.service.ServiceIamRessource;
import com.github.cunvoas.iam.service.mapper.MapperHelper;

/**
 * Service implemntation.
 * @author CUNVOAS
 */
@Service("ServiceIamApplication")
@DependsOn(value= {"ServiceIamRessource"})
public class ServiceIamApplicationImpl implements ServiceIamApplication {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceIamApplicationImpl.class);
    
    @Resource
    private ApplicationRepository applicationRepository;

    @Resource
    private RessourceValeurRepository ressourceValeurRepository;
    
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private RessourceRepository ressourceRepository;
    
    @Autowired
    private ServiceIamRessource serviceIamRessource;
    
    @Autowired
    private ServiceIamManagement serviceIamManagement;
    
    @Autowired
    private DashBoardMonitor dashBoardMonitor;
    

    /**
     * @see com.github.cunvoas.iam.service.ServiceIamApplication#findForExport(java.lang.String)
     */
    @Override
    public IamApplication findForExport(String codeApplication) {
        
        Application application = applicationRepository.findByCode(codeApplication);
        IamApplication iamApp = MapperHelper.map(application);
        if (iamApp!=null) {
            IamRessource iamRessource = serviceIamRessource.findResources(codeApplication);
            iamApp.setRessources(iamRessource);
            
            List<Role> roles = roleRepository.findByApplication(application);
            List<IamRole> iamRoles = new ArrayList<IamRole>();
            for (Role role : roles) {
                IamRole iamRole = MapperHelper.map(role);
                IamRessource ressAppRoleIamRessource = serviceIamRessource.getRessourceValueTree(iamApp, iamRole);
                iamRole.setRessource(ressAppRoleIamRessource);
                iamRole.setRessourceValues(new HashMap<String, String>());
                List<IamRessource> listResRoleApp = MapperHelper.asList(ressAppRoleIamRessource);
                for (IamRessource resRolApp : listResRoleApp) {
                    if (!resRolApp.isNode()) {
                        iamRole.getRessourceValues().put(resRolApp.getVectorCode(), String.valueOf(resRolApp.getValeur()));
                    }
                }
                
                
                iamRoles.add(iamRole);
            }
            iamApp.setRoles(iamRoles);
            
        }
        return iamApp;
    }


    /**
     * @see com.github.cunvoas.iam.service.ServiceIamApplication#findByCode(java.lang.String)
     */
    @Override
    public IamApplication findByCode(String codeApplication) {
        Application application = applicationRepository.findByCode(codeApplication);
        IamApplication iamApp = MapperHelper.map(application);
        return iamApp;
    }

    /**
     * @see com.github.cunvoas.iam.service.ServiceIamApplication#findAll()
     */
    @Override
    public List<IamApplication> findAll() {
        List<IamApplication> iamApps=new ArrayList<IamApplication>();
         List<Application> applications = applicationRepository.findAll();
         
         for (Application application : applications) {
             IamApplication iamApp = MapperHelper.map(application);
             iamApps.add(iamApp);
        }
        
        return  iamApps;
    }


    /**
     * @see com.github.cunvoas.iam.service.ServiceIamApplication#save(com.github.cunvoas.iam.bo.IamApplication)
     */
    @Override
    public IamApplication save(IamApplication iamApplication) {
        boolean changeResourceRoot = false;
        boolean createResourceRoot = false;
        Application application = null;
        if (iamApplication.getId()!=null) {
            application = applicationRepository.findOne(iamApplication.getId());
            if (!application.getCode().equals(iamApplication.getCode())) {
                Application applicationCode = applicationRepository.findByCode(iamApplication.getCode());
                if (applicationCode!=null) {
                    // another app with same code exists
                    throw new IamException("RULE_01_003");
                }
                
                // need to change de root resource
                changeResourceRoot=true;
            }
        } else {
            Application applicationCode = applicationRepository.findByCode(iamApplication.getCode());
            if (applicationCode!=null) {
                // another app with same code exists
                throw new IamException("RULE_01_002");
            }
            createResourceRoot=true;
        }
        
        LOGGER.debug("changeResourceRoot={}", changeResourceRoot);
        
        application = MapperHelper.merge(iamApplication, application);
        application = applicationRepository.save(application);
        IamApplication iamApp = MapperHelper.map(application);
        
        if (changeResourceRoot) {
            
            IamRessource resourceRoot = serviceIamRessource.findResources(iamApp.getCode());
            resourceRoot.setCode(iamApp.getCode());
            serviceIamRessource.saveRoot(resourceRoot, iamApp.getCode());
            
        } else if (createResourceRoot) {
            IamRessource resourceRoot = new IamRessource();
            resourceRoot.setCode(iamApp.getCode());
            serviceIamRessource.saveRoot(resourceRoot, iamApp.getCode());
        }
        return iamApp;
    }
    
    /**
     * @see com.github.cunvoas.iam.service.ServiceIamApplication#saveUpload(com.github.cunvoas.iam.bo.IamApplication)
     */
    @Override
    public void saveUpload(IamApplication iamApplication) {
        Application dbApp = applicationRepository.findByCode(iamApplication.getCode());
        
        if (dbApp!=null && dbApp.getId()!=null) {
            
            // clear existing ressourceRoleValues
            List<Role> roles = roleRepository.findByApplication(dbApp);
            if (!roles.isEmpty()) {
                for (Role role : roles) {
                    ressourceValeurRepository.deleteByRoleId(role.getId());
                }
                
                // clear existing roles
                roleRepository.deleteByApplicationId(dbApp.getId());
            }
            
            // clear existing ressource
            ressourceRepository.deleteByApplicationId(dbApp.getId());
        }
        
        dbApp = MapperHelper.merge(iamApplication, dbApp);
        dbApp = applicationRepository.save(dbApp);
        iamApplication.setId(dbApp.getId());
        
        // save new roles
        for (IamRole iamRole : iamApplication.getRoles()) {
            Role bdRole = roleRepository.findRoleByApplicationAndCode(dbApp.getId(), iamRole.getCode());
            bdRole = MapperHelper.merge(iamRole, bdRole, iamApplication);
            bdRole.setApplication(dbApp);
            bdRole = roleRepository.save(bdRole);
            iamRole.setId(bdRole.getId());
        }
        
        // save new resources
        IamRessource dbRess = serviceIamRessource.saveRoot(iamApplication.getRessources(), iamApplication.getCode());
        List<IamRessource> dbIamResList = MapperHelper.asList(dbRess);
        Collections.sort(dbIamResList, IamRessourceByVectorComparator.INSTANCE);
        
        List<RessourceValeur> resValList = new ArrayList<>();
        // save new resourceValues
        for (IamRole iamRole : iamApplication.getRoles()) {
            // Map VectorCode/value
            Map<String, String> values = iamRole.getRessourceValues();
            for (Iterator<Entry<String, String>> iterator = values.entrySet().iterator(); iterator.hasNext();) {
                Entry<String, String> entry = iterator.next();
                String vectorCode = entry.getKey();
                Integer vectorValue = Integer.valueOf(entry.getValue());
                
                IamRessource dbIamRess =  searchIamResourceByVectorCode(vectorCode, dbIamResList);
                
                RessourceValeurPK id = new RessourceValeurPK();
                RessourceValeur resval = null;
            
                if (dbIamRess!=null) {
                    id.setRolId(iamRole.getId());
                    id.setResId(dbIamRess.getId());
                    resval = ressourceValeurRepository.findById(id);
                    if (resval==null) {
                        resval = new RessourceValeur();
                        resval.setId(id);
                    }
                    resval.setValue(new Valeur(vectorValue));
                    // add to list for saving in one repo access
                    resValList.add(resval);
                    
                } else {
                    // the ressource must exist in persisance layer
                    throw new IamException(IamExceptionContants.ERR_RESSOUCE_NOT_EXISTS);
                }
            }
            
        }
        
        if (!resValList.isEmpty()) {
            ressourceValeurRepository.save(resValList);
        }
    }


    /**
     * @see com.github.cunvoas.iam.service.ServiceIamApplication#search(java.lang.String)
     */
    @Override
    public List<IamApplication> search(String codeOrDescription) {
        List<IamApplication> retApplications = new ArrayList<IamApplication>();
        if (StringUtils.isNotEmpty(codeOrDescription) && codeOrDescription.length()>=serviceIamManagement.getMinimumStringLengthForSearch()) {
            List<Application> apps = applicationRepository.searchApplication(codeOrDescription.toUpperCase());
            for (Application application : apps) {
                retApplications.add(MapperHelper.map(application));
            }
        }
        return retApplications;
    }


    /**
     * @see com.github.cunvoas.iam.service.ServiceIamApplication#findById(java.lang.Integer)
     */
    @Override
    public IamApplication findById(Integer idApplication) {
        Application application = applicationRepository.findOne(idApplication);
        IamApplication iamApp = MapperHelper.map(application);
        return iamApp;
    }

    /**
     * @see com.github.cunvoas.iam.service.ServiceIamApplication#getIamDashboard()
     */
    @Override
    public IamDashboard getIamDashboard() {
        IamDashboard dashboard = dashBoardMonitor.getActivityOfDay();
        
        long cpt = applicationRepository.count();
        dashboard.setNbApplication(cpt);
        
        return dashboard;
    }


    /**
     * Comparator for fast list search.
     * @author cunvoas
     */
    static class IamRessourceByVectorComparator implements Comparator<IamRessource> {
        public static final IamRessourceByVectorComparator INSTANCE = new IamRessourceByVectorComparator();
        
        /** 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(IamRessource o1, IamRessource o2) {
            CompareToBuilder ctb = new CompareToBuilder();
            ctb.append(o1.getVectorCode(), o2.getVectorCode());
            return ctb.toComparison();
        }
        
    }
    
    private static IamRessource searchIamResourceByVectorCode(String vectorCode, List<IamRessource> list) {
        IamRessource res =null;
        IamRessource key = new IamRessource(vectorCode);
        int idx = Collections.binarySearch(list, key, IamRessourceByVectorComparator.INSTANCE);
        if (idx>=0) {
            res = list.get(idx);
        }
        return res;
    }


    /**
     * @see com.github.cunvoas.iam.service.ServiceIamApplication#delete(java.lang.String)
     */
    @Override
    public void delete(String codeApplication) {
        Application application = applicationRepository.findByCode(codeApplication);
        
         if (application!=null) {
                
            // clear existing ressourceRoleValues
            List<Role> roles = roleRepository.findByApplication(application);
            if (!roles.isEmpty()) {
                for (Role role : roles) {
                    ressourceValeurRepository.deleteByRoleId(role.getId());
                }
                
                // clear existing roles
                roleRepository.deleteByApplicationId(application.getId());
            }
            
            // clear existing ressource
            ressourceRepository.deleteByApplicationId(application.getId());

            applicationRepository.delete(application);
        }
    }
    
}
