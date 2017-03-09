package com.github.cunvoas.iam.web.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import com.github.cunvoas.iam.service.DashBoardMonitor;

/**
 * Aspect for tracking of administrator save activity.
 * @author CUNVOAS
 */
@Aspect
public class AdminActionTracker {

    private DashBoardMonitor dashBoardMonitor;

    /**
     * Setter for dashBoardMonitor.
     * 
     * @param dashBoardMonitor
     *            the dashBoardMonitor to set
     */
    public void setDashBoardMonitor(DashBoardMonitor dashBoardMonitor) {
        this.dashBoardMonitor = dashBoardMonitor;
    }
    
    @After("execution( * com.github.cunvoas.iam.web.front.*.*Controller.save*(..) )")
    public void trackMethodAfter(JoinPoint joinPoint) {
        dashBoardMonitor.incrementAdminActivity();
    }

}
