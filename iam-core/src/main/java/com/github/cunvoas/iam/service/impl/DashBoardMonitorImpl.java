package com.github.cunvoas.iam.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.cunvoas.iam.bo.IamDashboard;
import com.github.cunvoas.iam.bo.IamDashboardItem;
import com.github.cunvoas.iam.service.DashBoardMonitor;

@Component("DashBoardMonitor")
@Scope("singleton")
public class DashBoardMonitorImpl implements DashBoardMonitor {
    private static final int HOUR_IN_DAY=24;
    
    private List<IamDashboardItem> dayDashboardItems = new ArrayList<IamDashboardItem>(HOUR_IN_DAY);
    protected Set<String> uniqueUserId = new HashSet<String>();
    
     
    public DashBoardMonitorImpl() {
        for (int i = 0; i < HOUR_IN_DAY; i++) {
            dayDashboardItems.add(new IamDashboardItem(i));
        }
        int rotateIdx = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Collections.rotate(dayDashboardItems, -rotateIdx);
    }
    
    private IamDashboardItem currentItem() {
        return dayDashboardItems.get(0);
    }
    
    /**
     * executed every hour (H:00:00).
     */
    @Scheduled(cron="0 0 * * * *")
    public void changeHour() {
        Collections.rotate(dayDashboardItems, -1);
        currentItem().clear();
    }
    
    /**
     *  executed every day (0:00:01).
     */
    @Scheduled(cron="1 0 0 * * *")
    public void changeDay() {
        uniqueUserId.clear();
        for (IamDashboardItem item : dayDashboardItems) {
            item.resetUniqueUser();
        }
    }
    
    /**
     * @see com.github.cunvoas.iam.service.DashBoardMonitor#incrementIamVextor(java.lang.String)
     */
    @Override
    public void incrementIamVextor(String userId) {
        currentItem().addNbVector();
        if (!uniqueUserId.contains(userId)) {
            uniqueUserId.add(userId);
            currentItem().addNbNewUser();
        }
    }

    /**
     * @see com.github.cunvoas.iam.service.DashBoardMonitor#incrementDelegationDemand()
     */
    @Override
    public void incrementDelegationDemand() {
        currentItem().addNbNewDelegationDemand();
    }

    /**
     * @see com.github.cunvoas.iam.service.DashBoardMonitor#incrementAdminActivity()
     */
    @Override
    public void incrementAdminActivity() {
        currentItem().addNbAdminActivity();
    }

    /**
     * @see com.github.cunvoas.iam.service.DashBoardMonitor#getActivityOfDay()
     */
    @Override
    public IamDashboard getActivityOfDay() {
        IamDashboard dayActivityDashboard = new IamDashboard();
        for (IamDashboardItem item : dayDashboardItems) {
            dayActivityDashboard.add(item);
        }
        return dayActivityDashboard;
    }

    /**
     * @see com.github.cunvoas.iam.service.DashBoardMonitor#getActivityByHour()
     */
    @Override
    public List<IamDashboardItem> getActivityByHour() {
        return dayDashboardItems;
    }

    /**
     * @see com.github.cunvoas.iam.service.DashBoardMonitor#incrementIamVectorRefused()
     */
    @Override
    public void incrementIamVectorRefused() {
        currentItem().addNbVectorRedused();
    }


}
