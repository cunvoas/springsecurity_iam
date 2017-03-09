package com.github.cunvoas.iam.security.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.cunvoas.iam.bo.IamRawUser;
import com.github.cunvoas.iam.service.ServiceVectorIam;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({
//    "/spring/spring-test-sec.xml",
//    "/spring/spring-security-iam-remote.xml"
//})
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestIamUserDetailsService {
    
//    @Autowired
    private IamUserDetailsService iamUserDetailsService;
    
//    @Autowired
//    @Qualifier("iamWsClient")
    private ServiceVectorIam serviceVectorIam;
    
    @Value("${iamWebServiceInterface}")
    private String wsInterface;
    
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test00Skip() {
    	Assert.assertTrue(true);
    }
    
   // @Test
    public void test01LoadUserByUsername() {
        Class[] itfs = serviceVectorIam.getClass().getInterfaces();
        List<String> listStrItfs = new ArrayList<String>();
        for (int i = 0; i < itfs.length; i++) {
            listStrItfs.add(itfs[i].getName());
        }
        Assert.assertTrue("java interface", listStrItfs.contains(wsInterface));
        
        
        IamRawUser user = serviceVectorIam.findIamRawVector("cunvoas", "IAM");
        Assert.assertNotNull("userDetail", user);
        
        UserDetails userDetails = iamUserDetailsService.loadUserByUsername("ADMIN_IAM");
        Assert.assertNotNull("userDetail", userDetails);
        
    }

}
