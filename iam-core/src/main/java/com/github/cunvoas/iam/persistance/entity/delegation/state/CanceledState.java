package com.github.cunvoas.iam.persistance.entity.delegation.state;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;


/**
 * @author CUNVOAS
 */
@Entity
@DiscriminatorValue(DelegationState.STATE_CANCELED)
public class CanceledState extends AbstractFinalState {
    
	/** serialVersionUID. */
	private static final long serialVersionUID = 8725052114060031473L;

	/**
     * Constructor.
     */
    public CanceledState() {
        super(ID_CANCELED, STATE_CANCELED);
    }
   
}
