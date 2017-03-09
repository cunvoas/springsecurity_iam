package com.github.cunvoas.iam.persistance.entity.delegation.state;

import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;

/**
 * Implements final methods for final States.
 * @author CUNVOAS
 */
public abstract class AbstractFinalState extends AbstractState {

	/** serialVersionUID. */
	private static final long serialVersionUID = 6005556953372086396L;

	public AbstractFinalState() {
		super();
	}

	public AbstractFinalState(Integer id) {
		super(id);
	}

	public AbstractFinalState(Integer id, String disc) {
		super(id, disc);
	}
	

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateChange#read(com.github.cunvoas.iam.persistance.entity.delegation.DelegationState)
	 */
	@Override
	public final DelegationState read() {
		super.throwStateException();
		return null;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateChange#accept(com.github.cunvoas.iam.persistance.entity.delegation.DelegationState)
	 */
	@Override
	public final DelegationState accept() {
		super.throwStateException();
		return null;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateChange#refuse(com.github.cunvoas.iam.persistance.entity.delegation.DelegationState)
	 */
	@Override
	public final DelegationState refuse() {
		super.throwStateException();
		return null;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateChange#expire(com.github.cunvoas.iam.persistance.entity.delegation.DelegationState)
	 */
	@Override
	public final DelegationState expire() {
		super.throwStateException();
		return null;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateChange#cancel(com.github.cunvoas.iam.persistance.entity.delegation.DelegationState)
	 */
	@Override
	public final DelegationState cancel() {
		super.throwStateException();
		return null;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateChange#terminate(com.github.cunvoas.iam.persistance.entity.delegation.DelegationState)
	 */
	@Override
	public final DelegationState terminate() {
		super.throwStateException();
		return null;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition#couldBeRead()
	 */
	@Override
	public final boolean couldBeRead() {
		return false;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition#couldBeAcceptable()
	 */
	@Override
	public final boolean couldBeAcceptable() {
		return false;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition#couldBeRefusable()
	 */
	@Override
	public final boolean couldBeRefusable() {
		return false;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition#couldBeExpirable()
	 */
	@Override
	public final boolean couldBeExpirable() {
		return false;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition#couldBeCancelable()
	 */
	@Override
	public final boolean couldBeCancelable() {
		return false;
	}

	/**
	 * @see com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition#couldBeTerminable()
	 */
	@Override
	public final boolean couldBeTerminable() {
		return false;
	}

}
