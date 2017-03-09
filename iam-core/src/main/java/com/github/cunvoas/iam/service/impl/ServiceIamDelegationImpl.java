package com.github.cunvoas.iam.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.github.cunvoas.iam.bo.delegation.IamDelegation;
import com.github.cunvoas.iam.bo.delegation.IamDelegationSearchCriteria;
import com.github.cunvoas.iam.persistance.entity.delegation.Delegation;
import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;
import com.github.cunvoas.iam.persistance.entity.delegation.state.AbstractState;
import com.github.cunvoas.iam.persistance.repositories.ApplicationRepository;
import com.github.cunvoas.iam.persistance.repositories.delegation.DelegationRepository;
import com.github.cunvoas.iam.persistance.repositories.delegation.DelegationStateRepository;
import com.github.cunvoas.iam.persistance.specification.delegation.SpecificationDelegation;
import com.github.cunvoas.iam.service.ServiceIamDelegation;
import com.github.cunvoas.iam.service.mapper.MapperHelper;

/**
 * @author CUNVOAS
 */
@Service
public class ServiceIamDelegationImpl implements ServiceIamDelegation {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceIamDelegationImpl.class);
    
    @Resource
    private ApplicationRepository applicationRepository;
    
    @Resource
    private DelegationRepository delegationRepository;
    
    @Resource
    private DelegationStateRepository delegationStateRepository;
    

	/**
	 * @see com.github.cunvoas.iam.service.ServiceIamDelegation#find(com.github.cunvoas.iam.bo.delegation.IamDelegationSearchCriteria)
	 */
	@Override
	public List<IamDelegation> find(IamDelegationSearchCriteria criteria) {
		List<IamDelegation> ret=null;
		
		List<Delegation> results = delegationRepository.findAll(SpecificationDelegation.search(criteria));
		if (CollectionUtils.isNotEmpty(results)) {
			ret = new ArrayList<IamDelegation>();
			
			for (Delegation delegation : results) {
				ret.add(MapperHelper.map(delegation));
			}
		}
		
		return ret;
	}

	/**
	 * @see com.github.cunvoas.iam.service.ServiceIamDelegation#findDelegatedForVector(java.lang.String)
	 */
	@Override
	public List<IamDelegation> findDelegatedForVector(String uidDelegate) {
		IamDelegationSearchCriteria criteria = new IamDelegationSearchCriteria();
		// only active delegation
		criteria.setDelegateUid(uidDelegate);
		criteria.setStatus(DelegationState.ID_ACCEPTED);
		criteria.setSearchDate(new Date());
		
		List<IamDelegation> delegations = this.find(criteria);
		
		//FIXME ADD ROLES/RESSOURCES
		return delegations;
	}

	/**
	 * executed every hour (H:00:00).
	 * @see com.github.cunvoas.iam.service.ServiceIamDelegation#daemonTask()
	 */
	@Scheduled(cron="1 0 0 * * *")
	@Override    
	public void daemonTask() {
		LOGGER.info("start daemonTask");
		
		// search delegation to expire
		Integer[] idsStrings = new Integer[] {DelegationState.ID_DEMAND, DelegationState.ID_READ};
		
		List<Delegation> toExpire = delegationRepository.findForExpiration(Arrays.asList(idsStrings));
		if (CollectionUtils.isNotEmpty(toExpire)) {
			for (Delegation delegation : toExpire) {
				delegation.expire();
			}
			delegationRepository.save(toExpire);
		}
		// search delegation to terminate
		List<Delegation> toTerminate = delegationRepository.findForTermination(DelegationState.ID_ACCEPTED);
		if (CollectionUtils.isNotEmpty(toTerminate)) {
			for (Delegation delegation : toTerminate) {
				delegation.terminate();
			}
			delegationRepository.save(toTerminate);
		}
		
		LOGGER.info("stop daemonTask");
	}

	/**
	 * @see com.github.cunvoas.iam.service.ServiceIamDelegation#read(com.github.cunvoas.iam.bo.delegation.IamDelegation)
	 */
	@Override
	public void read(IamDelegation iamDeleg) {
		
		Delegation bo = delegationRepository.findOne(iamDeleg.getId());
		if (bo.getStatus().couldBeRead()) {
			bo.read();
			bo = delegationRepository.save(bo);
		}
	}

	/**
	 * @see com.github.cunvoas.iam.service.ServiceIamDelegation#accept(com.github.cunvoas.iam.bo.delegation.IamDelegation)
	 */
	@Override
	public void accept(IamDelegation iamDeleg) {
		
		Delegation bo = delegationRepository.findOne(iamDeleg.getId());
		if (bo.getStatus().couldBeAcceptable()) {
			bo.accept();
			bo = delegationRepository.save(bo);
		}
	}

	/**
	 * @see com.github.cunvoas.iam.service.ServiceIamDelegation#refuse(com.github.cunvoas.iam.bo.delegation.IamDelegation)
	 */
	@Override
	public void refuse(IamDelegation iamDeleg) {
		
		Delegation bo = delegationRepository.findOne(iamDeleg.getId());
		if (bo.getStatus().couldBeRefusable()) {
			bo.refuse();
			bo = delegationRepository.save(bo);
		}
	}

	/**
	 * @see com.github.cunvoas.iam.service.ServiceIamDelegation#cancel(com.github.cunvoas.iam.bo.delegation.IamDelegation)
	 */
	@Override
	public void cancel(IamDelegation iamDeleg) {
		
		Delegation bo = delegationRepository.findOne(iamDeleg.getId());
		if (bo.getStatus().couldBeCancelable()) {
			bo.cancel();
			bo = delegationRepository.save(bo);
		}

	}

	/**
	 * @see com.github.cunvoas.iam.service.ServiceIamDelegation#save(com.github.cunvoas.iam.bo.delegation.IamDelegation)
	 */
	@Override
	public IamDelegation save(IamDelegation iamDeleg) {
		Delegation bo = null;
		if (iamDeleg!=null && iamDeleg.getId()!=null) {
			bo = delegationRepository.findOne(iamDeleg.getId());
		}
		bo = MapperHelper.merge(iamDeleg, bo);
		bo = delegationRepository.save(bo);
		
		return MapperHelper.map(bo);
	}

	/**
	 * @see com.github.cunvoas.iam.service.ServiceIamDelegation#findById(java.lang.Integer)
	 */
	@Override
	public IamDelegation findById(Integer id) {
		Delegation deleg = delegationRepository.findOne(id);
		IamDelegation bo = MapperHelper.map(deleg);
		return bo;
	}

	/**
	 * @see com.github.cunvoas.iam.service.ServiceIamDelegation#findPersistedById(java.lang.Integer)
	 */
	@Override
	public Delegation findPersistedById(Integer id) {
		Delegation deleg = delegationRepository.findOne(id);
		
		// FIX LazyInit
		AbstractState state = delegationStateRepository.findOne(deleg.getStatus().getId());
		state.getId();state.getDiscriminator(); state.getName();
		deleg.setStatus(state);
		
		return deleg;
	}
}
