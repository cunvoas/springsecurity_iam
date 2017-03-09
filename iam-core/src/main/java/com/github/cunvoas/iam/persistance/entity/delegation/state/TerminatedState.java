package com.github.cunvoas.iam.persistance.entity.delegation.state;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;


/**
 * @author CUNVOAS
 */
@Entity
@DiscriminatorValue(DelegationState.STATE_TERMINATED)
public class TerminatedState extends AbstractFinalState {
    
    /** serialVersionUID. */
	private static final long serialVersionUID = 4008904660122888438L;

	/**
     * Constructor.
     */
    public TerminatedState() {
        super(ID_TERMINATED, STATE_TERMINATED);
    }
}
