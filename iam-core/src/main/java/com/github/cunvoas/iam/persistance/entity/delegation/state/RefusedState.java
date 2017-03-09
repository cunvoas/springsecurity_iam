package com.github.cunvoas.iam.persistance.entity.delegation.state;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;

/**
 * @author CUNVOAS
 */
@Entity
@DiscriminatorValue(DelegationState.STATE_REFUSED)
public class RefusedState extends AbstractFinalState {

	/** serialVersionUID. */
	private static final long serialVersionUID = -1077679883298574456L;

	/**
	 * Constructor.
	 */
	public RefusedState() {
		super(ID_REFUSED, STATE_REFUSED);
	}

}
