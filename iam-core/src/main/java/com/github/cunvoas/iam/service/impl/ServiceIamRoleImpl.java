package com.github.cunvoas.iam.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRole;
import com.github.cunvoas.iam.exception.IamException;
import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.entity.Role;
import com.github.cunvoas.iam.persistance.repositories.RessourceValeurRepository;
import com.github.cunvoas.iam.persistance.repositories.RoleRepository;
import com.github.cunvoas.iam.service.ServiceIamManagement;
import com.github.cunvoas.iam.service.ServiceIamRole;
import com.github.cunvoas.iam.service.mapper.MapperHelper;

/**
 * @author Cunvoas
 */
@Service("ServiceIamRole")
public class ServiceIamRoleImpl implements ServiceIamRole {
    
    @Resource
    private RoleRepository roleRepository;
    
    @Autowired
    private ServiceIamManagement serviceIamManagement;

    @Resource
    private RessourceValeurRepository ressourceValeurRepository;

    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRole#findById(java.lang.Integer)
     */
    @Override
    public IamRole findById(Integer id) {
        Role role = roleRepository.findOne(id);
        return     MapperHelper.map(role);
    }

    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRole#findByApplication(com.github.cunvoas.iam.persistance.entity.Application)
     */
    @Override
    public List<IamRole> findByApplication(IamApplication iamApplication) {
        Application application = new Application();
        application.setId(iamApplication.getId());
        
        List<Role> roles = roleRepository.findByApplication(application);

        List<IamRole> iamRoles = new ArrayList<IamRole>();
        for (Role role : roles) {
            iamRoles.add(MapperHelper.map(role));
        }
        return iamRoles;
    }

    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRole#save(com.github.cunvoas.iam.bo.IamRole, com.github.cunvoas.iam.bo.IamApplication)
     */
    @Override
    public IamRole save(IamRole iamRole, IamApplication applicaton) {
        
        Role role = null;
        if (iamRole.getId()!=null) {
            // update
            role = roleRepository.findOne(iamRole.getId());
            
            if (!role.getCode().equals(iamRole.getCode())) {
                // change code ?
                
                Role roleAlreadyExists = roleRepository.findRoleByApplicationAndCode(applicaton.getId(), iamRole.getCode());
                if (roleAlreadyExists!=null) {
                    // one role with the same code already exists
                    throw new IamException("RULE_02_003");
                }
            }
            
        } else {
            Role roleAlreadyExists = roleRepository.findRoleByApplicationAndCode(applicaton.getId(), iamRole.getCode());
            if (roleAlreadyExists!=null) {
                // one role with the same code already exists
                throw new IamException("RULE_02_002");
            }
        }
        role = MapperHelper.merge(iamRole, role, applicaton);
        role = roleRepository.save(role);
        
        return MapperHelper.map(role);
    }

    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRole#delete(java.lang.Integer)
     */
    @Override
    public void delete(Integer id) {
        ressourceValeurRepository.deleteByRoleId(id);
        roleRepository.delete(id);
    }

    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRole#search(java.lang.String, java.lang.String)
     */
    @Override
    public List<IamRole> search(String codeOrDescription, String codeApplication) {
        List<IamRole> ret = new ArrayList<IamRole>();
        if (StringUtils.isNotEmpty(codeOrDescription) && codeOrDescription.length()>=serviceIamManagement.getMinimumStringLengthForSearch()) {
            List<Role> roles = roleRepository.searchRole(codeOrDescription.toUpperCase(), codeApplication);
            for (Role role : roles) {
                ret.add(MapperHelper.map(role));
            }
        }
        return ret;
    }

    
}
