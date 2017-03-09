package com.github.cunvoas.iam.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IamRawRole implements Serializable {
    /** serialVersionUID. */
    private static final long serialVersionUID = 625553903644882110L;
    
    private Integer id;
    private String code;
    private String description;
    private String application;
    
    private IamRawDelegate delegation;
    
    public IamRawRole( ) {
        super();
    }
    
    public IamRawRole(IamRole role) {
        this.id = role.getId();
        this.code = role.getCode();
        this.description = role.getDescription();
        this.ressources = new ArrayList<IamRawRessource>();
    }
    
    private List<IamRawRessource> ressources;
    
    /**
     * Getter for id.
     * @return the id
     */
    public Integer getId() {
        return id;
    }
    /**
     * Setter for id.
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
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
        this.code = code;
    }
    /**
     * Getter for description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * Setter for description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Getter for ressources.
     * @return the ressources
     */
    public List<IamRawRessource> getRessources() {
        return ressources;
    }
    
    public void addRessources(IamRawRessource ressource) {
        if (this.ressources==null) {
            this.ressources = new ArrayList<IamRawRessource>();
        }
        ressources.add(ressource);
    }
    
    /**
     * Setter for ressources.
     * @param ressources the ressources to set
     */
    public void setRessources(List<IamRawRessource> ressources) {
        this.ressources = ressources;
    }

    /**
     * Getter for application.
     * @return the application
     */
    public String getApplication() {
        return application;
    }

    /**
     * Setter for application.
     * @param application the application to set
     */
    public void setApplication(String application) {
        this.application = application;
    }

    /**
     * Getter for delegation.
     * @return the delegation
     */
    public IamRawDelegate getDelegation() {
        return delegation;
    }

    /**
     * Setter for delegation.
     * @param delegation the delegation to set
     */
    public void setDelegation(IamRawDelegate delegation) {
        this.delegation = delegation;
    }
    
}
