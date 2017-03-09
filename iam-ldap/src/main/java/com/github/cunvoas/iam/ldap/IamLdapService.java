package com.github.cunvoas.iam.ldap;

import java.util.List;

/**
 * LDAP Service Interface.
 * @author CUNVOAS
 */
public interface IamLdapService {

    /**
     * Find user in LDAP.
     * @param uid
     * @return
     */
    IamLdapUser find(String uid);
    
    IamLdapUser find(String uid, String appCode);
    
    /**
     * Find users in LDAP by partial name.
     * @param firstname like
     * @param lastname like
     * @param appCode
     * @return
     */
    List<IamLdapUser> find(String firstname, String lastname, String appCode);

}