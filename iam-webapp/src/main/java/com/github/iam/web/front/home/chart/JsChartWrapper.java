package com.github.cunvoas.iam.web.front.home.chart;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.github.cunvoas.iam.bo.IamDashboardItem;

public abstract class JsChartWrapper {
    
    /**
     * Warp Business to json structure.
     * @param items
     * @return
     */
    public static List<JsChartData> wrap(List<IamDashboardItem> items) {
        List<JsChartData> wrapped = new ArrayList<JsChartData>();
        DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        
        Calendar calendarPrevDay = Calendar.getInstance();
        calendarPrevDay.set(Calendar.MINUTE, 0);
        calendarPrevDay.set(Calendar.SECOND, 0);
        calendarPrevDay.set(Calendar.MILLISECOND, 0);
        calendarPrevDay.add(Calendar.DAY_OF_MONTH, -1);
        
        for (IamDashboardItem item : items) {
            JsChartData datum = new JsChartData();
            datum.setGranted(String.valueOf(item.getNbVector()));
            datum.setRefused(String.valueOf(item.getNbVectorRedused()));
            datum.setUnique(String.valueOf(item.getNbNewUser()));
            
            int hour = item.getSegment();
            Calendar cal = null;
            if (hour>curHour) {
                // hour is on the previous day
                cal = calendarPrevDay;
            } else {
                cal = calendar;
            }
            cal.set(Calendar.HOUR_OF_DAY, hour);
            
            datum.setHour(dFormat.format(cal.getTime()));
            
            wrapped.add(datum);
        }
        
        return wrapped;
    }
}
