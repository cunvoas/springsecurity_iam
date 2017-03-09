package com.github.cunvoas.iam.security.jsp;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CUNVOAS
 * @see http://stackoverflow.com/questions/3732608/how-to-reference-constants-in-el
 */
public class JspConstants extends HashMap<String, String> {
    
    /** serialVersionUID. */
    private static final long serialVersionUID = 76564354565434789L;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JspConstants.class);

    /**
     * Constructor.
     */
    public JspConstants(String iamKeyClassName) {
        try {
            Field[] fields = null;
            Class c = Class.forName(iamKeyClassName);
            fields = c.getDeclaredFields();
            for(Field field : fields) {
                int modifier = field.getModifiers();
                if(Modifier.isPublic(modifier) && Modifier.isStatic(modifier) && Modifier.isFinal(modifier)) {
                    try {
                        put(field.getName(), (String)field.get(null));
                    } catch(IllegalAccessException ignored) {
                        LOGGER.debug("unable to put key :"+ field.getName());
                    }
                }
            }
            
            
            
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (SecurityException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * @see java.util.HashMap#get(java.lang.Object)
     */
    @Override
    public String get(Object key) {
        String result = super.get(key);
        if(result==null || "".equals(result.trim())) {
            throw new IllegalArgumentException("The key "+ key +" is wrong, no such constant!");
        }
        return result;
    }
}
