package com.ias.common.utils.number;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.ias.common.utils.collection.ArrayUtils;
import com.ias.common.utils.string.StringUtil;

/**
 *********************************************** 
 * @Copyright (c) by ysc All right reserved.
 * @Create Date: 2014-2-24 下午2:15:19
 * @Create Author: hujz
 * @File Name: NumberUtil
 * @Function: 对Java中的数字处理进行封装
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class NumberUtils {

    /**
     * 数字格式化对象
     */
    private final static NumberFormat theNumberFormatter = NumberFormat
            .getNumberInstance();

    // /////////////////////////////////////////////////////////////////
    /**
     * 判断一个字符串是否为long型
     * 
     * @param str 被判断的字符串
     * @return =true 是long型的字符串 =false 不是long型的字符串
     */
    public static boolean isLong(String str) {
        @SuppressWarnings("unused")
        long l;
        try {
            l = Long.parseLong(str);
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断一个字符串是否为int型
     * 
     * @param str 被判断的字符串
     * @return =true 是int型的字符串 =false 不是int型的字符串
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 把浮点型转换成含有指定小数点位数的字符串，带千分位
     * 
     * @param num 浮点型数字
     * @param fractionDigit 小数点位数
     * @return 带指定小树位数的浮点字符串
     */
    public static String toString(Number num, int fractionDigit) {
    	if(num == null) {
    		num = 0;
    	}
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(1);
        nf.setMinimumFractionDigits(fractionDigit);
        nf.setMaximumFractionDigits(fractionDigit);

        return nf.format(num);
    }
    
    /**
     * 把浮点型转换成含有指定小数点位数的浮点型
     * 
     * @param num 浮点型数字
     * @param fractionDigit 小数点位数
     * @return 带指定小树位数的浮点字符串
     */
    public static float toFloat(Number num, int fractionDigit) {
        return toFloat(toString(num,fractionDigit).replace(",", ""));
    }

    /**
     * 把一个浮点型数字转换成字符串
     * 
     * @param num 要转换的浮点型数值
     * @return 带小数点的字符串
     */
    public static String toString(double num) {
        NumberFormat f = NumberFormat.getInstance();
        if(f instanceof DecimalFormat) {
            ((DecimalFormat) f).setDecimalSeparatorAlwaysShown(true);
        }
        f.setParseIntegerOnly(true);
        return f.format(num);
    }

    // /////////////////////////////////////////////////////////////////
    /**
     * 把一个字符串转换成整数
     * 
     * @param str 整型字符串
     * @return 整型数字
     */
    public static int toInt(String str) {
        return toInt(str, 0);
    }
    
    /**
     * 把一个Object串转换成整数
     * 
     * @param str 整型字符串
     * @return 整型数字
     */
    public static int toInt(Object str) {
        return toInt(str+"", 0);
    }

    /**
     * 把一个字符串转换成整数對象
     * 
     * @param str 整型字符串
     * @return 整型数字
     */
    public static Integer toIntObject(String str) {
        return new Integer(str);
    }

    /**
     * 把一个字符串转换成整数，如果字符串為空就返回指定整形值
     * 
     * @param str 整型字符串
     * @param defaultValue 默認的整型值
     * @return 整型数字
     */
    public static int toInt(String str, int defaultValue) {
        if(StringUtil.isEmpty(str)) {
            return defaultValue;
        }
        try {
            if(!isInteger(str)) {
                return defaultValue;
            }
            return toRawInt(str);
        } catch(Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 把一个字符串转换成整数
     * 
     * @param str 要转换的字符串
     * @return 整数
     * @throws java.lang.Exception 转换错误的返回值
     */
    public static int toRawInt(String str) throws Exception {
        return Integer.parseInt(str.trim());
    }

    public static int toFormattedInt(String str) {
        return toFormattedInt(str, 0);
    }

    /**
     * 把一个字符串转换成整数，如果字符串为空或转换错误就返回缺省值
     * 
     * @param str 被转换的字符串
     * @param defaultValue 缺省值
     * @return 转换后的整数
     */
    public static int toFormattedInt(String str, int defaultValue) {
        if(StringUtil.isEmpty(str)) {
            return defaultValue;
        }
        try {
            return toRawFormattedInt(str);
        } catch(Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 把一个字符串转换成整数
     * 
     * @param str 被转换的字符串
     * @return 转换后的整数
     * @throws java.lang.Exception 转换错误
     */
    public static int toRawFormattedInt(String str) throws Exception {
        return Integer
                .parseInt(theNumberFormatter.parse(str.trim()).toString());
    }

    // /////////////////////////////////////////////////////////////////
    
    /**
     * 把字符串转换为long型，转换失败就返回0L
     * 
     * @param str 需要转换的字符串
     * @return 转换后的Long型数字
     */
    public static long toLong(Object str) {
        return toLong(str+"");
    }
    /**
     * 把字符串转换为long型，转换失败就返回0L
     * 
     * @param str 需要转换的字符串
     * @return 转换后的Long型数字
     */
    public static long toLong(String str) {
        return toLong(str.replace(",", ""), 0L);
    }

    /**
     * 把字符串转换为Long型
     * 
     * @param str 需要转换的字符串
     * @return 转换后的long型数字
     */
    public static Long toLongObject(String str) {
        return new Long(str.trim());
    }

    /**
     * 把字符串转换为Long型，转换失败就返回指定缺省值
     * 
     * @param str 需要转换的字符串
     * @param defaultValue 指定的缺省字符串
     * @return 转换后的Long型数字
     */
    public static Long toLongObject(String str, long defaultValue) {
        if(StringUtil.isEmpty(str)) {
            return new Long(defaultValue);
        }
        return new Long(str.trim());
    }
    
    /**
     * 把object转换为Long型，转换失败就返回指定缺省值
     * 
     * @param str 需要转换的字符串
     * @param defaultValue 指定的缺省字符串
     * @return 转换后的Long型数字
     */
    public static Long toLong(Object object, long defaultValue) {
    	String str = object == null?"":object+"";
        return toLong(str, defaultValue);
    }

    /**
     * 把字符串转换为long型，转换失败就返回指定缺省值
     * 
     * @param str 需要转换的字符串
     * @param defaultValue 指定的缺省字符串
     * @return 转换后的long型数字
     */
    public static long toLong(String str, long defaultValue) {
        if(StringUtil.isEmpty(str)) {
            return defaultValue;
        }
        try {
            return toRawLong(str);
        } catch(Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 把字符串转换成long型
     * 
     * @param str 需要转换的字符串
     * @return 转换后的long型数字
     * @throws java.lang.Exception 转换失败错误
     */
    public static long toRawLong(String str) throws Exception {
        return Long.parseLong(str.trim());
    }

    /**
     * 把字符串转换为long型，转换错误就返回0L
     * 
     * @param str 需要转换的字符串
     * @return 转换后的字符串
     */
    public static long toFormattedLong(String str) {
        return toFormattedLong(str, 0L);
    }

    /**
     * 把字符串转换成long型，转换错误就返回指定的缺省值
     * 
     * @param str 需要转换的字符串
     * @param defaultValue 指定的缺省值
     * @return 转换后的long型数字
     */
    public static long toFormattedLong(String str, long defaultValue) {
        if(StringUtil.isEmpty(str)) {
            return defaultValue;
        }
        try {
            return toRawFormattedLong(str);
        } catch(Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 把字符串转换成long型
     * 
     * @param str 转换的字符串
     * @return 转换后的long型数字
     * @throws java.lang.Exception 转换失败的错误
     */
    public static long toRawFormattedLong(String str) throws Exception {
        return Long.parseLong(theNumberFormatter.parse(str.trim()).toString());
    }

    // /////////////////////////////////////////////////////////////////
    /**
     * 把字符串转换成浮点型数字，转换失败就返回0.0
     * 
     * @param str 转换的字符串
     * @return 转换后的浮点数字
     */
    public static double toDouble(Object str) {
    	str = str == null?"":str;
        return toDouble(str+"", 0.0);
    }
    
    /**
     * 指定给定的double的小数位数
     * 
     * @param num 浮点数
     * @param fractionDigit 小数位数
     * @return 指定小数位数的浮点数
     */
    public static double toDouble(double num, int fractionDigit) {
        String d = toString(num, fractionDigit);
        return toDouble(d, 0.00);
    }

    /**
     * 把字符串转换为浮点型，转换失败就返回指定缺省值
     * 
     * @param str 需转换的字符串
     * @param defaultValue 指定的缺省值
     * @return 转换好的浮点数字
     */
    public static double toDouble(String str, double defaultValue) {
        if(StringUtil.isEmpty(str)) {
            return defaultValue;
        }
        try {
            return toRawDouble(str);
        } catch(Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
    
    /**
     * 四舍五入
     * @param value
     * @param format 格式位数，"0.00"
     * gurui@ysc.com
     */
    public static double toDouble(double value,String format){
    	DecimalFormat df=new DecimalFormat(format);
        return new Double(df.format(value));
    }

    /**
     * 把字符串转换成浮点型数字
     * 
     * @param str 转换的字符串
     * @return 转换后的浮点数字
     * @throws Exception 转换失败错误
     */
    public static double toRawDouble(String str) throws Exception {
        return Double.parseDouble(str.trim());
    }

    /**
     * 把字符串转换成浮点型数字，转换失败就返回0.0
     * 
     * @param str 转换的字符串
     * @return 转换后的浮点数字
     */
    public static double toFormattedDouble(String str) {
        return toFormattedDouble(str, 0.0);
    }

    /**
     * 把字符串转换为浮点型数字，转换失败就返回缺省值
     * 
     * @param str 需要转换的字符串
     * @param defaultValue 转换的缺省值
     * @return 转换后的浮点型数字
     */
    public static double toFormattedDouble(String str, double defaultValue) {
        if(StringUtil.isEmpty(str)) {
            return defaultValue;
        }
        try {
            return toRawFormattedDouble(str);
        } catch(Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 把字符串转换为浮点型数字
     * 
     * @param str 需要转换的字符串
     * @return 浮点型数字
     * @throws java.lang.Exception 转换错误
     */
    public static double toRawFormattedDouble(String str) throws Exception {
        return Double.parseDouble(theNumberFormatter.parse(str.trim())
                .toString());
    }

    // /////////////////////////////////////////////////////////////////
    
    ///////////////////////////////////////////////////////////////////
    /**
     * @Project SB
     * @Package com.soft.sb.util.type
     * @Method toFloat方法.<br>
     * @Description 将Object转换为Float
     * @author 胡久洲
     * @date 2013-8-6 上午11:07:44
     * @param str
     * @return
     */
    public static float toFloat(Object str) {
        return toFloat(str+"", 0.0f);
    }
    /**
     * @Project SB
     * @Package com.soft.sb.util.type
     * @Method toFloat方法.<br>
     * @Description 把字符串转换为浮点型，转换失败就返回指定缺省值
     * @author 胡久洲
     * @date 2013-8-6 上午11:08:02
     * @param str 需转换的字符串
     * @param defaultValue 指定的缺省值
     * @return 转换好的浮点数字
     */
    public static float toFloat(String str, float defaultValue) {
        if(StringUtil.isEmpty(str)) {
            return defaultValue;
        }
        try {
            return toRawFloat(str);
        } catch(Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
    
    /**
     * @Project SB
     * @Package com.soft.sb.util.type
     * @Method toRawFloat方法.<br>
     * @Description 把字符串转换成浮点型数字
     * @author 胡久洲
     * @date 2013-8-6 上午11:08:18
     * @param str 转换的字符串
     * @return 转换后的浮点数字
     * @throws Exception 转换失败错误
     */
    public static float toRawFloat(String str) throws Exception {
        return Float.parseFloat(str.trim());
    }
    ///////////////////////////////////////////////////////////////////
    /**
     * 把整数数组转化成整数对象数组
     * 
     * @param set 整数数组
     * @return 证书对象数组
     */
    public static Integer[] toIntegerObjectSet(int[] set) {
        if(set == null) {
            return null;
        }
        Integer[] s = new Integer[set.length];
        for(int i = 0; i < set.length; i++) {
            s[i] = new Integer(set[i]);
        }
        return s;
    }

    // /////////////////////////////////////////////////////////////////
    /**
     * 将字符串按照delim分隔符进行分割，并转化成数组
     * 
     * @param str 字符串
     * @param delim 分隔符
     * @return 整数数组
     */
    public static int[] splitToIntSet(String str, String delim) {
        int[] intSet = new int[0];
        String[] strSet = StringUtil.split(str, delim);
        if(null == strSet) {
            return intSet;
        }
        for(int i = 0; i < strSet.length; i++) {
            try {
                intSet = ArrayUtils.addIntArray(intSet, toRawInt(strSet[i]));
            } catch(Exception e) {
            }
        }
        return intSet;
    }

    /**
     * 将set数组合并成以delim为分割符的字符串
     * 
     * @param set 整型数组
     * @param delim 分割符
     * @return 带delim分隔符的字符串
     */
    public static String join(int[] set, String delim) {
        if((null == set) || (set.length <= 0)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(set[0]);
        for(int i = 1; i < set.length; i++) {
            sb.append(delim);
            sb.append(set[i]);
        }
        return sb.toString();
    }

    // /////////////////////////////////////////////////////////////////

    /**
     * 调整整数的范围
     * 
     * @param old 被调整的整数值
     * @param min 调整最小值
     * @param max 调整最大值
     * @return 调整后的整数
     */
    public static final int adjustRange(int old, int min, int max) {
        if(old < min) {
            old = min;
        }
        if(old > max) {
            old = max;
        }
        return old;
    }

    /**
     * 调整整数的范围
     * 
     * @param old 被调整的整数值
     * @param min 调整最小值
     * @return 调整后的整数
     */
    public static final int adjustMinRange(int old, int min) {
        if(old < min) {
            old = min;
        }
        return old;
    }

    /**
     * 调整整数的范围
     * 
     * @param old 被调整的整数值
     * @param max 调整最大值
     * @return 调整后的整数
     */
    public static final int adjustMaxRange(int old, int max) {
        if(old > max) {
            old = max;
        }
        return old;
    }

    /**
     * 将数组转换为字符串。
     * 
     * @param array 长整型数组
     * @return 字符串
     */
    public final static String toString(long[] array) {
        if(null == array) {
            return "count=0:[]";
        }

        String s = "count=" + array.length + ":[ ";
        for(int i = 0; i < array.length; i++) {
            if(i > 0) {
                s += ", ";
            }
            s += array[i];
        }
        s += " ]";
        return s;
    }

    /**
     * 将整型数组转换成字符串
     * 
     * @param array 整型数组
     * @return 字符串
     */
    public final static String toString(int[] array) {
        if(null == array) {
            return "count=0:[]";
        }

        String s = "count=" + array.length + ":[ ";
        for(int i = 0; i < array.length; i++) {
            if(i > 0) {
                s += ", ";
            }
            s += array[i];
        }
        s += " ]";
        return s;
    }
    
    /** 
     * 去掉小数点后面不需要的0
     * @author: jiuzhou.hu
     * @date:2017年4月17日上午10:25:27 
     * @param num
     * @return
     */
    public static String doubleTrans(Double num){
    	if(num == null) {
    		return null;
    	}
    	String s = num + "";
    	if(s.indexOf(".") > 0){
    		//正则表达
			s = s.replaceAll("0+?$", "");//去掉后面无用的零
			s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
		}
    	return s;
    }
    
    public static boolean isDigits(final String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
