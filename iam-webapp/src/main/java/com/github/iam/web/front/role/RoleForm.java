package com.github.cunvoas.iam.web.front.role;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author C.Unvoas
 */
public class RoleForm {

    private String id;
    
    @NotBlank(message="valid.role.application.blank")
    private String idApp;
    
    @NotBlank(message="valid.role.code.blank")
    @Length(min=3, max=50, message="valid.role.code.len")
    @Pattern(regexp="(^[A-Z]{1}[A-Z0-9_]*$)", message="valid.role.code.pattern")
    private String code;
    
    @Length(max=200, message="valid.role.commentaire.len")
    private String commentaire;

    @Length(max=200, message="valid.role.description.len")
    private String description;

    /**
     * Getter for  id.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for  id.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for  idApp.
     * @return the idApp
     */
    public String getIdApp() {
        return idApp;
    }

    /**
     * Setter for  idApp.
     * @param idApp the idApp to set
     */
    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    /**
     * Getter for  code.
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter for  code.
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter for  commentaire.
     * @return the commentaire
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * Setter for  commentaire.
     * @param commentaire the commentaire to set
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    /**
     * Getter for  description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for  description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    

}
