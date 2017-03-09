package com.github.cunvoas.iam.persistance.repositories.delegation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.cunvoas.iam.persistance.entity.delegation.state.AbstractState;

/**
 * Repository for Delegation.
 * @author CUNVOAS
 */
public interface DelegationStateRepository extends JpaRepository<AbstractState, Integer>, JpaSpecificationExecutor<AbstractState> {

	
	
}
