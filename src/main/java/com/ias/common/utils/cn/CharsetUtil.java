package com.ias.common.utils.cn;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.ias.common.utils.bean.ClassUtil;
import com.ias.common.utils.string.StringUtil;

/**
 *********************************************** 
 * @Copyright (c) by ysc All right reserved.
 * @Create Date: 2014-2-24 下午2:13:30
 * @Create Author: hujz
 * @File Name: EncodingUtil
 * @Function: 字符集帮助类
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class CharsetUtil {

    public static final String CHARSET_ISO88591 = "ISO-8859-1";         //"8859_1";
    public static final String CHARSET_88591 = "8859_1";   // 在jsp中，这两种编码方式不同，不知道为什么
    public static final String CHARSET_GB18030 = "GB18030";
    //由于新增加的汉字通过GB2312转换存在BUG，因此需要使用GB18030编码。
    //例如"家俬"编码为“bc d2 82 68”
    public static final String CHARSET_GB2312 = CHARSET_GB18030; // "GB2312";
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String CHARSET_UTF16 = "UTF-16";
    public static final String CHARSET_UNICODEBIGUNMARKED = "UnicodeBigUnmarked";
    public static final String CHARSET_USASCII = "US-ASCII";

    // 在寻找不到预定义的字符集时，使用的缺省字符集。
    public final static String ENCODING_DEFAULT = CHARSET_ISO88591;

    // 由于不同的语言平台读取文件时使用的是不同的字符集，因此我们这里提供转换。
    private static Map<String, String> theLangToEncodingMap = new HashMap<String, String>();


    static {
        theLangToEncodingMap.put("zh", CHARSET_GB2312);
        theLangToEncodingMap.put("en", CHARSET_ISO88591);
    }
    ;
    @SuppressWarnings("unused")
    private final static char[] digits = {
        '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b',
        'c', 'd', 'e', 'f', 'g', 'h',
        'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z'
    };

    ///////////////////////////////////////////////////////////////////

    /**
     * 获取当前运行系统的缺省字符集
     */
    public static String getSystemEncoding() {
        String lang = Locale.getDefault().getLanguage();
        String enc = (String) (theLangToEncodingMap.get(lang));
        // 在中文操作系统上不进行转码，其他系统都转换为英文。
        if (StringUtil.zero(enc)) {
            return ENCODING_DEFAULT;
        }
        return enc;
    }

    public static boolean sameSystemEncoding(String enc) {
        return enc.equals(getSystemEncoding());
    }

    ///////////////////////////////////////////////////////////////////
    /**
     * 公用的转码方法。
     */
    public static String convert(String str, String srcEnc, String dstEnc) {
        if (str == null) {
            return "";
        }
        try {
            if (needConvert(str, srcEnc, dstEnc)) {
                str = new String(str.getBytes(srcEnc), dstEnc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 判别是否需要进行字符串的转码处理。
     */
    public static boolean needConvert(String str, String srcEnc, String dstEnc)
            throws Exception {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        if (ClassUtil.equals(srcEnc, dstEnc)) {
            return false;
        }

        // 判断byte与char的数目是否相同。
        //???? 但是这样判断不准确，不知道为什么。 例如“美女”返回false，而“野兽”返回true。
        //return ( ( str.getBytes( ResourceAdapter.ENCODING_INTERNAL ).length ) != ( str.length() ) );
        return true;
    }

    /**
     *convert a string from unicode 8859_1 to gb2312
     *@param gbStr string coding with gb2312
     *@return u8859_1 string
     */
    public static String ISO88591toGB2312(String gbStr) {
        if (gbStr != null) {
            try {
                //if(isChinese(gbStr))
                gbStr = new String(gbStr.getBytes(CHARSET_ISO88591), CHARSET_GB2312);
            } catch (Throwable e) {
                return "";
            }
        }
        return gbStr;
    }

    /**
     *convert a string from unicode gb2312 to 8859_1
     *@return gb2312 string
     */
    public static String GB2312toISO88591(String uStr) {
        if (uStr != null) {
            try {
                uStr = new String(uStr.getBytes(CHARSET_GB2312), CHARSET_ISO88591);
            } catch (Throwable e) {
                return "";
            }
        }
        return uStr;
    }

    /**
     *convert a string from unicode gb18030 to 8859_1
     *@return gb2312 string
     */
    public static String GB18030toISO88591(String uStr) {
        if (uStr != null) {
            try {
                uStr = new String(uStr.getBytes(CHARSET_GB18030), CHARSET_ISO88591);
            } catch (Throwable e) {
                return "";
            }
        }
        return uStr;
    }

    public static String toISO88591(String uStr) {
        return toEncoding(uStr, CHARSET_ISO88591);
    }

    public static String toGB2312(String uStr) {
        return toEncoding(uStr, CHARSET_GB2312);
    }

    /**
     * 将某种编码方式的参数字符串转化为内部UniCode编码
     * @param str
     * @param currEncoding
     * @return 在转码失败情况下，返回“”串
     */
    public static String toUnicode(String str, String currEncoding) {
        if (str != null) {
            try {
                str = new String(str.getBytes(currEncoding));
            } catch (Throwable e) {
                return "";
            }
        }
        return str;
    }

    /**
     * 将某种编码方式的参数字符串转化为内部UniCode编码
     * @param str
     * @param currEncoding
     * @return 在转码失败情况下，返回原始串
     */
    public static String toUnicodeKeep(String str, String currEncoding) {
        if (str != null) {
            try {
                str = new String(str.getBytes(currEncoding));
            } catch (Throwable e) {
                return str;
            }
        }
        return str;
    }

    /**
     * 将UniCode编码的参数字符串转化为目标编码方式
     * @param str
     * @param destEncoding
     * @return 在转码失败情况下，返回“”串
     */
    public static String toEncoding(String str, String destEncoding) {
        if (str != null) {
            try {
                str = new String(str.getBytes(), destEncoding);
            } catch (Throwable e) {
                return "";
            }
        }
        return str;
    }

    /**
     * 将UniCode编码的参数字符串转化为目标编码方式
     * @param str
     * @param destEncoding
     * @return 在转码失败情况下，返回原始串
     */
    public static String toEncodingKeep(String str, String destEncoding) {
        if (str != null) {
            try {
                str = new String(str.getBytes(), destEncoding);
            } catch (Throwable e) {
                return str;
            }
        }
        return str;
    }

    ///////////////////////////////////////////////////////////////////
    public static byte[] toByteArray(String str, String srcEncoding) {
        try {
            return str.getBytes(srcEncoding);
        } catch (UnsupportedEncodingException e) {
            return str.getBytes();
        }
    }

    public static String URLEncoderToUTF8(String str) {
        if (StringUtil.isEmpty(str)) {
            return "";
        }
        try {
            str = URLEncoder.encode(str, CHARSET_UTF8);
            str = StringUtil.replace(str, "%", "~");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String URLDecodeFromUTF8(String str) {
        try {
            str = StringUtil.replace(str, "~", "%");
            str = URLDecoder.decode(str, CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
