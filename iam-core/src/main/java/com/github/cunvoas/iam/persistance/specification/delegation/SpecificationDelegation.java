package com.github.cunvoas.iam.persistance.specification.delegation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.github.cunvoas.iam.bo.delegation.IamDelegationSearchCriteria;
import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.entity.Application_;
import com.github.cunvoas.iam.persistance.entity.delegation.Delegation;
import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;
import com.github.cunvoas.iam.persistance.entity.delegation.Delegation_;
import com.github.cunvoas.iam.persistance.entity.delegation.state.AbstractState;
import com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateHelper;

/**
 * CriteriaBuilder for criteria search.
 * @author CUNVOAS
 * @see http://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-four-jpa-criteria-queries/
 * @see http://stackoverflow.com/questions/17154676/criteria-jpa-2-with-3-tables
 */
public class SpecificationDelegation {
	
	public static Specification<Delegation> active(final Date searchDate) {
		return new Specification<Delegation>() {
            @Override
            public Predicate toPredicate(Root<Delegation> delegation, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	DelegationState status = DelegationStateHelper.getState(DelegationState.ID_ACCEPTED);
            	
            	return
            	cb.and(
            			cb.equal(delegation.<AbstractState>get(Delegation_.status), status )
            			,
            			cb.and(
                    			cb.lessThanOrEqualTo(delegation.<Date>get(Delegation_.begin), searchDate),
                    			cb.greaterThanOrEqualTo(delegation.<Date>get(Delegation_.end), searchDate)
                    			)
            			)
            	;
            }
            
        };
	}
	
	/**
	 * Search partial delegateID
	 * @param delegate
	 * @return
	 */
	public static Specification<Delegation> likeDelegate(final String delegate) {
		return new Specification<Delegation>() {
            @Override
            public Predicate toPredicate(Root<Delegation> delegation, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	 String likePattern = getLikePattern(delegate);   
            	 
            	 return cb.like(cb.lower(delegation.<String>get(Delegation_.delege)), likePattern);
            }
            
            private String getLikePattern(final String searchTerm) {
                StringBuilder pattern = new StringBuilder();
                pattern.append("%");
                pattern.append(searchTerm.toLowerCase());
                pattern.append("%");
                return pattern.toString();
            }
            
        };
	}
	
	/**
	 * search current active
	 * @return
	 */
	public static Specification<Delegation> active() {
			return active(new Date());
	}
	
	/**
	 * Query by criteria.
	 * @param criteria
	 * @return
	 */
	public static Specification<Delegation> search(final IamDelegationSearchCriteria criteria) {
		
		return new Specification<Delegation>() {
			@Override
            public Predicate toPredicate(Root<Delegation> delegation, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 
				Predicate finalPredicate = null;
				
				 Predicate applicationPredicate = null;
				 if (StringUtils.isNotBlank(criteria.getApplicationCode()) || criteria.getApplicationId()!=null) {
					 Join<Delegation, Application> fromApplication = delegation.join("application");
					 
					 if (criteria.getApplicationId()!=null) {
						 applicationPredicate = cb.equal(fromApplication.get(Application_.id), criteria.getApplicationId());
					 }
					 if (StringUtils.isNotBlank(criteria.getApplicationCode())) {
						 if (applicationPredicate==null) {
							 applicationPredicate = cb.equal(fromApplication.get(Application_.code), criteria.getApplicationCode());
						 } else {
							 applicationPredicate = cb.and(applicationPredicate,  cb.equal(fromApplication.get(Application_.code), criteria.getApplicationCode()));
						 }
					 }
					 finalPredicate = applicationPredicate;
				 }

				 Predicate statusPredicate = null;
				 if (criteria.getStatus()!=null) {
					 DelegationState status = DelegationStateHelper.getState(criteria.getStatus());
		             statusPredicate =  cb.equal(delegation.<AbstractState>get(Delegation_.status), status);
            	 } else if (criteria.getAlive()!=null) {
					 List<Integer> activeState = new ArrayList<Integer>();

					 activeState.add(DelegationState.ID_DEMAND);
					 activeState.add(DelegationState.ID_READ);
					 activeState.add(DelegationState.ID_ACCEPTED);
					 
					 Expression<AbstractState> exp = delegation.get(Delegation_.status);
					 Predicate predicate = exp.in(activeState);
					 
					 if (Boolean.TRUE.equals(criteria.getAlive())) {
						 statusPredicate =  predicate;
					 } else {
						 statusPredicate =  cb.not(predicate);
					 }
				 }
				 if (statusPredicate!=null) {
					 if (finalPredicate==null) {
						 finalPredicate = statusPredicate;
					 } else {
						 finalPredicate = cb.and(finalPredicate, statusPredicate);
					 }
				 }
				 
	            	
				 Predicate delegatePredicate = null;
				 if (StringUtils.isNotBlank(criteria.getDelegateUid())) {
					 delegatePredicate = cb.equal(delegation.<String>get(Delegation_.delege), criteria.getDelegateUid());
				 }
				 if (delegatePredicate!=null) {
					 if (finalPredicate==null) {
						 finalPredicate = delegatePredicate;
					 } else {
						 finalPredicate = cb.and(finalPredicate, delegatePredicate);
					 }
				 }
				 
				 Predicate delegatorPredicate = null;
				 if (StringUtils.isNotBlank(criteria.getDelegatorUid())) {
					 delegatorPredicate = cb.equal(delegation.<String>get(Delegation_.delegateur), criteria.getDelegatorUid());
				 }
				 if (delegatorPredicate!=null) {
					 if (finalPredicate==null) {
						 finalPredicate = delegatorPredicate;
					 } else {
						 finalPredicate = cb.and(finalPredicate, delegatorPredicate);
					 }
				 }
				 
				 
				 Predicate searchDatePredicate = null;
				 if (criteria.getSearchDate()!=null) {
		             searchDatePredicate =  cb.and(
                 			cb.lessThanOrEqualTo(delegation.<Date>get(Delegation_.begin), criteria.getSearchDate()),
                 			cb.greaterThanOrEqualTo(delegation.<Date>get(Delegation_.end), criteria.getSearchDate())
                 			);
            	 }
				 if (searchDatePredicate!=null) {
					 if (finalPredicate==null) {
						 finalPredicate = searchDatePredicate;
					 } else {
						 finalPredicate = cb.and(finalPredicate, searchDatePredicate);
					 }
				 }
				 
				 
				 Predicate beginDatePredicate = null;
				 if (criteria.getBeginDate()!=null) {
					 beginDatePredicate =  cb.and(
                 			cb.lessThanOrEqualTo(delegation.<Date>get(Delegation_.begin), criteria.getBeginDate()),
                 			cb.greaterThanOrEqualTo(delegation.<Date>get(Delegation_.end), criteria.getBeginDate())
                 			);
            	 }
				 if (beginDatePredicate!=null) {
					 if (finalPredicate==null) {
						 finalPredicate = beginDatePredicate;
					 } else {
						 finalPredicate = cb.and(finalPredicate, beginDatePredicate);
					 }
				 }
				 
				 
				 Predicate endDatePredicate = null;
				 if (criteria.getEndDate()!=null) {
					 endDatePredicate =  cb.and(
                 			cb.lessThanOrEqualTo(delegation.<Date>get(Delegation_.begin), criteria.getEndDate()),
                 			cb.greaterThanOrEqualTo(delegation.<Date>get(Delegation_.end), criteria.getEndDate())
                 			);
            	 }
				 if (endDatePredicate!=null) {
					 if (finalPredicate==null) {
						 finalPredicate = endDatePredicate;
					 } else {
						 finalPredicate = cb.and(finalPredicate, endDatePredicate);
					 }
				 }
				 
				 return finalPredicate;
            }
			
		};
		
		
	}

        
   
}
