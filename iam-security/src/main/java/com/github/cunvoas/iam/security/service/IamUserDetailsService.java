package com.github.cunvoas.iam.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.github.cunvoas.iam.bo.IamRawUser;
import com.github.cunvoas.iam.security.authority.IamUserDetails;
import com.github.cunvoas.iam.service.ServiceVectorIam;

/**
 * UserDetailsService IAM Implementation.
 * @author CUNVOAS
 */
@Component("iamUserDetailsService")
@DependsOn("ServiceVectorIam")    // force la dépendance car mix Annotation / fichier XML
public class IamUserDetailsService implements UserDetailsService {
    
    static {
        javax.net.ssl.HostnameVerifier verifier =  new javax.net.ssl.HostnameVerifier() {
            public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
                if (hostname.equals("localhost")) {
                    return true;
                }
                return false;
            }
        };
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(verifier);
    }
    
    @Autowired
    @Qualifier("ServiceVectorIam")
    private ServiceVectorIam serviceVectorIam;

    @Value("${iamCodeApplication}")
    private String applicationCode;

    /**
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IamUserDetails userDetail = null;
        
        IamRawUser user = serviceVectorIam.findIamRawVector(username, applicationCode);
        if (user!=null ) {
            userDetail = new IamUserDetails(user);
        } else {
            throw new UsernameNotFoundException(username);
        }
        
        return userDetail;
    }

}
