package com.ias.common.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private static SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
	private static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	
    /**
     * 根据commonSearch提供的时间类型进行格式化
     */
    public static String formatByTimeType(String date, String timeType) {
    	try {
			if (timeType.equals("year")) {
				date = sdfYear.format(sdf.parse(date));
			} else if (timeType.equals("month")) {
				date = sdfMonth.format(sdf.parse(date));
			} else if (timeType.equals("day")) {
				date = sdfDay.format(sdf.parse(date));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return date;
    }
    
    /**
     * 获取date一个月（30天）之前的时间
     */
    public static String getDateMonthAgo(String date) {
    	try {
			return sdf.format(Date.from(sdf.parse(date).toInstant().minusSeconds(30 * 24 * 60 * 60)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }

    /**
     * 获取date一年（365天）之前的时间
     */
    public static String getDateYearAgo(String date) {
    	try {
			return sdf.format(Date.from(sdf.parse(date).toInstant().minusSeconds(365 * 24 * 60 * 60)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }
}
