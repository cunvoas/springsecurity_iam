package com.github.cunvoas.iam.ldap.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.stereotype.Repository;

import com.github.cunvoas.iam.ldap.IamLdapService;
import com.github.cunvoas.iam.ldap.IamLdapUser;
import com.github.cunvoas.iam.ldap.mapper.IamLdapUserMapper;

/**
 * LDAP Service. 
 * @author CUNVOAS
 */
@Repository
public class IamLdapServiceImpl implements IamLdapService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IamLdapServiceImpl.class);
    
    @Autowired
    private LdapTemplate ldapTemplate;
    
    @Value("${ldap.people.findquery}")
    private String findPeopleQuery;
    
    /**
     * @see com.github.cunvoas.iam.ldap.IamLdapService#find(java.lang.String)
     */
    @Override
    public IamLdapUser find(String uid) {
        IamLdapUser ret = (IamLdapUser)ldapTemplate.lookup(String.format(findPeopleQuery, uid), new IamLdapUserMapper());
        return ret;
    }

    /**
     * @see com.github.cunvoas.iam.ldap.IamLdapService#find(java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<IamLdapUser> find(String firstname, String lastname, String appCode) {
        
        Filter searchFilter = null;
        Filter nameFilter = null;
        LikeFilter fNameFilter = null;
        LikeFilter lNameFilter = null;
        if (!StringUtils.isEmpty(firstname)) {
            LOGGER.debug("firstname={}", firstname);
            fNameFilter = new LikeFilter(IamLdapUserMapper.PROP_FIRSTNAME, firstname.trim());
            nameFilter = fNameFilter;
            
            searchFilter = nameFilter;
        }
        if (!StringUtils.isEmpty(lastname)) {
            LOGGER.debug("lastname={}", lastname);
            lNameFilter = new LikeFilter(IamLdapUserMapper.PROP_LASTNAME, lastname.trim());
            
            nameFilter = lNameFilter;
            searchFilter = nameFilter;
        }
        
        if (fNameFilter!=null && lNameFilter!=null) {
            LOGGER.debug("NAME {} OR {}", firstname, lastname);
            OrFilter orFilter = new OrFilter();
            orFilter.or(fNameFilter).or(lNameFilter);
            
            nameFilter = orFilter;
            searchFilter = nameFilter;
        }
        
        LikeFilter appFilter = null;
        if (!StringUtils.isEmpty(appCode)) {
            LOGGER.debug("appCode={}", appCode);
            appFilter = new LikeFilter(IamLdapUserMapper.PROP_ROLES, appCode.trim()+"*");
            if (searchFilter==null) {
                searchFilter = appFilter;
            }
        }
        
        if (nameFilter!=null && appFilter!=null) {
            LOGGER.debug("({} OR {}) AND {}", new Object[] {firstname, lastname, appCode});
            AndFilter andFilter = new AndFilter();
            andFilter.and(nameFilter).and(appFilter);
            
            searchFilter = andFilter;
        }
        
        List<IamLdapUser> users = null;
        if (searchFilter!=null) {
            users  = (List<IamLdapUser>) ldapTemplate.search("ou=people", searchFilter.encode(), new IamLdapUserMapper());
        }
        return users;
    }

    /**
     * @see com.github.cunvoas.iam.ldap.IamLdapService#find(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public IamLdapUser find(String uid, String appCode) {
        IamLdapUser user = null;
        if (StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(appCode) ) {
        
            EqualsFilter uidFilter = new EqualsFilter(IamLdapUserMapper.PROP_UID, uid);
            LikeFilter appFilter = new LikeFilter(IamLdapUserMapper.PROP_ROLES, appCode.trim()+"*");
            AndFilter andFilter = new AndFilter();
            andFilter.and(uidFilter).and(appFilter);
            
            List<IamLdapUser> users = null;
            users  = (List<IamLdapUser>) ldapTemplate.search("ou=people", andFilter.encode(), new IamLdapUserMapper());
            
            
            if (users!=null && !users.isEmpty()) {
                user = users.get(0);
            }
        }
        return user;
    }
}
