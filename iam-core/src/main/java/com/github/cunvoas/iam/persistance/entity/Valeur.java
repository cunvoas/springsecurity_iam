package com.github.cunvoas.iam.persistance.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the iam_valeur database table.
 * 
 */
@Entity
@Table(name="iam_valeur")
@NamedQuery(name="Valeur.findAll", query="SELECT v FROM Valeur v")
public class Valeur implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String valeur;
    private List<RessourceValeur> ressourceValues;

    public Valeur() {
    }
    public Valeur(Integer id) {
        this.id=id;
    }


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="val_id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Column(length=12)
    public String getValeur() {
        return this.valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }


    //bi-directional many-to-one association to RessourceValeur
    @OneToMany(mappedBy="value")
    public List<RessourceValeur> getRessourceValues() {
        return this.ressourceValues;
    }

    public void setRessourceValues(List<RessourceValeur> ressourceValues) {
        this.ressourceValues = ressourceValues;
    }

    public RessourceValeur addRessourceValue(RessourceValeur ressourceValue) {
        getRessourceValues().add(ressourceValue);
        ressourceValue.setValue(this);

        return ressourceValue;
    }

    public RessourceValeur removeRessourceValue(RessourceValeur ressourceValue) {
        getRessourceValues().remove(ressourceValue);
        ressourceValue.setValue(null);

        return ressourceValue;
    }
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
        if (!(obj instanceof Valeur)) {
            return false;
        }
        Valeur other = (Valeur) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

}