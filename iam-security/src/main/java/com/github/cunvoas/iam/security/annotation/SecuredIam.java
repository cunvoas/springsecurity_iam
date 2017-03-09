package com.github.cunvoas.iam.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author CUNVOAS
 * @see org.springframework.security.access.annotation.Secured
 */
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SecuredIam {
    
    
    /**
     * 
     * @return
     */
    public String vectorKey();

    public String vectorValue();
}
