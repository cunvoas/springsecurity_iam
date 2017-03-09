package com.github.cunvoas.iam.security.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;
import org.springframework.stereotype.Component;

/**
 * Extract Metadata Annotation sources.
 * @author CUNVOAS
 */
@Component("securedIamAnnotationSecurityMetadataSource")
public class SecuredIamAnnotationSecurityMetadataSource extends AbstractFallbackMethodSecurityMetadataSource {

    /**
     * @see org.springframework.security.access.SecurityMetadataSource#getAllConfigAttributes()
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * @see org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource#findAttributes(java.lang.reflect.Method, java.lang.Class)
     */
    @Override
    protected Collection<ConfigAttribute> findAttributes(Method method, Class<?> targetClass) {
        return processAnnotation(AnnotationUtils.findAnnotation(method, SecuredIam.class));
    }

    /**
     * @see org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource#findAttributes(java.lang.Class)
     */
    @Override
    protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
        return processAnnotation(clazz.getAnnotation(SecuredIam.class));
    }

    /**
     * Get annotation Config.
     * @param a
     * @return
     */
    private List<ConfigAttribute> processAnnotation(Annotation a) {
        if (!(a instanceof SecuredIam)) {
            return null;
        }

        String key = ((SecuredIam) a).vectorKey();
        String value = ((SecuredIam) a).vectorValue();
        List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>(1);

        attributes.add(new IamConfigAttribute(key, value));

        return attributes;
    }

}
