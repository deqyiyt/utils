package com.ias.common.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.ias.common.utils.number.NumberUtils;
import com.ias.common.utils.string.StringUtil;

/**
 *********************************************** 
 * @Copyright (c) by ysc All right reserved.
 * @Create Date: 2014-2-24 下午2:16:08
 * @Create Author: hujz
 * @File Name: TimeUtil
 * @Function: 对Java中的时间处理进行封装 
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class TimeUtil extends DateFormatUtils{
	
	private TimeUtil() {}
	
	public static void main(String[] args) {
		Calendar cal = TimeUtil.toCalendar("2016-8-19 20:58:00");
		System.out.println("获取本周一日期:" + toString(getMondayOFWeek(cal)));
		System.out.println("获取本周日的日期~:" + toString(getCurrentWeekday(cal)));
		System.out.println("获取上周一日期:" + toString(getPreviousWeekday(cal)));
		System.out.println("获取上周日日期:" + toString(getPreviousWeekSunday(cal)));
		System.out.println("获取下周一日期:" + toString(getNextMonday(cal)));
		System.out.println("获取下周日日期:" + toString(getNextSunday(cal)));
		System.out.println("获取本月第一天日期:" + toString(getFirstDayOfMonth(cal)));
		System.out.println("获取本月最后一天日期:" + toString(getDefaultDay(cal)));
		System.out.println("获取上月第一天日期:" + toString(getPreviousMonthFirst(cal)));
		System.out.println("获取上月最后一天的日期:" + toString(getPreviousMonthEnd(cal)));
		System.out.println("获取下月第一天日期:" + toString(getNextMonthFirst(cal)));
		System.out.println("获取下月最后一天日期:" + toString(getNextMonthEnd(cal)));
		System.out.println("获取本年的第一天日期:" + toString(getCurrentYearFirst(cal)));
		System.out.println("获取本年最后一天日期:" + toString(getCurrentYearEnd(cal)));
		System.out.println("获取去年的第一天日期:" + toString(getPreviousYearFirst(cal)));
		System.out.println("获取去年的最后一天日期:" + toString(getPreviousYearEnd(cal)));
		System.out.println("获取明年第一天日期:" + toString(getNextYearFirst(cal)));
		System.out.println("获取明年最后一天日期:" + toString(getNextYearEnd(cal)));
		System.out.println("获取本季度第一天到最后一天:" + toString(getThisSeasonTime(cal)[0])+","+toString(getThisSeasonTime(cal)[1]));
		System.out.println("获取两个日期之间间隔天数2016-8-19~2016-9.29:"
				+ getTwoDay(TimeUtil.toString(Calendar.getInstance()),"2016-8-19 20:58:00"));
		
		cal.add(Calendar.DATE, 100);
		System.out.println(TimeUtil.toString(cal));
	}
	

    /**
     * 秒的定义
     */
    public static final long SECOND = 1000;

    /**
     *分的定义
     */
    public static final long MINUTE = SECOND * 60;

    /**
     * 小时的定义
     */
    public static final long HOUR = MINUTE * 60;

    /**
     * 日的定义
     */
    public static final long DAY = HOUR * 24;

    /**
     * 周的定义
     */
    public static final long WEEK = DAY * 7;

    /**
     * 时间格式：yyyy-MM-dd HH:mm:ss。
     */
    public final static String theTimeFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式：yyyyMMddHHmmss。
     */
    public final static String otherTimeFormat = "yyyyMMddHHmmss";

    /**
     * 时间格式：yyyyMM。
     */
    public final static String yearMonthTimeFormat = "yyyyMM";

    /**
     * 时间格式：yyyyMMdd。
     */
    public final static String yearMonthDayTimeFormat = "yyyyMMdd";
    
    /**
     * 时间格式：yyyy-MM-dd。
     */
    public final static String yearMonthDayFormat = "yyyy-MM-dd";

    /**
     * 是否使用存放各种时间格式的集合自定义标志
     */
    public static final boolean useFastDateFormatter = true;

    /**
     * 格式化和解析日期的对象
     */
    private final static SimpleDateFormat theTimeFormator = new SimpleDateFormat(theTimeFormat);
    
    /**
	 * 日期格式化对象，将当前日期格式化成ddHHmmss格式，用于生成文件名。
	 */
	public final static DateFormat nameDf = new SimpleDateFormat("ddHHmmss");
	
	/**
	 * 日期格式化对象，将当前日期格式化成yyyyMM格式，用于生成目录。
	 */
	public static final DateFormat pathDf = new SimpleDateFormat(yearMonthTimeFormat);
    
    /**
     * 存放各种时间格式的集合自定义
     */
    private static Map<String, Object> theFastTimeFormatterMap = new HashMap<String, Object>();

    /**
     * 日期/时间格式化子类对象的实现
     * 
     * @param format 时间格式
     * @return 日期/时间格式化子类对象
     */
    public static final DateFormat getTimeFormatter(String format) {
        if(useFastDateFormatter) {
            DateFormat sdf = (DateFormat) theFastTimeFormatterMap.get(format);
            if(sdf != null) {
                return sdf;
            }
        }

        return new SimpleDateFormat(format);
    }


    /**
     * 格式化和解析日期的对象获取
     * 
     * @param format 时间格式
     * @return 格式化和解析日期的对象
     */
    public static SimpleDateFormat newTimeFormatter(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 判断时间是否相等。 由于系统中的时间不需要很高的精度，因此我们提供了特定的比较方法。
     * 
     * @param c1 判断时间对象是否相等的第1个参数
     * @param c2 判断时间对象是否相等的第2个参数
     * @return =true：相等 =false：不相等
     */
    public static boolean equals(Calendar c1, Calendar c2) {
        if(c1 == c2) {
            return true;
        }
        if((null == c1) || (null == c2)) {
            return false;
        }

        final long t1 = c1.getTime().getTime() / 1000;
        final long t2 = c2.getTime().getTime() / 1000;
        return (t1 == t2);
    }

    /**
     * Calendar对象克隆。
     * 
     * @param cal 原始时间对象
     * @return 已经克隆好的时间对象
     */
    public static Calendar clone(Calendar cal) {
        Calendar c = Calendar.getInstance();
        c.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
                .get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal
                .get(Calendar.MINUTE), cal.get(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND));
        return c;
    }

    // /////////////////////////////////////////////////////////////////
    
    /**
	 * 获取系统时间Timestamp
	 * @return
	 */
	public static Date getSysTimestamp(){
		return Calendar.getInstance().getTime();
	}
    
    /**
     * 按照formator格式，把cal对象转换成字符串
     * 
     * @param cal 需要转换的时间对象
     * @param formator 转换的时间格式
     * @return 时间对象的格式字符串
     */
    public static String toString(Calendar cal, DateFormat formator) {
        if(null == cal) {
            return "";
        }
        return formator.format(cal.getTime());
    }

    /**
     * 按照formator格式，把cal对象转换成字符串
     * 
     * @param cal 需要转换的时间对象
     * @param format 转换的时间格式
     * @return 时间对象的格式字符串
     */
    public static String toString(Calendar cal, String format) {
        if(null == cal) {
            return "";
        }
        return toString(cal, getTimeFormatter(format));
    }

    /**
     *按照formator格式，把date对象转换成字符串
     * 
     * @param date 需要转换的时间对象
     * @param formator 转换的时间格式
     * @return 时间对象的格式字符串
     */
    public static String toString(Date date, DateFormat formator) {
        if(null == date) {
            return "";
        }
        return formator.format(date);
    }

    /**
     *按照format格式，把date对象转换成字符串
     * 
     * @param date 需要转换的时间对象
     * @param format 转换的时间格式
     * @return 时间对象的格式字符串
     */
    public static String toString(Date date, String format) {
        if(null == date) {
            return "";
        }
        if(StringUtil.isEmpty(format)) {
            format = "yyyy-MM-dd";
        }
        return toString(date, getTimeFormatter(format));
    }

    /**
     * 把系统当前时间按照指定的时间格式进行转换
     * 
     * @param format 时间格式
     * @return 当前时间格式字符串
     */
    public static String toString(String format) {
        return toString(Calendar.getInstance(), format);
    }

    /**
     * 把指定时间从一种格式转换成指定的另外一种时间格式
     * 
     * @param time 指定的时间
     * @param fromFormat 元时间格式
     * @param toFormat 目标时间格式
     * @return 已经转换成目标时间格式的字符串
     * @throws Exception 转换失败错误
     */
    public static String toString(String time, String fromFormat,
            String toFormat) {
        try {
            return toString(toCalendar(time, fromFormat), toFormat);
        }catch(Exception e) {
            return time;
        }
    }

    /**
     * 把时间对象转换成yyyy-MM-dd HH:mm:ss格式
     * 
     * @param cal 转换的时间
     * @return 时间格式是yyyy-MM-dd HH:mm:ss的字符串
     */
    public static String toString(Calendar cal) {
        return toString(cal, theTimeFormator);
    }

    /**
     * 把时间对象转换成yyyy-MM-dd HH:mm:ss格式
     * 
     * @param date 转换的时间
     * @return 时间格式是yyyy-MM-dd HH:mm:ss的字符串
     */
    public static String toString(Date date) {
        return toString(date, theTimeFormator);
    }

    /**
     * 把毫秒对象转换成yyyy-MM-dd HH:mm:ss格式
     * 
     * @param millSeconds 转换的毫秒
     * @return 时间格式是yyyy-MM-dd HH:mm:ss的字符串
     */
    public static String toString(long millSeconds) {
        Date date = new Date(millSeconds);
        return toString(date);
    }

    /**
     * 把毫秒按照指定的格式进行时间转换
     * 
     * @param millSeconds 转换的毫秒
     * @param format 指定的时间格式
     * @return 时间格式是指定的字符串
     */
    public static String toString(long millSeconds, String format) {
        Date date = new Date(millSeconds);
        return toString(date, format);
    }

    // /////////////////////////////////////////////////////////////////
    /**
     * 将时间对象转化为数字
     * 
     * @param cal 转换的数字
     * @param format 格式，例如"yyyyMMddHH"
     * @return 类型是数字的时间格式
     */
    public static long toNumber(Calendar cal, String format) {
        String time = toString(cal, format);
        return Long.parseLong(time);
    }

    /**
     * 将时间对象转化为数字
     * 
     * @param strTime 转换的时间字符串
     * @param timeFormat 格式，例如"yyyyMMddHH"
     * @param numberFormat 要转换的数字格式
     * @return 类型是数字的时间格式
     * @throws Exception 转换失败错误
     */
    public static long toNumber(String strTime, String timeFormat,
            String numberFormat){
        try {
			return toNumber(toCalendar(strTime, timeFormat), numberFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
    }

    /**
     * 把Calendar对象转换成长整形数字
     * 
     * @param time 时间对象
     * @return 长整型时间数字对象
     */
    public static Long toLong(Calendar time) {
        try {
            return new Long(time.getTime().getTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把Date对象转换成长整形数字
     * 
     * @param time 时间对象
     * @return 长整型时间数字对象
     */
    public static Long toLong(Date time) {
        try {
            return new Long(time.getTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Date型转换为指定格式的long型
     * 
     * @param time Date型对象
     * @param format 指定格式
     * @return 转换后的时间值
     */
    public static long toLong(Date time, String format) {
        String str = toString(time, format);
        return NumberUtils.toLong(str);
    }

    /**
     * 获取Calendar对象的年、月、日
     * 
     * @param cal 时间对象
     * @return 已经转换后的long型数字
     */
    public static long toNumberOfyyyyMMdd(Calendar cal) {
        return (cal.get(Calendar.YEAR) * 100 + cal.get(Calendar.MONTH) + 1) * 100 + cal
                .get(Calendar.DAY_OF_MONTH);
    }

    // /////////////////////////////////////////////////////////////////
    /**
     * 将字符串转化为时间对象
     * 
     * @param time 转换的时间
     * @param formator 指定格式
     * @return 带指定格式的Calendar时间对象
     * @throws Exception 转换失败错误
     */
    public static Calendar toRawCalendar(String time, DateFormat formator)
            throws Exception {
        Calendar cal = Calendar.getInstance();
        java.util.Date d = formator.parse(time);
        cal.setTime(d);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    /**
     * 把字符串按照指定的转换格式转换为Calendar
     * 
     * @param time 时间字符串
     * @param formator 指定的格式
     * @return 带指定格式的Calendar对象
     */
    public static Calendar toCalendar(String time, DateFormat formator) {

        try {
            return toRawCalendar(time, formator);
        } catch(Exception ex) {
            ex.printStackTrace();
            return Calendar.getInstance();
        }
    }

    /**
     * 把字符串按照指定的转换格式转换为Calendar
     * 
     * @param time 时间字符串
     * @param format 指定的格式
     * @return 带指定格式的Calendar对象
     * @throws Exception 转换失败错误
     */
    public static Calendar toRawCalendar(String time, String format)
            throws Exception {
        return toRawCalendar(time, getTimeFormatter(format));
    }

    /**
     * 把字符串按照指定的转换格式转换为Calendar
     * 
     * @param time 时间字符串
     * @param format 指定的格式
     * @return 带指定格式的Calendar对象
     * @throws Exception 转换失败错误
     */
    public static Calendar toCalendar(String time, String format)
            throws Exception {
        return toRawCalendar(time, getTimeFormatter(format));
    }

    /**
     * 把long型数字按照指定格式转换为Calendar对象
     * 
     * @param time 时间数字
     * @param format 指定的格式
     * @return 带指定格式的Calendar对象
     * @throws java.lang.Exception 转换失败错误
     */
    public static Calendar toRawCalendar(long time, String format)
            throws Exception {
        return toRawCalendar(String.valueOf(time), getTimeFormatter(format));
    }

    /**
     * 把long型时间按照指定格式转换为Calendar对象
     * 
     * @param time long型时间值
     * @param format 指定的格式
     * @return 带指定格式的Calendar对象
     */
    public static Calendar toCalendar(long time, String format) {
        return toCalendar(String.valueOf(time), getTimeFormatter(format));
    }

    /**
     * 把字符串转换为yyyy-MM-dd HH:mm:ss格式的Calendar对象
     * 
     * @param time 字符串时间
     * @return 带指定格式的Calendar对象
     * @throws java.lang.Exception 转换错误
     */
    public static Calendar toRawCalendar(String time) throws Exception {
        return toRawCalendar(time, theTimeFormator);
    }

    /** 
     * 将未指定格式的日期字符串转化成java.util.Date类型日期对象 <br> 
     * @param date,待转换的日期字符串 
     * @return 
     * @throws ParseException 
     */  
    public static Calendar toCalendar(String date){
        String parse=date;  
        parse=parse.replaceFirst("^[0-9]{4}([^0-9]?)", "yyyy$1");  
        parse=parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");  
        parse=parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");  
        parse=parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");  
        parse=parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");  
        parse=parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");  
        parse=parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");
        return toCalendar(date,new SimpleDateFormat(parse));
    }

    /**
     * 生成位于所有参数范围之内的时间。 同时，调整参数以保证时间的语义符合参数语义，例如month为6而day为31时，将调整day为30。
     * 
     * @param year 2000~2010
     * @param month 1~12
     * @param day 1~31
     * @param hour 0~23
     * @param minute 0~59
     * @param second 0~59
     * @return
     */
    public static Calendar toBoundaryCalendar(int year, int month, int day,
            int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, NumberUtils.adjustRange(year, 2000, 2010));
        cal.set(Calendar.MONTH, NumberUtils.adjustRange(month, 1, 12) - 1);
        cal.set(Calendar.DAY_OF_MONTH, NumberUtils.adjustRange(day, 1, cal
                .getActualMaximum(Calendar.DAY_OF_MONTH)));
        cal.set(Calendar.HOUR_OF_DAY, NumberUtils.adjustRange(hour, 0, 23));
        cal.set(Calendar.MINUTE, NumberUtils.adjustRange(minute, 0, 59));
        cal.set(Calendar.SECOND, NumberUtils.adjustRange(second, 0, 59));
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    /**
     * 把字符串时间按照指定的分隔符分隔
     * 
     * @param time 字符串时间
     * @param delim 分割符
     * @return Date型时间对象
     * @throws Exception 转换失败错误
     */
    public static Date toRawStartRegularBoundaryDate(String time, String delim)
            throws Exception {
        return toRawRegularBoundaryDate(time, delim, 2000, 1, 1, 0, 0, 0);
    }

    /**
     * 把字符串时间按照指定的分隔符分隔
     * 
     * @param time 字符串时间
     * @param delim 分割符
     * @return Date型时间对象
     * @throws Exception 转换失败错误
     */
    public static Date toRawEndRegularBoundaryDate(String time, String delim)
            throws Exception {
        try {
            return toRawRegularBoundaryDate(time, delim, 2010, 12, 31, 23, 59,
                    59);
        } catch(Exception e) {
            throw new Exception(
                    "toRawRegularStartDate, failure, exception = " + e);
        }
    }

    /**
     * 把字符串时间按照指定的分隔符分隔
     * 
     * @param time 字符串时间
     * @param delim 分割符
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 小时
     * @param minute 分钟
     * @param second 秒
     * @return Date型时间对象
     * @throws java.lang.Exception 转换失败错误
     */
    public static Date toRawRegularBoundaryDate(String time, String delim,
            int year, int month, int day, int hour, int minute, int second)
            throws Exception {
        String[] set = StringUtil.split(time, delim);
        if(set.length <= 0) {
            throw new Exception(
                    "time format illegal, time = " + time + ", delim = " + delim);
        }

        if(set.length >= 1) {
            year = NumberUtils.toRawInt(set[0]);
        }
        if(set.length >= 2) {
            month = NumberUtils.toRawInt(set[1]);
        }
        if(set.length >= 3) {
            day = NumberUtils.toRawInt(set[2]);
        }

        Calendar cal = toBoundaryCalendar(year, month, day, hour, minute,
                second);
        return cal.getTime();
    }

    // /////////////////////////////////////////////////////////////////
    /**
     * 比较两个Calendar时间大小，并返回较小的时间值对象
     * 
     * @param time1 第一个Calendar
     * @param time2 第2个Calendar
     * @return 较小的Calendar时间对象
     */
    public static Calendar minTime(Calendar time1, Calendar time2) {
        if((null == time1) || (time1.after(time2))) {
            return time2;
        }
        return time1;
    }

    /**
     * 比较两个Date时间大小，并返回较小的时间值对象
     * 
     * @param time1 第一个Date
     * @param time2 第2个Date
     * @return 较小的Date时间对象
     */
    public static Date minTime(Date time1, Date time2) {
        if((null == time1) && (null == time2)) {
            return null;
        }
        if((null == time1) && (null != time2)) {
            return time2;
        }
        if((null != time1) && (null == time2)) {
            return time1;
        }
        if(time1.after(time2)) {
            return time2;
        }
        return time1;
    }

    /**
     * 比较两个Calendar时间大小，并返回较大的时间值对象
     * 
     * @param time1 第一个Calendar
     * @param time2 第2个Calendar
     * @return 较大的Calendar时间对象
     */
    public static Calendar maxTime(Calendar time1, Calendar time2) {
        if((null == time1) || (time1.before(time2))) {
            return time2;
        }
        return time1;
    }

    /**
     * 比较两个Date时间大小，并返回较大的时间值对象
     * 
     * @param time1 第一个Date
     * @param time2 第2个Date
     * @return 较大的Date时间对象
     */
    public static Date maxTime(Date time1, Date time2) {
        if((null == time1) && (null == time2)) {
            return null;
        }
        if((null == time1) && (null != time2)) {
            return time2;
        }
        if((null != time1) && (null == time2)) {
            return time1;
        }
        if(time1.before(time2)) {
            return time2;
        }
        return time1;
    }

    /**
     * 判断第1个Calendar对象时间是否在第2个Calendar时间对象之后
     * 
     * @param time1 第一个Calendar时间对象
     * @param time2 第2个时间对象
     * @return =true：第1个时间对象不在第2个时间对象之后 =false：第1个时间对象在第2个时间对象之后
     */
    public static boolean notAfter(Calendar time1, Calendar time2) {
        if((null != time1) && (null == time2)) {
            return false;
        }
        if((null == time1) && (null != time2)) {
            return false;
        }
        if((null != time1) && (null != time2) && (time1.after(time2))) {
            return false;
        }
        return true;
    }

    /**
     * 判断第1个Date对象时间是否在第2个Date间对象之后
     * 
     * @param time1 第一1个Date时间对象
     * @param time2 第2个时间对象
     * @return =true：第1个时间对象不在第2个时间对象之后 =false：第1个时间对象在第2个时间对象之后
     */
    public static boolean notAfter(Date time1, Date time2) {
        if((null != time1) && (null == time2)) {
            return false;
        }
        if((null == time1) && (null != time2)) {
            return false;
        }
        if((null != time1) && (null != time2) && (time1.after(time2))) {
            return true;
        }
        return false;
    }

    /**
     * 判断某个时间是否在一个时间区间范围内
     * 
     * @param time 需要判断的时间对象
     * @param start 区间开始时间
     * @param end 区间结束时间
     * @return =true 指定时间在这个区间内 =false 指定时间不再这个区间内
     */
    public static boolean equalOrBetween(Calendar time, Calendar start,
            Calendar end) {
        return (notAfter(start, time) && notAfter(time, end));
    }

    /**
     * 判断某个时间是否在一个时间区间范围内
     * 
     * @param time 需要判断的时间对象
     * @param start 区间开始时间
     * @param end 区间结束时间
     * @return =true 指定时间在这个区间内 =false 指定时间不再这个区间内
     */
    public static boolean equalOrBetween(Date time, Date start, Date end) {
        return (notAfter(start, time) && notAfter(time, end));
    }

    /**
     * 实现时间之间的分钟差.<br>
     *工程名:cctccati<br>
     *包名:com.soft.sb.util.type<br>
     *方法名:getTwoTimeMinute方法.<br>
     * 
     *@author:hujiuzhou@hotoa.com
     *@since :1.0:2009-8-14
     *@param stratTime：开始时间：yyyyMMddHHmmss
     *@param endTime:结束时间：yyyyMMddHHmmss
     *@return 返回两个时间的分钟差
     */
    public static String getTwoTimeMinute(String stratTime, String endTime) {
        SimpleDateFormat myFormatter = new SimpleDateFormat(otherTimeFormat);
        long minute = 0;
        long second=0;
        try {
            java.util.Date date = myFormatter.parse(stratTime);
            java.util.Date mydate = myFormatter.parse(endTime);
            minute = (date.getTime() - mydate.getTime()) / (60 * 1000);
            second = ((date.getTime() - mydate.getTime())/1000)-minute*60;
        } catch(Exception e) {
            return "";
        }
        return minute +"分钟"+second+ "秒";
    }
    
    /**
     * 
     * @Title: getRandomCalendar
     * @Description: 获取随机时间
     * @return 设定文件
     * @author hujiuzhou
     * @return Calendar 返回类型
     * @throws
     */
    public static Calendar getRandomCalendar() {
    	Random rand = new Random(); 
    	Calendar cal = Calendar.getInstance(); 
    	cal.set(2000, 0, 1); 
    	long start = cal.getTimeInMillis(); 
    	cal.set(2008, 0, 1); 
    	long end = cal.getTimeInMillis(); 
    	Date d = new Date(start + (long)(rand.nextDouble() * (end - start)));
    	cal.setTime(d);
    	return cal;
    }
    
	public static Double getTwoDay(String sj1, String sj2) {
		Double day = 0d;
		try {
			Long times = toCalendar(sj1).getTimeInMillis() - toCalendar(sj2).getTimeInMillis();
			day = times/86400000d;
		} catch (Exception e) {
			return 0d;
		}
		return day;
	}
	
	/**
	 * 获取两个时间的差值秒
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Integer getSecondBetweenDate(Date d1,Date d2){
		Long second=(d2.getTime()-d1.getTime())/1000;
		return second.intValue();
	}
	
	public static int getDaysBetweenDate(Date begin, Date end) {
		return (int) ((end.getTime()-begin.getTime())/(1000 * 60 * 60 * 24));
	}

	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	// 计算当月最后一天,返回字符串
	public static Date getDefaultDay() {
		return getDefaultDay(Calendar.getInstance());
	}
	
	// 计算月最后一天,返回字符串
	public static Date getDefaultDay(Calendar lastDate) {
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		lastDate.set(Calendar.HOUR_OF_DAY, 0);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);
		return lastDate.getTime();
	}

	// 上月第一天
	public static Date getPreviousMonthFirst(Calendar cal) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeInMillis(cal.getTimeInMillis());
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
		lastDate.set(Calendar.HOUR_OF_DAY, 0);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);
		return lastDate.getTime();
	}

	// 获取当月第一天
	public static Date getFirstDayOfMonth(Calendar cal) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeInMillis(cal.getTimeInMillis());
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.set(Calendar.HOUR_OF_DAY, 0);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);
		return lastDate.getTime();
	}

	// 获得本周星期日的日期
	public static Date getCurrentWeekday(Calendar cal) {
		int mondayPlus = getMondayPlus(cal);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(cal.getTimeInMillis());
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return currentDate.getTime();
	}

	/**
	 * 获得当前日期与本周日相差的天数
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getMondayPlus方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:46:41
	 * @return
	 */
	private static int getMondayPlus(Calendar cal) {
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(cal.getTimeInMillis());
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/**
	 * 获得本周一的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getMondayOFWeek方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:46:48
	 * @return
	 */
	public static Date getMondayOFWeek(Calendar cal) {
		int mondayPlus = getMondayPlus(cal);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(cal.getTimeInMillis());
		currentDate.add(Calendar.DATE, mondayPlus);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return currentDate.getTime();
	}

	/**
	 * 获得上周星期日的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getPreviousWeekSunday方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:47:04
	 * @return
	 */
	public static Date getPreviousWeekSunday(Calendar cal) {
		int mondayPlus = getMondayPlus(cal);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(cal.getTimeInMillis());
		currentDate.add(GregorianCalendar.DATE, mondayPlus - 1);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return currentDate.getTime();
	}

	/**
	 * 获得上周星期一的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getPreviousWeekday方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:47:11
	 * @return
	 */
	public static Date getPreviousWeekday(Calendar cal) {
		int mondayPlus = getMondayPlus(cal);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(cal.getTimeInMillis());
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * - 1);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return currentDate.getTime();
	}

	/**
	 * 获得下周星期一的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getNextMonday方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:47:18
	 * @return
	 */
	public static Date getNextMonday(Calendar cal) {
		int mondayPlus = getMondayPlus(cal);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(cal.getTimeInMillis());
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return currentDate.getTime();
	}

	/**
	 * 获得下周星期日的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getNextSunday方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:47:26
	 * @return
	 */
	public static Date getNextSunday(Calendar cal) {
		int mondayPlus = getMondayPlus(cal);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(cal.getTimeInMillis());
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return currentDate.getTime();
	}
	
	/**
	 * 获得上月最后一天的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getPreviousMonthEnd方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:47:33
	 * @return
	 */
	public static Date getPreviousMonthEnd(Calendar cal) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeInMillis(cal.getTimeInMillis());
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		lastDate.set(Calendar.HOUR_OF_DAY, 0);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);
		return lastDate.getTime();
	}

	/**
	 * 获得下个月第一天的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getNextMonthFirst方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:47:41
	 * @return
	 */
	public static Date getNextMonthFirst(Calendar cal) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeInMillis(cal.getTimeInMillis());
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.set(Calendar.HOUR_OF_DAY, 0);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);
		return lastDate.getTime();
	}

	/**
	 * 获得下个月最后一天的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getNextMonthEnd方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:47:49
	 * @return
	 */
	public static Date getNextMonthEnd(Calendar cal) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeInMillis(cal.getTimeInMillis());
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		lastDate.set(Calendar.HOUR_OF_DAY, 0);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);
		return lastDate.getTime();
	}

	/**
	 * 获得明年最后一天的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getNextYearEnd方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:47:56
	 * @return
	 */
	public static Date getNextYearEnd(Calendar cal) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeInMillis(cal.getTimeInMillis());
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		lastDate.set(Calendar.HOUR_OF_DAY, 0);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);
		return lastDate.getTime();
	}

	/**
	 * 获得明年第一天的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getNextYearFirst方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:48:03
	 * @return
	 */
	public static Date getNextYearFirst(Calendar cal) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeInMillis(cal.getTimeInMillis());
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.set(Calendar.HOUR_OF_DAY, 0);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);
		return lastDate.getTime();
	}

	/**
	 * 获得本年有多少天
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getMaxYear方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:48:28
	 * @return
	 */
	public static int getMaxYear(Calendar cal) {
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(cal.getTimeInMillis());
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private static int getYearPlus(Calendar cal) {
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(cal.getTimeInMillis());
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	/**
	 * 获得本年第一天的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getCurrentYearFirst方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:48:40
	 * @return
	 */
	public static Date getCurrentYearFirst(Calendar cal) {
		int yearPlus = getYearPlus(cal);
		GregorianCalendar currentDate = new GregorianCalendar();
		cal.setTimeInMillis(cal.getTimeInMillis());
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return currentDate.getTime();
	}

	/**
	 * 获得本年最后一天的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getCurrentYearEnd方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:48:53
	 * @return
	 */
	public static Date getCurrentYearEnd(Calendar cal) {
		String str = toString(cal,"yyyy") + "-12-31";
		Calendar currentDate = toCalendar(str);
		return currentDate.getTime();
	}

	/**
	 * 获得上年第一天的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getPreviousYearFirst方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:49:01
	 * @return
	 */
	public static Date getPreviousYearFirst(Calendar cal) {
		String years = toString(cal,"yyyy");
		int years_value = NumberUtils.toInt(years);
		years_value--;
		return toCalendar(years_value + "-01-01").getTime();
	}

	/**
	 * 获得上年最后一天的日期
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method getPreviousYearEnd方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:49:08
	 * @return
	 */
	public static Date getPreviousYearEnd(Calendar cal) {
		int yearPlus = getYearPlus(cal);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(cal.getTimeInMillis());
		currentDate.add(GregorianCalendar.DATE, yearPlus + 0 * 0 + (0 - 1));
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return currentDate.getTime();
	}
	
	/**
	 * 获得本季度
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:49:14
	 * @return
	 */
	public static Date[] getThisSeasonTime(int year, int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];
		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(year, end_month);
		return new Date[]{toCalendar(year + "-" + start_month + "-" + start_days).getTime() , toCalendar(year + "-" + end_month + "-" + end_days).getTime()};
	}

	/**
	 * 获得本季度
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:49:14
	 * @return
	 */
	public static Date[] getThisSeasonTime(Calendar cal) {
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		return getThisSeasonTime(year, month);
	}

	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}
	
	/**
	 * 判断是否运年
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method isLeapYear方法.<br>
	 * @author 胡久洲
	 * @date 2014-5-8 下午9:49:24
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
	
	/**
	 * 
	 * @param date
	 *            指定比较日期
	 * @param compareDate
	 * @return
	 */
	public static boolean isInDate(Date date, Date compareDate) {
		if (compareDate.after(getStartDate(date))
				&& compareDate.before(getFinallyDate(date))) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 得到指定日期的一天的的最后时刻23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFinallyDate(Date date) {
		String temp = toString(date);
		temp += " 23:59:59";
		return toCalendar(temp).getTime();
	}

	/**
	 * 得到指定日期的一天的开始时刻00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		String temp = toString(date);
		temp += " 00:00:00";
		return toCalendar(temp).getTime();
	}
	
	public static String formatDate(Date date){
		return DateFormat.getDateInstance().format(date);
	}
	
	public static String formatTime(Date date){
		return DateFormat.getTimeInstance().format(date);
	}
	
	public static String formatDateTime(Date date){
		if(DateFormat.getDateTimeInstance().format(date).contains("0:00:00")){
			return formatDate(date);
		}
		return DateFormat.getDateTimeInstance().format(date);
	}
	
	/**
	 * 转换为HH:mm:ss
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method parseTime方法.<br>
	 * @author hjz
	 * @date 2014-12-19 上午10:25:43
	 * @param time
	 * @return
	 */
	public static Date parseTime(Date time){
		DateFormat format = new SimpleDateFormat("HH:mm:ss"); 
		Date result=time;
		try {
			result = format.parse(formatTime(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return result;
	}
	
	/**
	 * 转换为yyyy-dd-mm
	 * @Project SC
	 * @Package com.ias.common.utils.type
	 * @Method parseDate方法.<br>
	 * @author hjz
	 * @date 2014-12-19 上午10:25:23
	 * @param time
	 * @return
	 */
	public static Date parseDate(Date time){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Date result=time;
		try {
			result = format.parse(formatDate(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return result;
	}
	
	public static Date getSpecficDateStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, amount);
		return getStartDate(cal.getTime());
	}
	
	/**
	 * 获取date周后的第amount周的开始时间（这里星期一为一周的开始）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getStartDate(cal.getTime());
	}
	
	/**
	 * 获取date月后的amount月的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getStartDate(cal.getTime());
	}
	
	/**
	 * 获取当前自然月后的amount月的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthEnd(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getSpecficMonthStart(date, amount + 1));
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}
	
	/**
	 * 获取date年后的amount年的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return getStartDate(cal.getTime());
	}
	
	/**
	 * 获取date年后的amount年的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearEnd(Date date, int amount) {
		Date temp = getStartDate(getSpecficYearStart(date, amount + 1));
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}
	
	/** 
	 * 将秒数转为*天*小时*分*秒返回
	 * @author: jiuzhou.hu
	 * @date:2017年4月27日上午10:43:54 
	 * @param mss
	 * @return
	 */
	public static String formatDateTime(long mss) {
		StringBuffer dateTimes = new StringBuffer();
		long days = mss / ( 60 * 60 * 24);
		long hours = (mss % ( 60 * 60 * 24)) / (60 * 60);
		long minutes = (mss % ( 60 * 60)) /60;
		long seconds = mss % 60;
		if(days>0){
			dateTimes.append(days).append("天");
		}else if(hours>0){
			dateTimes.append(hours).append("小时");
		}else if(minutes>0){
			dateTimes.append(minutes).append("分钟");
		}else{
			dateTimes.append(seconds).append("秒");
		}

		return dateTimes.toString();
	}
	
	/*
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime(Long ms) {
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;
		
		Long day = ms / dd;
		Long hour = (ms - day * dd) / hh;
		Long minute = (ms - day * dd - hour * hh) / mi;
		Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
		
		StringBuffer sb = new StringBuffer();
		if(day > 0) {
		    sb.append(day + "天");
		}
		if(hour > 0) {
		    sb.append(hour + "小时");
		}
		if(minute > 0) {
		    sb.append(minute + "分");
		}
		if(second > 0) {
		    sb.append(second + "秒");
		}
		if(milliSecond > 0) {
		    sb.append(milliSecond + "毫秒");
		}
		return sb.toString();
    }
    
	/*
     * 毫秒转化天时分
     */
    public static String formatMinuteTime(Long ms) {
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;
		
		Long day = ms / dd;
		Long hour = (ms - day * dd) / hh;
		Long minute = (ms - day * dd - hour * hh) / mi;
		
		StringBuffer sb = new StringBuffer();
		if(day > 0) {
		    sb.append(day + "天");
		}
		if(hour > 0) {
		    sb.append(hour + "小时");
		}
		if(minute > 0) {
		    sb.append(minute + "分钟");
		}else{
			sb.append("1分钟内");
		}
		return sb.toString();
    }
	
	public static class RelativeDateFormat {

		private static final long ONE_MINUTE = 60000L;
		private static final long ONE_HOUR = 3600000L;
		private static final long ONE_DAY = 86400000L;
		private static final long ONE_WEEK = 604800000L;

		private static final String ONE_SECOND_AGO = "秒前";
		private static final String ONE_MINUTE_AGO = "分钟前";
		private static final String ONE_HOUR_AGO = "小时前";
		private static final String ONE_DAY_AGO = "天前";
		private static final String ONE_MONTH_AGO = "月前";
		private static final String ONE_YEAR_AGO = "年前";

		public static void main(String[] args) throws ParseException {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
			Date date = format.parse("2013-11-11 18:35:35");
			System.out.println(format(date));
			System.out.println(formatDateTime(2852959));
		}

		public static String format(Date date) {
			long delta = new Date().getTime() - date.getTime();
			if (delta < 1L * ONE_MINUTE) {
				long seconds = toSeconds(delta);
				return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
			}
			if (delta < 45L * ONE_MINUTE) {
				long minutes = toMinutes(delta);
				return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
			}
			if (delta < 24L * ONE_HOUR) {
				long hours = toHours(delta);
				return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
			}
			if (delta < 48L * ONE_HOUR) {
				return "昨天";
			}
			if (delta < 30L * ONE_DAY) {
				long days = toDays(delta);
				return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
			}
			if (delta < 12L * 4L * ONE_WEEK) {
				long months = toMonths(delta);
				return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
			} else {
				long years = toYears(delta);
				return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
			}
		}

		private static long toSeconds(long date) {
			return date / 1000L;
		}

		private static long toMinutes(long date) {
			return toSeconds(date) / 60L;
		}

		private static long toHours(long date) {
			return toMinutes(date) / 60L;
		}

		private static long toDays(long date) {
			return toHours(date) / 24L;
		}

		private static long toMonths(long date) {
			return toDays(date) / 30L;
		}

		private static long toYears(long date) {
			return toMonths(date) / 365L;
		}

	}
}
