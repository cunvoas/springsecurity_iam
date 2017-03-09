package com.github.cunvoas.iam.security.evaluator;


import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author CUNVOAS
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityContextHolder.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestIamPermissionEvaluator {
    
    @Mock 
    private SecurityContext mockSecurityContext;

    @Before
    public void setUp() throws Exception {
        //TODO
        Authentication authentication =  null;
        
        // Set mock behaviour/expectations on the mockSecurityContext
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(authentication);
        
        // Tell mockito to use Powermock to mock the SecurityContextHolder
        PowerMockito.mockStatic(SecurityContextHolder.class);

        // use Mockito to set up your expectation on SecurityContextHolder.getSecurityContext()
        Mockito.when(SecurityContextHolder.getContext()).thenReturn(mockSecurityContext);
        
    }

    @Test
    public void test() {
        
        
//        boolean allowed = iamPermissionEvaluator.hasPermission(authentication, targetDomainObject);
//        Assert.assertTrue("hasPermission", allowed);
        
        Assert.assertEquals("", true, true);
    }

}
