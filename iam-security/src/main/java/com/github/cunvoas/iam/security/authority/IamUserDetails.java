package com.github.cunvoas.iam.security.authority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.github.cunvoas.iam.bo.IamRawRessource;
import com.github.cunvoas.iam.bo.IamRawRole;
import com.github.cunvoas.iam.bo.IamRawUser;
import com.github.cunvoas.iam.security.helper.IamRawRessourceComparator;

/**
 * Objet IAM de gestion de la sécurité avec Spring-Security.
 * @author CUNVOAS
 */
public class IamUserDetails implements UserDetails {

    /** serialVersionUID. */
    private static final long serialVersionUID = 831920254741774310L;
    
    private String username;
    private String firstname;
    private String lastname;
    private String langage;
    private List<String> langages;
    
    private Date arrival;
    private Date departure;
    
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    
    private List<IamGrantedAuthority> roles;
    
    private IamGrantedAuthority currentRole;
    
    // merge ressources in case of merging roles
    // IRL - complex to use in production
    private List<IamRawRessource> mergeRessources;

    /**
     * Constructor.
     */
    public IamUserDetails() {
        super();
    }
    
    public IamUserDetails(IamRawUser rawUser) {
        super();
        this.accountNonExpired = rawUser.isAccountNonExpired();
        this.accountNonLocked = rawUser.isAccountNonLocked();
        this.credentialsNonExpired = rawUser.isCredentialsNonExpired();
        this.enabled = rawUser.isEnabled();
        
        this.username=rawUser.getUsername();
        this.firstname=rawUser.getFirstname();
        this.lastname=rawUser.getLastname();
        this.langage=rawUser.getLanguage();
        
        IamGrantedAuthority[] auths = null;
        
        List<IamRawRole> userRoles = rawUser.getRoles();
        if (userRoles!=null && !userRoles.isEmpty()) {
            int size = userRoles.size();
            auths = new IamGrantedAuthority[size];
            
            Iterator<IamRawRole> iterRoleIterator = userRoles.iterator();
            for (int i = 0; i <size; i++) {
                auths[i]=new IamGrantedAuthority(iterRoleIterator.next());
            }
            
            // positionnement du role par défaut.
            if (size==1) {
                currentRole = auths[0];
            } else {
            	currentRole = getDefautCurrent(auths);
            }
            
            this.roles = Arrays.asList(auths);
            
            for (IamRawRole iamRawRole : userRoles) {
                // sort for future search
                if (iamRawRole.getRessources()!=null && !iamRawRole.getRessources().isEmpty()) {
                    Collections.sort(iamRawRole.getRessources(), IamRawRessourceComparator.INSTANCE);
                }
            }
        }
        
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Override
    public Collection<IamGrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
     */
    @Override
    public String getPassword() {
        return null;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Setter for username.
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for firstname.
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Setter for lastname.
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Setter for roles.
     * @param roles the roles to set
     */
    public void setRoles(List<IamGrantedAuthority> roles) {
        this.roles = roles;
    }

    /**
     * Getter for firstname.
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Getter for lastname.
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Getter for currentRole.
     * @return the currentRole
     */
    public IamGrantedAuthority getCurrentRole() {
        return currentRole;
    }

    /**
     * Setter for currentRole.
     * @param currentRole the currentRole to set
     */
    public void setCurrentRole(IamGrantedAuthority currentRole) {
        this.currentRole = currentRole;
    }

    /**
     * Getter for mergeRessources.
     * @return the mergeRessources
     */
    public List<IamRawRessource> getMergeRessources() {
        return mergeRessources;
    }

    /**
     * Setter for mergeRessources.
     * @param mergeRessources the mergeRessources to set
     */
    public void setMergeRessources(List<IamRawRessource> mergeRessources) {
        this.mergeRessources = mergeRessources;
    }

    /**
     * Getter for langage.
     * @return the langage
     */
    public String getLangage() {
        return langage;
    }

    /**
     * Setter for langage.
     * @param langage the langage to set
     */
    public void setLangage(String langage) {
        this.langage = langage;
    }

    /**
     * Getter for langages.
     * @return the langages
     */
    public List<String> getLangages() {
        return langages;
    }

    /**
     * Setter for langages.
     * @param langages the langages to set
     */
    public void setLangages(List<String> langages) {
        this.langages = langages;
    }
    
    /**
     * Compute de default role in case of multi-role.
     * Basic rule is the best-score :
     *    ACTION=2, READ=1, NO_ACCESS=1, NOT_SET=0
     * @param auths
     * @return default
     */
    protected IamGrantedAuthority getDefautCurrent(IamGrantedAuthority[] auths) {
    	IamGrantedAuthority auth = null;
    	int len = auths.length;
    	int maxScore=Integer.MIN_VALUE;
    	for (int i = 0; i < len; i++) {
    		
    		IamRawRole rawrol = auths[i].getRole();
    		if (rawrol!=null 
    			&& rawrol.getRessources()!=null
    			&& rawrol.getDelegation()!=null
    			) {
	    		int tmpScore=0;	
    			for (IamRawRessource rawress : rawrol.getRessources()) {
    				tmpScore+= rawress.getValeur()!=null?rawress.getValeur():0;
				}
    			
    			if (tmpScore>maxScore) {
    				maxScore = tmpScore;
    				this.currentRole = auths[i];
    			}
    		}
		}
    	
    	if (this.currentRole==null) {
    		this.currentRole = auths[0];
    	}
    	
    	return auth;
    }

}
