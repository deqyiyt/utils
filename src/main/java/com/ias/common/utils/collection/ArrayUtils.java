package com.ias.common.utils.collection;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ias.common.utils.bean.ClassUtil;
import com.ias.common.utils.number.NumberUtils;
import com.ias.common.utils.string.StringUtil;

/**
 *********************************************** 
 * @Copyright (c) by ysc All right reserved.
 * @Create Date: 2014-2-24 下午2:12:52
 * @Create Author: hujz
 * @File Name: ArrayUtil
 * @Function: 集合帮助类
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class ArrayUtils {

    /**
     * 判断对象数组是否为空
     * @param set 集合数组
     * @return =true：为空
     *          =false：非空
     */
    public static boolean empty(Object[] set) {
        return ((null == set) || (set.length <= 0));
    }

    /**
     * 判断对象数组是否为空
     * @param set 集合数组
     * @return =true：为空
     *          =false：非空
     */
	public static boolean empty(Collection<?> set) {
        return ((null == set) || (set.size() <= 0));
    }
    
    /**
     * 判断一个整数数组是否为空
     * @author zhili.yang
     * @date 2017年12月6日 上午9:32:50
     * @param set
     * @return
     */
    public static boolean empty(int[] set) {
        return ((null == set) || (set.length <= 0));
    }

	/**
	 * 判断一个集合是否非空，实际就是empty方法的非运算结果，提供更符合直觉的判断。
	 * @author zhili.yang
	 * @date 2017年12月5日 下午6:06:00
	 * @param set
	 */
	public static boolean isNotEmpty(Collection<?> set) {
		return !empty(set);
	}
	
    /**
     * 判断整数数组中是否包含某值
     * @param set 整数数组
     * @param val 包含的值
     * @return =true 包含某个值
     *          =false 不包含某个值
     */
    public static boolean has(int[] set, int val) {
        if (empty(set)) {
            return false;
        }
        for (int i = 0; i < set.length; i++) {
            if (set[i] == val) {
                return true;
            }
        }
        return false;
    }

    /**
     * 求出整数数组中的最大值
     * @param set 整数数组
     * @return 整数数组中的最大值
     */
    public static int max(int[] set) {
        if (empty(set)) {
            return 0;
        }
        int m = set[0];
        for (int i = 1; i < set.length; i++) {
            m = Math.max(m, set[i]);
        }
        return m;
    }

    /**
     * 求出整数数组中的最小值
     * @param set 整数数组
     * @return 整数数组中的最小值
     */
    public static int min(int[] set) {
        if (empty(set)) {
            return 0;
        }
        int m = set[0];
        for (int i = 1; i < set.length; i++) {
            m = Math.min(m, set[i]);
        }
        return m;
    }

    /**
     * 克隆字符串数组
     * @param set 元字符串数组
     * @return 克隆号的字符串数组
     */
    public static String[] clone(String[] set) {
        if (null == set) {
            return null;
        }
        String[] clone = new String[set.length];
        for (int i = 0; i < set.length; i++) {
            clone[i] = set[i];
        }
        return clone;
    }

    /**
     * 把整数数组转换成List数组
     * @param set 整数数组
     * @return List对象
     */
    public static List<Integer> toArrayList(int[] set) {
        List<Integer> list = new ArrayList<Integer>();
        if (!empty(set)) {
            for (int i = 0; i < set.length; i++) {
                int val = set[i];
                list.add(new Integer(val));
            }
        }
        return list;
    }
    
    /**
     * 把整数数组转换成List数组
     * @param set 整数数组
     * @return List对象
     */
    public static Integer[] toArray(Object[] set) {
    	Integer[] array = new Integer[set.length];
        if (!empty(set)) {
            for (int i = 0; i < set.length; i++) {
                array[i] = NumberUtils.toInt(set[i]);
            }
        }
        return array;
    }
    
    /**
     * 转换字符串数组
     * @param objs
     * @return
     */
    public static String[] changeObjectArray(Object objs[]) {
        String strs[] = new String[objs.length];
        System.arraycopy(((Object) (objs)), 0, strs, 0, objs.length);
        return strs;
    }
    
    /**
     * 把字符数组转换成字符串
     * @param array 字符数组
     * @return 转换后的字符串
     */
    public static String CharToString(char[] array) {
        return String.valueOf(array);
    }
    
    /**
     * 把整数数组转换成List数组
     * @param set 整数数组
     * @return List对象
     */
	public static Integer[] toArray(Collection<Integer> set) {
    	Integer[] array =new Integer[set.size()];
        if (!empty(set)) {
        	int index = 0;
        	for(Integer t:set) {
    			array[index] = t;
        		index ++;
        	}
        }
        return array;
    }

    /**
     * 把对象数组转换成List集合
     * @param set 对象数组
     * @return 转换好的List集合
     */
    public static <T> List<T> toArrayList(T[] set) {
        List<T> list = new ArrayList<T>();
        if (!empty(set)) {
            for (int i = 0; i < set.length; i++) {
                list.add(set[i]);
            }
        }
        return list;
    }
    
    /**
     * 把对象数组转换成String
     * @param set 对象数组
     * @return 转换好的字符串
     */
    public static String toString(Object[] set) {
        StringBuffer str = new StringBuffer();;
        if (!empty(set)) {
            for (int i = 0; i < set.length; i++) {
            	str.append(set[i]);
            	if(i<set.length-1){
            		str.append(",");
            	}
            }
        }
        return str.toString();
    }
    
    /**
     * 把对象数组转换成String
     * @param collection 对象数组
     * @return 转换好的字符串
     */
    public static String toString(Collection<?> collection) {
        StringBuffer str = new StringBuffer();;
    	for (Object o:collection){
    		str.append(o).append(",");
        }
    	
        return StringUtil.subStrEndDiffStr(str.toString(), ",");
    }
    
    /** 
     * 把对象数组转换成String
     * @author: jiuzhou.hu
     * @date:2017年3月13日下午9:21:58 
     * @param collection
     * @param separator
     * @return
     */
    public static String toString(Collection<?> collection, char separator) {
    	return StringUtil.join(collection.toArray(),separator);
    }
    
    /**
     * 把整数数组转换成String
     * @param set 对象数组
     * @return 转换好的字符串
     */
    public static String toString(int[] set) {
        StringBuffer str = new StringBuffer();;
        if (!empty(set)) {
            for (int i = 0; i < set.length; i++) {
            	str.append(set[i]);
            	if(i<set.length-1){
            		str.append(",");
            	}
            }
        }
        return str.toString();
    }
    
    /**
     * 把整数数组转换成String
     * @param set 对象数组
     * @return 转换好的字符串
     */
    public static String toString(String[] set) {
        StringBuffer str = new StringBuffer();;
        if (!empty(set)) {
            for (int i = 0; i < set.length; i++) {
            	str.append(set[i]);
            	if(i<set.length-1){
            		str.append(",");
            	}
            }
        }
        return str.toString();
    }

    /**
     * 把字符串数组转换成long型数组
     * @param strArray 字符串数组
     * @return long型数组
     */
    public static long[] toLongArray(String[] strArray) {
        if (empty(strArray)) {
            return new long[0];
        }
        long[] arr = new long[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            String str = strArray[i];
            arr[i] = NumberUtils.toLong(str);
        }
        return arr;
    }

    /**
     * 向整数数组最后位置添加一个整数
     * @param srcArray 元整数数组
     * @param addElement 要添加的值
     * @return 新的整数数组
     */
    public static int[] addIntArray(int[] srcArray, int addElement) {
        int srcLength = 0;
        if (null != srcArray) {
            srcLength = srcArray.length;
        }

        int[] dstArray = new int[srcLength + 1];
        for (int i = 0; i < srcLength; i++) {
            dstArray[i] = srcArray[i];
        }
        dstArray[srcLength] = addElement;

        return dstArray;
    }

    /**
     * 合并两个整数数组
     * @param srcArray 元整数数组
     * @param addArray 被合并的整数数组
     * @return 新的整数数组
     */
    public static int[] addIntArray(int[] srcArray, int[] addArray) {
        int srcLength = 0;
        if (null != srcArray) {
            srcLength = srcArray.length;
        }

        int addLength = 0;
        if (null != addArray) {
            addLength = addArray.length;
        }

        int[] dstArray = new int[srcLength + addLength];
        for (int i = 0; i < srcLength; i++) {
            dstArray[i] = srcArray[i];
        }
        for (int i = 0; i < addLength; i++) {
            dstArray[srcLength + i] = addArray[i];
        }

        return dstArray;
    }

    /**
     * 把一个字符串添加到一个字符串数组最后位置中
     * @param srcArray 元字符串数组
     * @param addElement 要添加的字符串数组
     * @return 新的字符串数组
     */
    public static String[] addStringArray(String[] srcArray, String addElement) {
        int srcLength = 0;
        if (null != srcArray) {
            srcLength = srcArray.length;
        }

        String[] dstArray = new String[srcLength + 1];
        for (int i = 0; i < srcLength; i++) {
            dstArray[i] = srcArray[i];
        }
        dstArray[srcLength] = addElement;

        return dstArray;
    }

    /**
     * 合并两个字符串数组
     * @param srcArray 元字符串数组
     * @param addArray 被合并的字符串数组
     * @return 新的合并后的字符串数组
     */
    public static String[] addIntArray(String[] srcArray, String[] addArray) {
        int srcLength = 0;
        if (null != srcArray) {
            srcLength = srcArray.length;
        }

        int addLength = 0;
        if (null != addArray) {
            addLength = addArray.length;
        }

        String[] dstArray = new String[srcLength + addLength];
        for (int i = 0; i < srcLength; i++) {
            dstArray[i] = srcArray[i];
        }
        for (int i = 0; i < addLength; i++) {
            dstArray[srcLength + i] = addArray[i];
        }

        return dstArray;
    }

    /**
     * 获取两个字符串的交集
     * @param set1 第1个字符串数组
     * @param set2 第2个字符串数组
     * @return 两个字符串中相等的值的新字符串数组
     */
    public static String[] toIntersectSet(String[] set1, String[] set2) {
        if (empty(set1) || empty(set2)) {
            return new String[0];
        }
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < set1.length; i++) {
            String s1 = set1[i];
            for (int j = 0; j < set2.length; j++) {
                String s2 = set2[j];
                if (StringUtil.equals(s1, s2)) {
                    list.add(s1);
                    break;
                }
            }
        }
        String[] set = new String[list.size()];
        list.toArray(set);
        return set;
    }
    
    /**
     * 根据指定字段汇总
     * @Title: total
     * @author Administrator
     * @param 设定文件
     * @return 返回类型
     */
    public static double total(Collection<?> collections,final String totalKey){
		double ret = 0.0;
		DecimalFormat df = new DecimalFormat("0.00");
		for(Object obj: collections){
			Object value = ClassUtil.getProperty(obj, totalKey);
			ret += NumberUtils.toDouble(value==null?"":value+"");
		}
		return NumberUtils.toDouble(df.format(ret));
	}
    
    /**
	 * 获取数组中字符串相等的第二个值
	 * 
	 * @param str
	 * @param al
	 * @return
	 */
	public static String getArrFristEle(String str, ArrayList<String[]> al) {
		for (int i = 0; i < al.size(); i++) {
			String strs[] = al.get(i);
			if (str.equals(strs[0]))
				return strs[1];
		}
		return "";
	}

	/**
	 * 字符串是否在数组中
	 * 
	 * @param str
	 * @param strs
	 * @return
	 */
	public static boolean isExist(String str, String strs[]) {
		for (int i = 0; i < strs.length; i++)
			if (strs[i].equalsIgnoreCase(str))
				return true;

		return false;
	}

	/**
	 * 字符串是否在数组中
	 * 
	 * @param str
	 * @param sets
	 * @return
	 */
	public static boolean isExist(String str, Set<Object> sets) {
		String s[] = ArrayUtils.changeObjectArray(sets.toArray());
		return isExist(str, s);
	}
	
	/**
     * 去重复
     * @author jiuzhou.hu
     * @date 2017年11月16日 下午6:17:18
     * @param list
     */
    public static <T> void distinct(List<T> list) {
        Set<T> set = new HashSet<T>();
        List<T> newList = new ArrayList<T>();
        for (Iterator<T> iter = list.iterator(); iter.hasNext();) {
            T element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
    }

}
