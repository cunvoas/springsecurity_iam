package com.github.cunvoas.iam.bo;

import java.io.Serializable;

/**
 * @author CUNVOAS
 */
public class IamRawRessource implements Serializable {
    /** serialVersionUID. */
    private static final long serialVersionUID = -5795992608185781952L;
    private Integer id;
    private String vectorCode;
    private Integer valeur;
    
    public IamRawRessource() {
        super();
    }
    
    public IamRawRessource(IamRessource ress) {
        super();
        this.id = ress.getId();
        this.vectorCode = ress.getVectorCode();
        this.valeur = ress.getValeur();
    }
    
    
    /**
     * Getter for vectorCode.
     * @return the vectorCode
     */
    public String getVectorCode() {
        return vectorCode;
    }
    /**
     * Setter for vectorCode.
     * @param vectorCode the vectorCode to set
     */
    public void setVectorCode(String vectorCode) {
        this.vectorCode = vectorCode;
    }
    /**
     * Getter for valeur.
     * @return the valeur
     */
    public Integer getValeur() {
        return valeur;
    }
    /**
     * Setter for valeur.
     * @param valeur the valeur to set
     */
    public void setValeur(Integer valeur) {
        this.valeur = valeur;
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s=%s", vectorCode, valeur);
    }
    
}
