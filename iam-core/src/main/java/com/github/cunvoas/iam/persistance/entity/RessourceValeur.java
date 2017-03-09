package com.github.cunvoas.iam.persistance.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the iam_resval database table.
 * 
 */
@Entity
@Table(name="iam_resval")
@NamedQuery(name="RessourceValeur.findAll", query="SELECT r FROM RessourceValeur r")
public class RessourceValeur implements Serializable {
    private static final long serialVersionUID = 1L;
    private RessourceValeurPK id;
    private Ressource ressource;
    private Role role;
    private Valeur value;

    @EmbeddedId
    public RessourceValeurPK getId() {
        return this.id;
    }

    public void setId(RessourceValeurPK id) {
        this.id = id;
    }


    //bi-directional many-to-one association to Ressource
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="res_id", nullable=false, insertable=false, updatable=false)
    public Ressource getRessource() {
        return this.ressource;
    }

    public void setRessource(Ressource ressource) {
        this.ressource = ressource;
    }


    //bi-directional many-to-one association to Role
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rol_id", nullable=false, insertable=false, updatable=false)
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    //bi-directional many-to-one association to Valeur
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="val_id")
    public Valeur getValue() {
        return this.value;
    }

    public void setValue(Valeur value) {
        this.value = value;
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
        if (!(obj instanceof RessourceValeur)) {
            return false;
        }
        RessourceValeur other = (RessourceValeur) obj;
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