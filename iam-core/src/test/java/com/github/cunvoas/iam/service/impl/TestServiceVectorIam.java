/**
 * 
 */
package com.github.cunvoas.iam.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.cunvoas.iam.bo.IamRawUser;
import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.bo.IamRole;
import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.entity.Role;
import com.github.cunvoas.iam.persistance.repositories.ApplicationRepository;
import com.github.cunvoas.iam.persistance.repositories.RoleRepository;
import com.github.cunvoas.iam.service.ServiceVectorIam;

/**
 * @author CUNVOAS
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "/spring/spring-test-jdbc.xml",
    "/spring/spring-test-ldap.xml",
    "/spring/spring-ldap.xml",
    "/spring/core/spring-core-root.xml"
})
@Transactional
@TransactionConfiguration(defaultRollback = true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestServiceVectorIam {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceVectorIam.class);
    
    @Resource
    private ApplicationRepository applicationRepository;
    
    @Resource
    private RoleRepository roleRepository;
    
    private Application application;
    private Role role;
    
    @Autowired
    private ServiceVectorIam serviceVectorIam;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        application = applicationRepository.findOne(1);
        role = roleRepository.findOne(1);
    }

    @Test
    public void testIamRawVector() {
        IamRawUser user = serviceVectorIam.findIamRawVector("cunvoas", "IAM");
        
        Assert.assertNotNull("IAM USER", user);
        Assert.assertTrue("IAM USER ROLE", user.getRoles()!=null && !user.getRoles().isEmpty());
        
        
    }
    
    /**
     * Test method for {@link com.github.cunvoas.iam.service.impl.ServiceVectorIamImpl#findIamVector(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testFindIamVector() {
        
        Long timerNs = System.nanoTime();
        
        List<IamRole> roles = serviceVectorIam.findIamRessource("cunvoas", "IAM");
         
        
        LOGGER.error("duration for first call {}ms", (System.nanoTime() - timerNs)/1E6);
        
        for (int i = 0; i <10; i++) {
            timerNs = System.nanoTime();
            
            roles = serviceVectorIam.findIamRessource("cunvoas", "IAM");
            
            LOGGER.error("duration for call n#{} {}ms", i+1, (System.nanoTime() - timerNs)/1E6);
        }
        
        Assert.assertNotNull("Vecteur IAM", roles);
        Assert.assertNotNull("Vecteur Ressource IAM", roles.get(0).getRessource());
        
        
    }

    /**
     * Test method for {@link com.github.cunvoas.iam.service.impl.ServiceVectorIamImpl#getRessourceValueTree(com.github.cunvoas.iam.persistance.entity.Application, com.github.cunvoas.iam.persistance.entity.Role)}.
     *
    @Test
    public void testGetRessourceTree() {
        Long maxTimer=0L;
        Long minTimer=Long.MAX_VALUE;
        Long sumTimer=0L;
        Long tmpLong=0L;
        int cpt=0;
        
        Long timerNs = System.nanoTime();
        IamRessource ressource = serviceVectorIam.findIamRessource("ADMIN_IAM", "IAM");
        cpt++;
        //LOGGER.error("TREE duration for first call {}ms", (System.nanoTime() - timerNs)/1E6);
        
        tmpLong = Double.valueOf((System.nanoTime() - timerNs)/1E3).longValue();
        maxTimer = Math.max(maxTimer, tmpLong);
        minTimer = Math.min(minTimer, tmpLong);
        sumTimer+=tmpLong;
        
        for (int i = 0; i <1000; i++) {
            timerNs = System.nanoTime();
            ressource = serviceVectorIam.findIamRessource("ADMIN_IAM", "IAM");
            
            tmpLong = Double.valueOf((System.nanoTime() - timerNs)/1E3).longValue();
            maxTimer = Math.max(maxTimer, tmpLong);
            minTimer = Math.min(minTimer, tmpLong);
            sumTimer+=tmpLong;
            //LOGGER.error("TREE duration for call n#{} {}µs", i+1,tmpLong);
            cpt++;
        }
        LOGGER.error("TREE max duration is {}µs",maxTimer);
        LOGGER.error("TREE min duration is {}µs",minTimer);
        LOGGER.error("TREE avg duration is {}µs",sumTimer/cpt);
        
        
        Assert.assertTrue("max duration", maxTimer<6000);
    }
    */
    
    @Test
    public void testFindForConstants() {
        List<IamRessource> ressources = serviceVectorIam.findForConstants("IAM");
        Assert.assertNotNull("Vecteur IAM", ressources);
        Assert.assertNotNull("Vecteur Ressource IAM", ressources.get(0).getVectorCode());
    }

}
