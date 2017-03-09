package com.github.cunvoas.iam.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author CUNVOAS
 */
@XmlRootElement
public class IamRawUser implements Serializable {
    
    /** serialVersionUID */
    private static final long serialVersionUID = -2382238553080951527L;
    
    private transient String ldapPathString;
    private String username;
    private String firstname;
    private String lastname;
    private String language;
    private List<String> languages;
    private List<IamRawRole> roles = new ArrayList<IamRawRole>();
    private boolean accountNonExpired=false;
    private boolean accountNonLocked=false;
    private boolean credentialsNonExpired=false;
    private boolean enabled=false;
    
    /**
     * Getter for ldapPathString.
     * @return the ldapPathString
     */
    public String getLdapPathString() {
        return ldapPathString;
    }
    /**
     * Setter for ldapPathString.
     * @param ldapPathString the ldapPathString to set
     */
    public void setLdapPathString(String ldapPathString) {
        this.ldapPathString = ldapPathString;
    }
    /**
     * Getter for username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Setter for username.
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Getter for firstname.
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     * Setter for firstname.
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    /**
     * Getter for lastname.
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }
    /**
     * Setter for lastname.
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    /**
     * Getter for roles.
     * @return the roles
     */
    public List<IamRawRole> getRoles() {
        return roles;
    }
    /**
     * Setter for roles.
     * @param roles the roles to set
     */
    public void setRoles(List<IamRawRole> roles) {
        this.roles = roles;
    }
    /**
     * Getter for accountNonExpired.
     * @return the accountNonExpired
     */
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    /**
     * Setter for accountNonExpired.
     * @param accountNonExpired the accountNonExpired to set
     */
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    /**
     * Getter for accountNonLocked.
     * @return the accountNonLocked
     */
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    /**
     * Setter for accountNonLocked.
     * @param accountNonLocked the accountNonLocked to set
     */
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    /**
     * Getter for credentialsNonExpired.
     * @return the credentialsNonExpired
     */
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    /**
     * Setter for credentialsNonExpired.
     * @param credentialsNonExpired the credentialsNonExpired to set
     */
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    /**
     * Getter for enabled.
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }
    /**
     * Setter for enabled.
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    /**
     * Getter for language.
     * @return the language
     */
    public String getLanguage() {
        return language;
    }
    /**
     * Setter for language.
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }
    /**
     * Getter for languages.
     * @return the languages
     */
    public List<String> getLanguages() {
        return languages;
    }
    /**
     * Setter for languages.
     * @param languages the languages to set
     */
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
    
    
    
}
