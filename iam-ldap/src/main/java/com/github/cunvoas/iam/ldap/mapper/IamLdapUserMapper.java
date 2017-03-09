package com.github.cunvoas.iam.ldap.mapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;

import com.github.cunvoas.iam.ldap.IamLdapUser;

/**
 * Map IamLdapUser from LDAP.
 * @author CUNVOAS
 * 
 * dn: uid=cunvoas,ou=people,dc=gie,dc=priv
 * objectClass: top
 * objectClass: person
 * objectClass: organizationalPerson
 * objectClass: inetOrgPerson
 * uid: cunvoas
 * initials: M.
 * sn: Unvoas
 * givenName: Christophe
 * mail: christophe.unvoas.ext@humanis.com
 * cn: Unvoas Christophe
 * privArrival: 20130805000000.000000Z
 * privDeparture: 20150101000000.000000Z
 * userPassword: {SSHA}**********************
 */
public class IamLdapUserMapper implements ContextMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(IamLdapUserMapper.class);

    public static final String PROP_UID="uid";
    public static final String PROP_INITIALS="initials";
    public static final String PROP_FIRSTNAME="givenName";
    public static final String PROP_LASTNAME="sn";
    public static final String PROP_FULLNAME="cn";
    public static final String PROP_MAIL="mail";
    public static final String PROP_ARRIVAL="privArrival";
    public static final String PROP_DEPARTURE="privDeparture";
    //FIXME selon structure LDAP
    public static final String PROP_ROLES="businessCategory";
    
    /**
     * @return
     */
    protected static DateFormat getSdf() {
        //                                             20150101000000.000000Z
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss'.000000Z'");
        return sdf;
    }

    
    
    /**
     * @see org.springframework.ldap.core.ContextMapper#mapFromContext(java.lang.Object)
     */
    @Override
    public Object mapFromContext(Object ctx) {
        IamLdapUser ret = null;
        DirContextAdapter ldapCtx = (DirContextAdapter) ctx;
        
        if (ldapCtx!=null) {
            
            String uid = ldapCtx.getStringAttribute(PROP_UID);
            ret = new IamLdapUser(uid);
            
            ret.setInitial(ldapCtx.getStringAttribute(PROP_INITIALS));
            ret.setFirstName(ldapCtx.getStringAttribute(PROP_FIRSTNAME));
            ret.setLastName(ldapCtx.getStringAttribute(PROP_LASTNAME));
            ret.setFullName(ldapCtx.getStringAttribute(PROP_FULLNAME));
            ret.setMail(ldapCtx.getStringAttribute(PROP_MAIL));
            
            String dt = ldapCtx.getStringAttribute(PROP_ARRIVAL);
            try {
                if (dt!=null) {
                    Date date = getSdf().parse(dt);
                    ret.setArrival(date);
                }
            } catch (ParseException pe) {
                LOGGER.debug("ARRIVAL {}", dt);
            }
            
            dt = ldapCtx.getStringAttribute(PROP_DEPARTURE);
            try {
                if (dt!=null) {
                    Date date = getSdf().parse(dt);
                    ret.setDeparture(date);
                }
            } catch (ParseException pe) {
                LOGGER.debug("DEPARTURE {}", dt);
            }

            String[] tRoles = ldapCtx.getStringAttributes(PROP_ROLES);
            if (tRoles!=null && tRoles.length>0) {
                ret.setRolesList(Arrays.asList(tRoles));
            }
        }
        
        return ret;
    }



}
