package com.github.cunvoas.iam.bo;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.cunvoas.iam.util.BoHelper;

@XmlRootElement
public class IamRole  implements Serializable {
    private static final long serialVersionUID = 1097747033462765085L;
    
    private Integer id;
    private String code;
    private String commentaire;
    private String description;
    private IamRessource ressource;
    
    // VectorCode, Value in string
    private transient Map<String, String> ressourceValues;

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
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for commentaire.
     * @return the commentaire
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * Setter for commentaire.
     * @param commentaire
     *            the commentaire to set
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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
     * 
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for ressources.
     * 
     * @return the ressources
     */
    public IamRessource getRessource() {
        return ressource;
    }

    /**
     * Setter for ressources.
     * 
     * @param ressources
     *            the ressources to set
     */
    public void setRessource(IamRessource ressource) {
        this.ressource = ressource;
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
     * Getter for ressourceValues.
     * @return the ressourceValues
     */
    public Map<String, String> getRessourceValues() {
        return ressourceValues;
    }

    /**
     * Setter for ressourceValues.
     * @param ressourceValues the ressourceValues to set
     */
    public void setRessourceValues(Map<String, String> ressourceValues) {
        this.ressourceValues = ressourceValues;
    }
    /**
     * @return Short Description
     */
    @JsonIgnore
    public String getDescriptionShort() {
        return BoHelper.getDescriptionShort(description);
    }

    /**
     * @return Code And Description
     */
    @JsonIgnore
    public String getCodeAndDescription() {
         StringBuilder sBuilder = new StringBuilder();
         sBuilder.append(getCode());
         sBuilder.append(" - ");
         sBuilder.append(getDescription());
         return sBuilder.toString();
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
        if (!(obj instanceof IamRole)) {
            return false;
        }
        IamRole other = (IamRole) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
