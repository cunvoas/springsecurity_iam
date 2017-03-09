package com.github.cunvoas.iam.service.impl;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.cunvoas.iam.bo.delegation.IamDelegation;
import com.github.cunvoas.iam.bo.delegation.IamDelegationSearchCriteria;
import com.github.cunvoas.iam.service.ServiceIamDelegation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "/spring/spring-test-jdbc.xml",
    "/spring/spring-test-ldap.xml",
    "/spring/core/spring-core-root.xml"
})
@Transactional
@TransactionConfiguration(defaultRollback = true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestServiceIamDelegationImpl {
	
	@Autowired
	private ServiceIamDelegation tested;
	
	private IamDelegationSearchCriteria criteria=null;
	private String delegated="jbpetit";
	private String delegator="cunvoas";

	@Before
	public void setUp() throws Exception {
		criteria=new IamDelegationSearchCriteria();
		criteria.setApplicationCode("IAM");
		criteria.setApplicationId(1);
        criteria.setSearchDate(new Date());
        criteria.setAlive(Boolean.TRUE);
	}

	@Test
	public void testFind_4Delegator() {
        criteria.setDelegatorUid(delegator);
        
		List<IamDelegation> delegs = tested.find(criteria);
		Assert.assertNotNull("delegations", delegs);

		Assert.assertEquals("nb items", 2, delegs.size());
		
		for (IamDelegation iamDelegation : delegs) {
			Assert.assertNotNull("roles dlg="+iamDelegation.getId(), iamDelegation.getRoles());
		}
		
		
	}

	@Test
	public void testFind_4Delegated() {
        criteria.setDelegateUid(delegated);
        
		List<IamDelegation> delegs = tested.find(criteria);
		Assert.assertNotNull("delegations", delegs);

		Assert.assertEquals("nb items", 1, delegs.size());
	}

}
