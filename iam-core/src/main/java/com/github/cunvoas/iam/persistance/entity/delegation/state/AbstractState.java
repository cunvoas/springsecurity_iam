package com.github.cunvoas.iam.persistance.entity.delegation.state;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.github.cunvoas.iam.exception.IamException;

/**
 * Status for the delegation
 * 
 * @author CUNVOAS
 */

@Entity
@Table(name = "iam_delegation_status")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dlg_state_disc", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractState implements DelegationStateChange {

	/** serialVersionUID */
	private static final long serialVersionUID = -7033425428229354820L;
	
	protected Integer id;
	protected String discriminator;
	protected String name;

	/**
	 * Getter for id.
	 * 
	 * @return the id
	 */
	@Id
	@Column(name = "dlg_state_id", insertable = false, updatable = false)
	public Integer getId() {
		return this.id;
	}

	@Column(name = "dlg_state_disc", length=5, insertable = false, updatable = false)
	public String getDiscriminator() {
		return this.discriminator;
	}
	/**
	 * Getter for name.
	 * 
	 * @return the name
	 */
	@Column(name = "dlg_state_label", length = 50, insertable = false, updatable = false)
	public String getName() {
		return this.name;
	}
	

	public AbstractState() {
		super();
	}

	public AbstractState(Integer id) {
		super();
		this.id = id;
	}

	public AbstractState(Integer id, String disc) {
		super();
		this.id = id;
		this.discriminator = disc;
	}



	/**
	 * Setter for id.
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Setter for discriminator.
	 * @param discriminator the discriminator to set
	 */
	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	
	/**
	 * Setter for name.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
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
		if (!(obj instanceof AbstractState)) {
			return false;
		}
		AbstractState other = (AbstractState) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	/**
	 * throw a standardized exception.
	 */
	protected void throwStateException() {
		throw new IamException("RULE_10_001", new String[] {
				String.valueOf(this.getId()), this.getName() });
	}

}
