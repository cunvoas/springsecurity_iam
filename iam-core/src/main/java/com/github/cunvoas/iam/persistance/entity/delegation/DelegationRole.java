package com.github.cunvoas.iam.persistance.entity.delegation;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.cunvoas.iam.persistance.entity.Role;

@Entity
@Table(name="iam_delegation_approl")
public class DelegationRole implements Serializable {
	
	 /** serialVersionUID. */
	private static final long serialVersionUID = -7756973268610244959L;

	private DelegationRolePK id;
	 
	 private Role role;
	 private Delegation delegation;

	/**
	 * Getter for id.
	 * @return the id
	 */
	 @EmbeddedId
	public DelegationRolePK getId() {
		return id;
	}

	/**
	 * Setter for id.
	 * @param id the id to set
	 */
	public void setId(DelegationRolePK id) {
		this.id = id;
	}
	

    //bi-directional many-to-one association to Role
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dlg_app_rol_rolid", nullable=false, insertable=false, updatable=false, foreignKey=@ForeignKey(name="fk_dlgapprol_rol"))
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    

    //bi-directional many-to-one association to Ressource
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dlg_app_rol_dlgid", nullable=false, insertable=false, updatable=false, foreignKey=@ForeignKey(name="fk_dlgapprol_dlg"))
    public Delegation getDelegation() {
        return this.delegation;
    }
    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
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
		if (!(obj instanceof DelegationRole)) {
			return false;
		}
		DelegationRole other = (DelegationRole) obj;
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
