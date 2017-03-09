package com.github.cunvoas.iam.persistance.entity.delegation.state;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;


/**
 * @author CUNVOAS
 */
@Entity
@DiscriminatorValue(DelegationState.STATE_EXPIRED)
public class ExpiredState extends AbstractFinalState {
    
	/** serialVersionUID. */
	private static final long serialVersionUID = 789909705623268218L;

	/**
     * Constructor.
     */
    public ExpiredState() {
        super(ID_EXPIRED, STATE_EXPIRED);
    }
    


}
