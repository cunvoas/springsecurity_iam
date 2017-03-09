package com.github.cunvoas.iam.persistance.entity.delegation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the iam_resval database table.
 * 
 */
@Embeddable
public class DelegationRolePK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    
    private Integer rolId;
    private Integer delegationId;

    public DelegationRolePK() {
    }

    @Column(name="dlg_app_rol_rolid", insertable=false, updatable=false, nullable=false)
    public Integer getRolId() {
        return this.rolId;
    }
    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }


    @Column(name="dlg_app_rol_dlgid", insertable=false, updatable=false, nullable=false)
    public Integer getDelegationId() {
        return this.delegationId;
    }
    public void setDelegationId(Integer delegationId) {
        this.delegationId = delegationId;
    }

    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DelegationRolePK)) {
            return false;
        }
        DelegationRolePK castOther = (DelegationRolePK)other;
        return 
            this.rolId.equals(castOther.rolId)
            && this.delegationId.equals(castOther.delegationId);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.rolId.hashCode();
        hash = hash * prime + this.delegationId.hashCode();
        
        return hash;
    }
}