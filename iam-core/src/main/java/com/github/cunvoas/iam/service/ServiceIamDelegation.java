package com.github.cunvoas.iam.service;

import java.util.List;

import com.github.cunvoas.iam.bo.delegation.IamDelegation;
import com.github.cunvoas.iam.bo.delegation.IamDelegationSearchCriteria;
import com.github.cunvoas.iam.persistance.entity.delegation.Delegation;

/**
 * Interface from delegation service.
 * 
 * An accepted Delegation cannot be modified :
 *   - responsability of the delegate cont be extended by delegator.
 *   - an another delegation must be created and accepted.
 * 
 * @author CUNVOAS
 */
public interface ServiceIamDelegation {
    
    /**
     * find the delegations.
     * @param criteria
     * @return
     */
    List<IamDelegation> find(IamDelegationSearchCriteria criteria);
    
    /**
     * find the active delegation I can use.
     * @param uidDelegate
     * @return
     */
    List<IamDelegation> findDelegatedForVector(String uidDelegate);
    
    /**
     * find the delegation.
     * @param id
     * @return
     */
    IamDelegation findById(Integer id);
    Delegation findPersistedById(Integer id);
    
    /**
     * Scheduled task for internal management.
     */
    void daemonTask();
    

    void read(IamDelegation delagation);
    void accept(IamDelegation delagation);
    void refuse(IamDelegation delagation);
    void cancel(IamDelegation delagation);
    
    IamDelegation save(IamDelegation delagation);
    
}
