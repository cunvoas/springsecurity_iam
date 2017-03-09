package com.github.cunvoas.iam.persistance.entity.delegation.state;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;


/**
 * @author CUNVOAS
 */
@Entity
@DiscriminatorValue(DelegationState.STATE_ACCEPTED)
public class AcceptedState extends AbstractState {
    
	/** serialVersionUID. */
	private static final long serialVersionUID = 1404043901642780232L;
	
	/**
     * Constructor.
     */
    public AcceptedState() {
        super(ID_ACCEPTED, STATE_ACCEPTED);
    }

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateChange#read(com.github.cunvoas.iam.persistance.entity.delegation.DelegationState)
	 */
	@Override
	public DelegationState read() {
		super.throwStateException();
		return null;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateChange#accept(com.github.cunvoas.iam.persistance.entity.delegation.DelegationState)
	 */
	@Override
	public DelegationState accept() {
		super.throwStateException();
		return null;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateChange#refuse(com.github.cunvoas.iam.persistance.entity.delegation.DelegationState)
	 */
	@Override
	public DelegationState refuse() {
		super.throwStateException();
		return null;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateChange#expire(com.github.cunvoas.iam.persistance.entity.delegation.DelegationState)
	 */
	@Override
	public DelegationState expire() {
		super.throwStateException();
		return null;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateChange#cancel(com.github.cunvoas.iam.persistance.entity.delegation.DelegationState)
	 */
	@Override
	public DelegationState cancel() {
		return new CanceledState();
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateChange#terminate(com.github.cunvoas.iam.persistance.entity.delegation.DelegationState)
	 */
	@Override
	public DelegationState terminate() {
		return new TerminatedState();
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition#couldBeRead()
	 */
	@Override
	public boolean couldBeRead() {
		return false;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition#couldBeAcceptable()
	 */
	@Override
	public boolean couldBeAcceptable() {
		return false;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition#couldBeRefusable()
	 */
	@Override
	public boolean couldBeRefusable() {
		return false;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition#couldBeExpirable()
	 */
	@Override
	public boolean couldBeExpirable() {
		return false;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition#couldBeCancelable()
	 */
	@Override
	public boolean couldBeCancelable() {
		return true;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition#couldBeTerminable()
	 */
	@Override
	public boolean couldBeTerminable() {
		return true;
	}
}
