package com.github.cunvoas.iam.persistance.repositories.delegation;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.cunvoas.iam.persistance.entity.delegation.Delegation;

/**
 * Repository for Delegation.
 * @author CUNVOAS
 */
public interface DelegationRepository extends JpaRepository<Delegation, Integer>, JpaSpecificationExecutor<Delegation> {
	
	@Query("SELECT d FROM Delegation d where d.end<=current_date() and d.status.id=:statusId")
    List<Delegation> findForTermination(@Param("statusId") Integer statusId);
	
	@Query("SELECT d FROM Delegation d where d.end<=current_date() and d.status.id in (:statusIds)")
    List<Delegation> findForExpiration(@Param("statusId") Collection<Integer> statusIds);
	
	@Query
	List<Delegation> findCurrent(Integer status, String delegue);
	
	
}
