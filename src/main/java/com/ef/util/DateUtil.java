package com.ef.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ef.log.DurationType;

public class DateUtil {
	
	public final static SimpleDateFormat LOG_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	public static Date addTime(Date startDate, DurationType durationType) { // TODO change to enum
		Calendar cal = Calendar.getInstance();
	    
		cal.setTime(startDate);
		if (durationType == DurationType.hourly) {
			cal.add(Calendar.HOUR_OF_DAY, 1);
		} else if (durationType == DurationType.daily) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return cal.getTime();
	}

}
