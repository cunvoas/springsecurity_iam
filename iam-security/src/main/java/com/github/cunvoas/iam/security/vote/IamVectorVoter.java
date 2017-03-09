package com.github.cunvoas.iam.security.vote;

import java.util.Collection;

import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import com.github.cunvoas.iam.security.helper.IamHelper;
import com.github.cunvoas.iam.security.helper.IamSecurityHelper;
import com.github.cunvoas.iam.security.annotation.IamConfigAttribute;

/**
 * The voter to decide access for SecuredIam annotation.
 * @author CUNVOAS 
 */
public class IamVectorVoter implements AccessDecisionVoter {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IamVectorVoter.class);

    private String supportCode = "IAM_POINTCUT";

    /**
     * @see rg.springframework.security.access.AccessDecisionVoter#supports(org.springframework.security.access.ConfigAttribute)
     */
    public boolean supports(ConfigAttribute attribute) {
        boolean ret=false;
        if ((attribute.getAttribute() != null)
                && supportCode.equals(attribute.getAttribute())) {
            ret = true;
            LOGGER.debug("ConfigAttribute={}", attribute.getAttribute());
        }
        return ret;
    }

    /**
     * @see org.springframework.security.access.AccessDecisionVoter#vote(org.springframework.security.core.Authentication, java.lang.Object, java.util.Collection)
     */
    public int vote(Authentication authentication, Object object, Collection attributes) {
        int access = ACCESS_DENIED;
        for (Object attribute : attributes) {
            if (attribute instanceof IamConfigAttribute) {
                IamConfigAttribute iamConfigAttribute = (IamConfigAttribute)attribute;
                
                Integer authenticationLevel = IamSecurityHelper.getRoleSecurityLevel(iamConfigAttribute.getVectorKey());
                String securityRequired = iamConfigAttribute.getVectorValue();
                
                if ( IamHelper.isSufficent(authenticationLevel, securityRequired) ) {
                    access = ACCESS_GRANTED;
                }
            }
        }
        
        return access;
    }

    /**
     * @see org.springframework.security.access.AccessDecisionVoter#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
        return true;
    }

}
