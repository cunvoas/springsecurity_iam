package com.github.cunvoas.iam.bo;

import java.io.Serializable;

public class IamValue implements Serializable{
    /** serialVersionUID. */
    private static final long serialVersionUID = 1532415154498418676L;
    private Integer id;
    private String valeur;
    
    public IamValue() {
        super();
    }
    public IamValue(Integer id, String val) {
        this.id=id;
        this.valeur=val;
    }
    
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
     * Getter for valeur.
     * @return the valeur
     */
    public String getValeur() {
        return valeur;
    }
    /**
     * Setter for valeur.
     * @param valeur the valeur to set
     */
    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
    
}
