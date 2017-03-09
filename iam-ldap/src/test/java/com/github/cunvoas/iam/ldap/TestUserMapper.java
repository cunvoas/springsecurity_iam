package com.github.cunvoas.iam.ldap;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Test only use to have an LDAP server during dev.
 * @author CUNVOAS
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "/spring/spring-test-ldap.xml",
    "/spring/ldap/spring-ldap.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserMapper {
    
    /**
     * 
     */
    @Test
    public void testLdapServer() {
        int loop = 1 * 60; // minutes * 60 sec
        
        try {
            while(loop-- >0) {
                Thread.sleep(1000);;
            }
        } catch (InterruptedException e) {
            Assert.fail("Exception occurs");
        }
        Assert.assertTrue("done", true);
    }

}
