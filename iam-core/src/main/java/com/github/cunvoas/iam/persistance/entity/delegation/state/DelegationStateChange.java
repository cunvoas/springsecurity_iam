package com.github.cunvoas.iam.persistance.entity.delegation.state;

import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;



/**
 * @author cunvoas
 */
public interface DelegationStateChange extends DelegationStateTransition {

    
    /**
     * change to the READ state.
     * @param state
     */
	DelegationState read();

    
    /**
     * change to the ACCEPTED state.
     * @param state
     */
	DelegationState accept();


    /**
     * change to the REFUSED state.
     * @param state
     */
	DelegationState refuse();

    
    /**
     * change to the EXPIRED state.
     * @param state
     */
	DelegationState expire();

    
    /**
     * change to the CANCELED state.
     * @param state
     */
	DelegationState cancel();

    /**
     * change to the TERMINATED state.
     * @param state
     */
	DelegationState terminate();
}
