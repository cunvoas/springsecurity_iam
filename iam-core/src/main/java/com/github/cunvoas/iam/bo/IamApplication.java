package com.github.cunvoas.iam.bo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author CUNVOAS
 */
public class IamApplication implements Serializable {

    private static final long serialVersionUID = -4927622773264470649L;

    private static final int MAX_DISPLAY_LEN = 50;
    
    private Integer id;
    private String code;
    private IamRessource ressources;
    private String description;
    private String url;
    private List<IamRole> roles;

    /**
     * Getter for id.
     * 
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter for id.
     * 
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    @JsonIgnore
    public String getDescriptionShort() {
        String retString = null;
        if (description!=null && description.length()>MAX_DISPLAY_LEN) {
            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("<span title=\"");
            sBuilder.append(description.replaceAll("\"", "&quot;"));
            sBuilder.append("\">");
            sBuilder.append(description.substring(0, MAX_DISPLAY_LEN));
            sBuilder.append("...");
            sBuilder.append("</span>");
            retString = sBuilder.toString();
        } else {
            retString = description;
        }
        return retString;
    }
    
    

    /**
     * Setter for description.
     * 
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for url.
     * 
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter for url.
     * 
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter for roles.
     * 
     * @return the roles
     */
    public List<IamRole> getRoles() {
        return roles;
    }

    /**
     * Setter for roles.
     * 
     * @param roles
     *            the roles to set
     */
    public void setRoles(List<IamRole> roles) {
        this.roles = roles;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof IamApplication)) {
            return false;
        }
        IamApplication other = (IamApplication) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Getter for code.
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter for code.
     * @param code the code to set
     */
    public void setCode(String code) {
        if (code!=null) {
            this.code = code.trim().toUpperCase();
        }
    }

    /**
     * Getter for ressources.
     * @return the ressources
     */
    public IamRessource getRessources() {
        return ressources;
    }

    /**
     * Setter for ressources.
     * @param ressources the ressources to set
     */
    public void setRessources(IamRessource ressources) {
        this.ressources = ressources;
    }

}
