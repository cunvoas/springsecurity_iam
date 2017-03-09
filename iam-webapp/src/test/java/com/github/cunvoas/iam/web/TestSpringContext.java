package com.github.cunvoas.iam.web;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.repositories.ApplicationRepository;
import com.github.cunvoas.iam.persistance.repositories.RessourceRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "/spring/spring-test-jdbc.xml",
    "/spring/spring-test-ldap.xml",
    "/spring/core/spring-core-root.xml"
})
@Transactional
@TransactionConfiguration(defaultRollback = true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSpringContext {
    @Resource
    ApplicationRepository applicationRepository;
    @Resource
    RessourceRepository ressourceRepository;
    
    private Integer appId = 1;

    @Before
    public void init() {
        appId = 1;
    }

    @Test
    public void test01Application() {
        Application application = applicationRepository.findOne(appId);
        Assert.assertNotNull("Application ID", application);
        Assert.assertEquals("Application ID", appId, application.getId());
        
    }
    

}
