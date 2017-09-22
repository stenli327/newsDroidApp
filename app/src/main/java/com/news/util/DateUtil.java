package com.news.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private static TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Helsinki"); //TODO

    //2017-09-22T09:18:04Z
    private static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static String CURRENT_DATE_PATTERN = "yyyy-MM-dd";

    public static TimeZone getTimeZone() {
        return TIME_ZONE;
    }

    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(getTimeZone());

        return sdf;
    }

    public static String customizeDate(String date){

        Date d = parseDateFromString(date);

        if(d != null){
            return dateToString(getSimpleDateFormat(CURRENT_DATE_PATTERN), d);
        }
        return "";
    }
    public static Date parseDateFromString(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);

        Date result;
        try {
            result = sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }

        return result;
    }

    public static Date parseDateFromString(SimpleDateFormat sdf, String date) {
        Date result;
        try {
            result = sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }

        return result;
    }

    public static String dateToString(SimpleDateFormat sdf, Date date) {
        return sdf.format(date);
    }
}
