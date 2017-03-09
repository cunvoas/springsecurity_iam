package com.github.cunvoas.iam.persistance.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the iam_resval database table.
 * 
 */
@Embeddable
public class RessourceValeurPK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    private Integer rolId;
    private Integer resId;

    @Column(name="rol_id", insertable=false, updatable=false, unique=true, nullable=false)
    public Integer getRolId() {
        return this.rolId;
    }
    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    @Column(name="res_id", insertable=false, updatable=false, unique=true, nullable=false)
    public Integer getResId() {
        return this.resId;
    }
    public void setResId(Integer resId) {
        this.resId = resId;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RessourceValeurPK)) {
            return false;
        }
        RessourceValeurPK castOther = (RessourceValeurPK)other;
        return 
            this.rolId.equals(castOther.rolId)
            && this.resId.equals(castOther.resId);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.rolId.hashCode();
        hash = hash * prime + this.resId.hashCode();
        
        return hash;
    }
}