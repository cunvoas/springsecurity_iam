package com.github.cunvoas.iam.security.authority;

import org.springframework.security.core.GrantedAuthority;

import com.github.cunvoas.iam.bo.IamRawRole;

/**
 * @author CUNVOAS
 * @see http://stackoverflow.com/questions/1322135/spring-security-authorization-without-authentication
 */
public class IamGrantedAuthority implements GrantedAuthority {
    
    /** serialVersionUID. */
    private static final long serialVersionUID = -8867231002452985802L;
    
    private IamRawRole role;
    
    public IamGrantedAuthority(IamRawRole role) {
        this.role=role;
    }
        
    /**
     * @see org.springframework.security.core.GrantedAuthority#getAuthority()
     */
    @Override
    public String getAuthority() {
        return role.getCode();
    }

    /**
     * Getter for role.
     * @return the role
     */
    public IamRawRole getRole() {
        return role;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return role!=null?role.getCode():"";
    }
    
    

}
