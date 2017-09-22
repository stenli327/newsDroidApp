package com.news.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	/**
	 * Return a time zone of the server
	 * 
	 * @return {@link TimeZone}
	 */
	private static TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Helsinki"); //FIXME
	
	
	public static final TimeZone getTimeZone(){
		return TIME_ZONE;
	}
	
	/**
	 * @param pattern
	 * @return SimpleDateFormat with application default time zone
	 */
	public static final SimpleDateFormat getSimpleDateFormat(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(getTimeZone());
		
		return sdf;
	}
	
	public static java.sql.Timestamp dateToSqlTimestamp(Date date) {
		if (date == null) {
			return null;
		}
		
		if (date instanceof java.sql.Timestamp) {
			return (java.sql.Timestamp)date.clone();
		}
		
		return new java.sql.Timestamp(date.getTime());
	}
	
	/**
	 * Checks if a given string is valid as a date
	 * @param string date
	 * @return true/false
	 */
	public static boolean isValidDate(String date)
	{
		String dateFormat = ""; //TODO
	    SimpleDateFormat sdf = getSimpleDateFormat(dateFormat);
	    Date testDate = null;

	    try
	    {
	      testDate = sdf.parse(date);
	    }
	    catch (ParseException e)
	    {
	      return false;
	    }

	    if (!sdf.format(testDate).equals(date)) 
	    {
	      return false;
	    }

	    return true;

	}
	
	public static final Calendar getCalendarInstance() {
		return Calendar.getInstance(getTimeZone());
	}
	
	/**
	 * Compare include only year, month and day.
	 * @param date1 first date;
	 * @param date2 second date;
	 * @return -1 date1 is less than date2;<br>0 date1 is equal to date2<br>1 date1 is bigger than date2.
	 */
	public static Integer compareDate(Date date1, Date date2){
	    Calendar calendar1 = getCalendarInstance();
	    calendar1.setTime(date1);
	    Calendar calendar2 = getCalendarInstance();
	    calendar2.setTime(date2);
	    return compareDate(calendar1, calendar2);
	}
	
	public static Integer compareDate(Date date1, Calendar calendar2){
	    Calendar calendar1 = getCalendarInstance();
	    calendar1.setTime(date1);
	    return compareDate(calendar1, calendar2);
	}
	
	public static Integer compareDate(Calendar calendar1, Date date2){
	    Calendar calendar2 = getCalendarInstance();
	    calendar2.setTime(date2);
	    return compareDate(calendar1, calendar2);
	}
	
	/**
	 * Compare include only year, month and day.
	 * @param date1 first date;
	 * @param date2 second date;
	 * @return -1 date1 is less than date2;<br>0 date1 is equal to date2<br>1 date1 is bigger than date2.
	 */
	public static Integer compareDate(Calendar calendar1, Calendar calendar2){
	    if (calendar1.get(Calendar.YEAR) < calendar2.get(Calendar.YEAR)){
	    	return -1; // date1 < date2
	    }
	    else if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)){
		    if (calendar1.get(Calendar.DAY_OF_YEAR) < calendar2.get(Calendar.DAY_OF_YEAR)){
		    	return -1; // date1 < date2
		    }	
		    else if (calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)){
		    	return 0; // date1 = date2
		    }
		    else {
		    	return 1; // date1 > date2
		    }
	    }
	    else {
	    	return 1; // date1 > date2
	    }
	}
	
}
