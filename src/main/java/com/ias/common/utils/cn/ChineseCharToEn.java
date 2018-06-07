package com.ias.common.utils.cn;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class ChineseCharToEn {
	private final static Logger logger = LoggerFactory.getLogger(ChineseCharToEn.class);
	private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274,  
        2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,  
        4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };  
	private final static String[] lc_FirstLetter = { "a", "b", "c", "d", "e",  
	        "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
	        "t", "w", "x", "y", "z" };  

	/**
    * 汉字转拼音的方法
    * @param name 汉字
    * @return 拼音
    */
    public static String hanyuToPinyin(String name){
    	String pinyinName = "";
        char[] nameChar = name.toCharArray();
        HanyuPinyinOutputFormat defaultFormat =  new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        }
        return pinyinName;
    }
    
    static Pattern p = Pattern.compile("\"([^\"]+)\"");  
    
    /**
     * 获得中文首字母，只返回中文首字母，其中英文部分不返回
     * @Project FileTest
     * @Package com
     * @Method getAllFirstLetter方法.<br>
     * @author 胡久洲
     * @date 2013-9-6 下午3:52:06
     * @param str
     * @return
     */
    public static String getAllFirstLetterForChinese(String str) {
    	if (str == null || str.trim().length() == 0) {   
            return "";   
        }   
  
        StringBuffer _str = new StringBuffer();   
        for (int j = 0; j < str.length(); j++) {
        	String ch = str.substring(j, j + 1);
        	
        	if(Pattern.compile("([\u4e00-\u9fa5]+)").matcher(ch).find()){
	        	String py = hanyuToPinyin(ch);
	        	_str.append(py.substring(0,1));
        	}
        } 
        return _str.toString();
    }
    
    /** 
     * 取得给定汉字串的首字母串,即声母串，英文部分之间输出 
     * @param str 给定汉字串 
     * @return 声母串 
     */  
    public static String getAllFirstLetter(String str) {  
        if (str == null || str.trim().length() == 0) {  
            return "";  
        }  
  
        String _str = "";  
        for (int i = 0; i < str.length(); i++) {  
            _str = _str + getFirstLetter(str.substring(i, i + 1));  
        }  
  
        return _str;  
    }  
    
	/** 
	 * 取得给定汉字的首字母,即声母 
	 * @param chinese 给定的汉字 
	 * @return 给定汉字的声母 
	 */  
	public static String getFirstLetter(String chinese) {  
	    if (chinese == null || chinese.trim().length() == 0) {  
	        return "";  
	    }  
	    chinese = conversionStr(chinese, "GB2312", "ISO8859-1");  
	
	    if (chinese.length() > 1) // 判断是不是汉字  
	    {  
	        int li_SectorCode = (int) chinese.charAt(0); // 汉字区码  
	        int li_PositionCode = (int) chinese.charAt(1); // 汉字位码  
	        li_SectorCode = li_SectorCode - 160;  
	        li_PositionCode = li_PositionCode - 160;  
	        int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码  
	        if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {  
	            for (int i = 0; i < 23; i++) {  
	                if (li_SecPosCode >= li_SecPosValue[i]  
	                        && li_SecPosCode < li_SecPosValue[i + 1]) {  
	                    chinese = lc_FirstLetter[i];  
	                    break;  
	                }  
	            }  
	        } else // 非汉字字符,如图形符号或ASCII码  
	        {  
	            chinese = conversionStr(chinese, "ISO8859-1", "GB2312");  
	            chinese = chinese.substring(0, 1);  
	        }  
	    }  
	
	    return chinese;  
	}  
	
	/** 
	 * 字符串编码转换 
	 * @param str 要转换编码的字符串 
	 * @param charsetName 原来的编码 
	 * @param toCharsetName 转换后的编码 
	 * @return 经过编码转换后的字符串 
	 */  
	public static String conversionStr(String str, String charsetName,String toCharsetName) {  
	    try {  
	        str = new String(str.getBytes(charsetName), toCharsetName);  
	    } catch (UnsupportedEncodingException ex) {
	    	logger.debug("字符串编码转换异常：" + ex.getMessage());
	    }
	    return str;  
	}  
	
	/**
     * 获取中文字符串占用长度（像素）
     * @param str
     * @return
     */
    public static int getVarcharSpace(String str) {
        int space = 0;
        if (str != null && !str.equals("")) {
            for (int i = 0; i < str.length(); i++)
                if (str.substring(i, i + 1).matches("[\u4E00-\u9FA5]"))
                    space += 12;
                else
                    space += 6;

        }
        return space;
    }
    
    public static boolean isChinese(String txt) throws UnsupportedEncodingException {
        byte[] bytes = new byte[0];
        try {
            bytes = txt.getBytes(CharsetUtil.CHARSET_ISO88591);
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] < 0) {
                    return true;
                }
            }
            return false;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    public static boolean isChinese(char message) {
        boolean isChinese = false;
        if ((message < 0) || (message > 127)) {
            isChinese = true;
        }
        return isChinese;
    }
	
	public static void main(String[] args) {  
	    System.out.println("获取拼音首字母："+getAllFirstLetter("大中国南昌中大china"));
	    System.out.println("获取拼音字母："+hanyuToPinyin("大中国南昌中大china"));
	}
}
