package com.ias.common.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import com.ias.common.utils.string.StringUtil;

/**
 *********************************************** 
 * @Copyright (c) by ysc All right reserved.
 * @Create Date: 2014-2-24 下午2:13:30
 * @Create Author: hujz
 * @File Name: EncodingUtil
 * @Function: 提供转码方法类
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class EncodingUtil {

    /**
     * @Title URLEncode
     * @Method encode方法.<br>
     * @author jiuzhou.hu
     * @date 2016年7月25日 下午6:00:54
     * @param str
     * @return
     */
    public static String encode(String sign) {
        if(StringUtil.isBlank(sign)) {
            return null;
        } else {
            try {
                return java.net.URLEncoder.encode(sign, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
    }
}
