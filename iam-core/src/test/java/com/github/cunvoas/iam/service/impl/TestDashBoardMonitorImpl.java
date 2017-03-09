package com.github.cunvoas.iam.service.impl;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.cunvoas.iam.bo.IamDashboard;
import com.github.cunvoas.iam.bo.IamDashboardItem;

public class TestDashBoardMonitorImpl {

    
    private DashBoardMonitorImpl tested;
    private Calendar calendar;
    @Before
    public void setUp() throws Exception {
        tested = new DashBoardMonitorImpl();
        calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    @Test
    public void testChangeHour() {
        int currHour =  calendar.get(Calendar.HOUR_OF_DAY);
        IamDashboardItem item = tested.getActivityByHour().get(0);
        Assert.assertEquals("HOUR START", currHour, item.getSegment());
        
        for (int i = 1; i <50; i++) {
            tested.changeHour();
            item = tested.getActivityByHour().get(0);
            Assert.assertEquals("HOUR +"+i, (currHour+i)%24, item.getSegment());
        }
    }

    @Test
    public void testChangeDay() {
        tested.changeDay();
        Assert.assertTrue("empty uniqe user id", tested.uniqueUserId.isEmpty());
    }
    

    @Test
    public void testIncrementIamVextor() {
        tested.incrementIamVextor("USER_TEST");
        IamDashboardItem item2 = tested.getActivityByHour().get(0);
        
        Assert.assertEquals("NB VECTOR", 1, item2.getNbVector());
        Assert.assertEquals("UNIQUE FIRST", 1,  tested.uniqueUserId.size());
        
        tested.incrementIamVextor("USER_TEST");
        item2 = tested.getActivityByHour().get(0);
        Assert.assertEquals("NB VESCTOR", 2, item2.getNbVector());
        Assert.assertEquals("UNIQUE SECOND", 1,  tested.uniqueUserId.size());
    }

    @Test
    public void testIncrementDelegationDemand() {
        
        tested.incrementDelegationDemand();
        IamDashboardItem item2 = tested.getActivityByHour().get(0);
        
        Assert.assertEquals("NB VESCTOR", 1, item2.getNbNewDelegationDemand());
    }

    @Test
    public void testIncrementAdminActivity() {
        IamDashboardItem item = tested.getActivityByHour().get(0);
        Assert.assertEquals("NB VESCTOR", 0, item.getNbAdminActivity());
        
        tested.incrementAdminActivity();
        IamDashboardItem item2 = tested.getActivityByHour().get(0);
        
        Assert.assertEquals("NB VESCTOR", 1, item2.getNbAdminActivity());
    }

    @Test
    public void testIncrementIamVectorRefused() {
        
        tested.incrementIamVectorRefused();
        IamDashboardItem item2 = tested.getActivityByHour().get(0);
        
        Assert.assertEquals("NB VESCTOR", 1, item2.getNbVectorRedused());
    }

    @Test
    public void testGetActivityOfDay() {
        tested.incrementAdminActivity();
        tested.incrementAdminActivity();
        tested.changeHour();
        tested.incrementAdminActivity();
        
        IamDashboard dashboard = tested.getActivityOfDay();
        
        Assert.assertEquals("DAY AdminActivity", 3, dashboard.getNbAdminActivity());
        
        
    }

}
