package com.github.cunvoas.iam.security.evaluator;

import java.io.Serializable;

import javax.activity.InvalidActivityException;
import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.github.cunvoas.iam.security.helper.IamHelper;
import com.github.cunvoas.iam.security.helper.IamSecurityHelper;

/**
 * Evaluate the IAM Habilitation vector.
 * @author cunvoas
 * 
 * Inspired by
 * @see http://docs.spring.io/autorepo/docs/spring-security/3.1.4.RELEASE/reference/el-access.html
 * @see http://krams915.blogspot.fr/2011/01/spring-security-simple-acl-using.html
 * @see http://blog.novoj.net/2012/03/27/combining-custom-annotations-for-securing-methods-with-spring-security/
 * @see http://www.studytrails.com/frameworks/spring/spring-security-method-level.jsp
 * 
 */
@Component("iamPermissionEvaluator")
public class IamPermissionEvaluator implements PermissionEvaluator {
    

    @Value("${iamCodeApplication}")
    private String applicationCode;
    
    /**
     * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, java.lang.Object, java.lang.Object)
     */
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
         boolean hasPermission = false;
         
          if ( authentication != null &&  permission instanceof String && targetDomainObject instanceof String) {
              String iamVectorKey = (String) targetDomainObject;
              String securityRequired = (String)permission;
              
              Integer userSecurityLevel = IamSecurityHelper.getRoleSecurityLevel(iamVectorKey);
              
              hasPermission = IamHelper.isSufficent(userSecurityLevel, securityRequired);
         
          }
          return hasPermission;
    }

    /**
     * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, java.io.Serializable, java.lang.String, java.lang.Object)
     */
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new InvalidDataAccessApiUsageException("Id and Class permissions are not supperted by this application");
    }

}
