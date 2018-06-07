package com.ias.common.utils.booleans;

import com.ias.common.utils.string.StringUtil;

/**
 *********************************************** 
 * @Copyright (c) by ysc All right reserved.
 * @Create Date: 2014-2-24 下午2:13:06
 * @Create Author: hujz
 * @File Name: BooleanUtil
 * @Function: 提供布尔值方法类
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class BooleanUtil {

    // /////////////////////////////////////////////////////////////////

    public static boolean toBoolean(Object str) {
    	if(str == null) {
    		return false;
    	} else {
    		return toBoolean(str.toString(), false);
    	}
        
    }

    public static boolean toBoolean(String str, boolean defaultValue) {
        if(StringUtil.isEmpty(str))
            return defaultValue;
        try {
            return toRawBoolean(str);
        } catch(Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static boolean toRawBoolean(String str) throws Exception {
        return "true".equalsIgnoreCase(str.trim());
    }
}
