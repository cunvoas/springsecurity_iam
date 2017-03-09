/**
 * 
 */
package com.github.cunvoas.iam.security.annotation;

import org.springframework.security.access.ConfigAttribute;

/**
 * @author CUNVOAS
 */
public class IamConfigAttribute implements ConfigAttribute {
    
    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    
    private String vectorKey;
    private String vectorValue;
    
    public IamConfigAttribute(String key, String value) {
        this.vectorKey=key;
        this.vectorValue=value;
    }

    @Override
    public String getAttribute() {
        return (new StringBuilder()).append(this.vectorKey).append("=").append(this.vectorValue).toString();
    }

    /**
     * Getter for vectorKey.
     * @return the vectorKey
     */
    public String getVectorKey() {
        return vectorKey;
    }

    /**
     * Getter for vectorValue.
     * @return the vectorValue
     */
    public String getVectorValue() {
        return vectorValue;
    }
    
    

}
