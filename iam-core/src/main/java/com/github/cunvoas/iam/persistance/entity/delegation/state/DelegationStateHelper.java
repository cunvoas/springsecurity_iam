package com.github.cunvoas.iam.persistance.entity.delegation.state;

import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;



/**
 * Helper for Delegation State.
 * @author CUNVOAS
 */
public abstract class DelegationStateHelper {
    
    /**
     * Instanciate state from id.
     * @param id
     * @return
     */
    public static DelegationState getState(Integer id) {
        DelegationState state = null;
        
        if (DelegationState.ID_DEMAND.equals(id)) {
            state = new DemandState();
            
        } else if (DelegationState.ID_READ.equals(id)) {
            state = new ReadState();
            
        } else if (DelegationState.ID_ACCEPTED.equals(id)) {
            state = new AcceptedState();
            
        } else if (DelegationState.ID_REFUSED.equals(id)) {
            state = new RefusedState();
            
        } else if (DelegationState.ID_EXPIRED.equals(id)) {
            state = new ExpiredState();
            
        } else if (DelegationState.ID_CANCELED.equals(id)) {
            state = new CanceledState();
            
	    } else if (DelegationState.ID_TERMINATED.equals(id)) {
	        state = new TerminatedState();
	        
	    }
        
        return state;
    }

    /**
     * Instanciate state from State code.
     * @param id
     * @return
     */
    public static DelegationState getState(String id) {
        DelegationState state = null;
        
        if (DelegationState.STATE_DEMAND.equals(id)) {
            state = new DemandState();
            
        } else if (DelegationState.STATE_READ.equals(id)) {
            state = new ReadState();
            
        } else if (DelegationState.STATE_ACCEPTED.equals(id)) {
            state = new AcceptedState();
            
        } else if (DelegationState.STATE_REFUSED.equals(id)) {
            state = new RefusedState();
            
        } else if (DelegationState.STATE_EXPIRED.equals(id)) {
            state = new ExpiredState();
            
        } else if (DelegationState.STATE_CANCELED.equals(id)) {
            state = new CanceledState();
            
	    } else if (DelegationState.STATE_TERMINATED.equals(id)) {
	        state = new TerminatedState();
	        
	    }
        
        return state;
    }

}
