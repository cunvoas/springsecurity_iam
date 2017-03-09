/**
 * 
 */
package com.github.cunvoas.iam.ldap;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;

/**
 * @author CUNVOAS
 */
public class UserMapper implements ContextMapper {

      /**
       * @see org.springframework.ldap.core.ContextMapper#mapFromContext(java.lang.Object)
       */
      @Override
      public Object mapFromContext(Object ctx) {
        DirContextAdapter context = (DirContextAdapter) ctx;
        
        return context.getStringAttribute("privrole");
      }

}
