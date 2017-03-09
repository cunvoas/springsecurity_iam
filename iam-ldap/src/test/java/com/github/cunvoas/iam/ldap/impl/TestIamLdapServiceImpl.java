package com.github.cunvoas.iam.ldap.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.cunvoas.iam.ldap.IamLdapService;
import com.github.cunvoas.iam.ldap.IamLdapUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "/spring/spring-test-ldap.xml",
    "/spring/ldap/spring-ldap.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestIamLdapServiceImpl {
    
    @Autowired
    private IamLdapService tested;

    @Test
    public void test01Find() {
        IamLdapUser user = tested.find("jbpetit");
        Assert.assertNotNull("users found jbpetit", user);
        
        user = tested.find("cunvoas");
        Assert.assertNotNull("users found cunvoas", user);

        Assert.assertEquals("uid", "cunvoas", user.getUid());
        Assert.assertEquals("initials", "M.", user.getInitial());
        Assert.assertEquals("cn", "Unvoas Christophe", user.getFullName());
        Assert.assertEquals("givenName", "Christophe", user.getFirstName());
        Assert.assertEquals("sn", "Unvoas", user.getLastName());
        Assert.assertEquals("mail", "christophe.unvoas.fake.email@domain.com", user.getMail());
        
        List<String> roles = new ArrayList<String>();
        roles.add("IAM:ADMIN");
        roles.add("IAM:APPLICATION_MANAGER");
        roles.add("IAM:USER_MANAGER");
        roles.add("IAM:READER");
        roles.add("SAMPLE:READER");
        
        Assert.assertEquals("businessCategory size", roles.size(), user.getRolesList().size());
        
        roles.removeAll(user.getRolesList());
        Assert.assertEquals("businessCategory same ", 0, roles.size());
        
        Assert.assertNull("privArrival", user.getArrival());
        Assert.assertNull("privDeparture", user.getDeparture());
    
    }
    

    @Test
    public void test02Find() {
        List<IamLdapUser> users = tested.find("", null, null);
        Assert.assertNull("users", users);
        
        users = tested.find(null, "", null);
        Assert.assertNull("users", users);

        users = tested.find(null, null, "");
        Assert.assertNull("users", users);
        
        users = tested.find("Jea*", null, null);
        Assert.assertNotNull("users", users);
        Assert.assertEquals("users.size", 1, users.size());
        
        users = tested.find(null, "Pet*", null);
        Assert.assertNotNull("users", users);
        Assert.assertEquals("users.size", 1, users.size());
        
        users = tested.find("Jea*", "Pet*", null);
        Assert.assertNotNull("users", users);
        Assert.assertEquals("users.size", 1, users.size());
        
        users = tested.find(null, null, "IAM");
        Assert.assertNotNull("users", users);
        Assert.assertEquals("users.size", 3, users.size());
        
        users = tested.find("Jea*", null, "IAM");
        Assert.assertNotNull("users", users);
        Assert.assertEquals("users.size", 1, users.size());
        Assert.assertNotNull("users.size", users.get(0).getRolesList());
        Assert.assertEquals("users.size", 2, users.get(0).getRolesList().size());
        
        
    }

}
