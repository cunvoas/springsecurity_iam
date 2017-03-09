package com.github.cunvoas.iam.service;

import java.util.List;

import com.github.cunvoas.iam.bo.IamDashboard;
import com.github.cunvoas.iam.bo.IamDashboardItem;

/**
 * Monitoring IAM access Activity.
 * @author CUNVOAS
 */
public interface DashBoardMonitor {
    
    /**
     * Increment vector acces and new unique user.
     */
    void incrementIamVextor(String userId);
    
    void incrementIamVectorRefused();
    
    /**
     * new delegation demand.
     */
    void incrementDelegationDemand();
    
    /**
     * admin save activity
     */
    void incrementAdminActivity();
    
    IamDashboard getActivityOfDay();
    List<IamDashboardItem> getActivityByHour();
}
