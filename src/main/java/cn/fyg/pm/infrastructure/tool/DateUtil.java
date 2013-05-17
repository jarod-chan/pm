package cn.fyg.pm.infrastructure.tool;

import java.util.Date;

import org.joda.time.DateTime;

public class DateUtil {
	
	public static Date nextDay(Date day){
		DateTime dateTime = new DateTime(day);   
		dateTime=dateTime.plusDays(1);
		return dateTime.toDate();   
	}

}
