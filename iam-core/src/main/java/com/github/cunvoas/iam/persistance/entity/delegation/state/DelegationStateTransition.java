package com.github.cunvoas.iam.persistance.entity.delegation.state;

import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;


/**
 * @author cunvoas
 *
 */
public interface DelegationStateTransition extends DelegationState {

    /**
     * possible to be change in state READ ?.
     * @return
     */
    boolean couldBeRead();
    
    /**
     * possible to be change in state ACCEPTED ?.
     * @return
     */
    boolean couldBeAcceptable();

    /**
     * possible to be change in state REFUSED ?.
     * @return
     */
    boolean couldBeRefusable();

    /**
     * possible to be change in state EXPIRED ?.
     * @return
     */
    boolean couldBeExpirable();
    

    /**
     * possible to be change in state CANCELED ?.
     * @return
     */
    boolean couldBeCancelable();
    

    /**
     * possible to be change in state FINISHED ?.
     * @return
     */
    boolean couldBeTerminable();

}
