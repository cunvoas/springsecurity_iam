package com.github.cunvoas.iam.bo.delegation;

import com.github.cunvoas.iam.bo.delegation.IamDelegationState;
import com.github.cunvoas.iam.exception.IamException;
import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;

/**
 * Status for the delegation.
 * @author CUNVOAS
 */
public class IamDelegationState  {

    private Integer id;
    private String discriminator;
    private String name;
    
    public IamDelegationState() {
        super();
    }
    
    public IamDelegationState(DelegationState dlgState) {
        super();
        this.setId( dlgState.getId() );
        this.setDiscriminator( dlgState.getDiscriminator() );
        this.setName( dlgState.getName() );
    }
    
    public IamDelegationState(Integer id) {
        super();
        this.id=id;
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
     * Getter for name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name the name to set
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
        if (!(obj instanceof IamDelegationState)) {
            return false;
        }
        IamDelegationState other = (IamDelegationState) obj;
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
        throw new IamException("RULE_10_001", new String[] {String.valueOf(this.getId()), this.getName()});
    }

	/**
	 * Getter for discriminator.
	 * @return the discriminator
	 */
	public String getDiscriminator() {
		return discriminator;
	}

	/**
	 * Setter for discriminator.
	 * @param discriminator the discriminator to set
	 */
	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}
    
}
