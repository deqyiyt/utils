package com.ias.common.utils.string;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;

import com.ias.common.utils.collection.ArrayUtils;


/**
 * 功能：对java中常见的字符串使用的功能进行二次封装，<br>
 * 达到减少字符串使用出错的机会。
 * 创建者: jiuzhou.hu
 */
public class StringUtil {
    
    public static final String EMPTY = "";
    
    private static final int PAD_LIMIT = 8192;
	
	/**
	 * 禁止实例化
	 */
	protected StringUtil() {
		
	}
	
	public static int length(String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 判别字符串是否为null或者没有内容
     * @param inStr 被判断的输入参数
     * @return  布尔值：true=表示输入字符串为null或者没有内容
     *                  false=表示输入字符串不为null或者有内容
     */
    public static boolean zero(String inStr) {
        return ((null == inStr) || (inStr.length() <= 0));
    }

    /**
     * 判断字符串是否为null或没有内容或全部为空格。
     * @param inStr 被判断的输入参数
     * @return  布尔值：true=表示输入字符串为null或没有内容或全部为空格
     *                  false=表示输入字符串不为null或有内容或全部不为空格
     */
    public static boolean isEmpty(String inStr) {
        return (zero(inStr) || (inStr.trim().equals("")));
    }
    
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * 判断非空字符串
     * @author jiuzhou.hu
     * @date 2014-3-24 下午2:28:43
     * @param str	源字符串
     * @return	true：非空，false：null或者""
     */
    public static Boolean isNotBlank(Object obj){
		Boolean flag=false;
		if(obj instanceof String){
			if(!isEmpty((String) obj)){
				flag=true;
			}
		}else{
			if(null!=obj){
				flag=true;
			}	
		}
		return flag;
	}
    
    /**
     * 判断空字符串
     * @author jiuzhou.hu
     * @date 2014-3-24 下午2:28:43
     * @param str	源字符串
     * @return	true：null或者""，false：非空
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 在str为null或者没有内容的情况下，返回空串；否则返回输入参数。
     * @param inStr 被判断的输入参数
     * @return 字符串="" 表示输入字符串为null或者没有内容
     *          字符串!="" 表示输入字符串有内容
     */
    public static String toZeroSafe(String inStr) {
        if (zero(inStr)) {
            return "";
        }
        return inStr;
    }

    /**
     * 在inStr为null或者没有内容的情况下，返回def；否则返回str
     * @param inStr 被判断的输入参数
     * @param def inStr为null或者没有内容的情况下，需要返回的字符串
     * @return 字符串=def 表示输入字符串为null或者没有内容
     *          字符串=inStr 表示输入字符串有内容
     */
    public static String toZeroSafe(String inStr, String def) {
        if (zero(inStr)) {
            return def;
        }
        return inStr;
    }

    /**
     * 在str为null或者没有内容，或者全部为空格的情况下，返回空串；否则返回str
     * @param inStr  被判断的输入参数
     * @return 字符串="" 表示输入字符串为null或者没有内容或者全部为空格
     *          字符串!="" 表示输入字符串有内容
     */
    public static String toEmptySafe(String inStr) {
        if (isEmpty(inStr)) {
            return "";
        }
        return inStr;
    }

    /**
     * 在str为null或者没有内容，或者全部为空格的情况下，返回def；否则返回str
     * @param inStr 被判断的输入参数
     * @param def inStr为null或者没有内容或者全部为空格的情况下，需要返回的字符串
     * @return 字符串=def 表示输入字符串为null或者没有内容或者全部为空格
     *          字符串=inStr 表示输入字符串有内容
     */
    public static String toEmptySafe(String inStr, String def) {
        if (isEmpty(inStr)) {
            return def;
        }
        return inStr;
    }

    /**
     * 去掉输入字符串首尾空格
     * @param inStr 输入字符串
     * @return 首尾无空格的字符串
     */
    public static String trim(String inStr) {
        if (isEmpty(inStr)) {
            return inStr;
        }
        return inStr.trim();
    }

    /**
     * 判断字符串是否内容相同
     * @param s1  第1个输入字符串
     * @param s2  第2个输入字符串
     * @return 布尔值=true：两个字符串相等
     *                =false:两个字符串不相等
     */
    public static boolean equals(String s1, String s2) {
        if (null == s1) {
            return false;
        }
        return s1.equals(s2);
    }

    /**
     * 判断字符串是否内容相同，不区分大小写
     * @param s1  第1个输入字符串
     * @param s2  第2个输入字符串
     * @return 布尔值=true：两个字符串相等
     *                =false:两个字符串不相等
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        if (null == s1) {
            return false;
        }
        return s1.equalsIgnoreCase(s2);
    }
    
    public static String defaultString(String str) {
        return str == null ? EMPTY : str;
    }

    /**
	 * 转换字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if (obj == null)
			return "";
		else
			return String.valueOf(obj);
	}
	
    /**
     * 在str字符串中，将所有token字符串，用delim字符串进行转义处理。
     * @param str 被替换的字符串
     * @param token 被替换的子字符串
     * @param delim 子字符串需要替换的内容
     * @return 已经替换好的字符串
     */
    public static String normalize(String str, String token, String delim) {
        //为空，直接返回
        if (isEmpty(str)) {
            return "";
        }
        //查找并替换
        StringTokenizer tokenizer = new StringTokenizer(str, token);
        StringBuilder fixedBuilder = new StringBuilder();
        while (tokenizer.hasMoreTokens()) {
            if (fixedBuilder.length() == 0) {
                fixedBuilder.append(tokenizer.nextToken());
            } else {
                fixedBuilder.append(fixedBuilder);
                fixedBuilder.append(delim);
                fixedBuilder.append(token);
                fixedBuilder.append(tokenizer.nextToken());
            }
        }

        if (str.indexOf(delim) == 0) {
            fixedBuilder.append(delim).append(token).append(fixedBuilder);
        }

        if (str.lastIndexOf(delim) == (str.length() - 1)) {
            fixedBuilder.append(fixedBuilder).append(delim).append(token);
        }

        return fixedBuilder.toString();
    }

    /**
     * 在字符串中，用新的字符串替换指定的字符
     * @param src 需要替换的字符串
     * @param charOld 被替换的字符
     * @param strNew  用于替换的字符串
     * @return 已经替换好的字符串
     */
    public static String replace(String src, char charOld, String strNew) {
        if (null == src) {
            return src;
        }
        if (null == strNew) {
            return src;
        }

        StringBuilder buf = new StringBuilder();
        for (int i = 0, n = src.length(); i < n; i++) {
            char c = src.charAt(i);
            if (c == charOld) {
                buf.append(strNew);
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    /**
     * 在字符对象中，用新的字符串替换指定的字符串
     * @param src 需要替换的字符对象
     * @param strOld 被替换的字符串
     * @param strNew  用于替换的字符串 
     */
    public static void replace(StringBuilder src, String strOld, String strNew) {
        if ((null == src) || (src.length() <= 0)) {
            return;
        }
        String s = replace(src.toString(), strOld, strNew);
        src.setLength(0);
        src.append(s);
    }

    /**
     * 在字符串中，用新的字符串替换指定的字符串
     * @param src 需要替换的字符对象
     * @param strOld 被替换的字符串
     * @param strNew  用于替换的字符串
     * @return 已经被替换的字符串
     */
    public static String replace(String src, String strOld, String strNew) {
        if (null == src) {
            return src;
        }
        if (zero(strOld)) {
            return src;
        }
        if (null == strNew) {
            return src;
        }
        if (equals(strOld, strNew)) {
            return src;
        }
        
        return src.replaceAll(strOld, strNew);
    }

    /**
     * 把字符串的第一个字符变为大写
     * @param s 输入字符串
     * @return 返回第一个字符是大写的字符串
     */
    public static String upperFirst(String s) {
        String str = null;
        if (null != s) {
            if (s.length() == 1) {
                str = s.toUpperCase();
            } else {
                str = s.substring(0, 1).toUpperCase() + s.substring(1);
            }
        }
        return str;
    }

    /**
     * 把字符对象第一个字符变为大写
     * @param sb 字符对象
     */
    public static void upperFirst(StringBuilder sb) {
        if ((null != sb) && (sb.length() > 0)) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
    }

    /**
     * 把字符串的第一个字符变为小写
     * @param s 输入的字符串
     * @return 返回的第一个字符是小写的字符串
     */
    public static String lowerFirst(String s) {
        String str = null;
        if (null != s) {
            if (s.length() == 1) {
                str = s.toLowerCase();
            } else {
                str = s.substring(0, 1).toLowerCase() + s.substring(1);
            }
        }
        return str;
    }

    /**
     * 把字符对象的第一个字符变为小写
     * @param sb 输入的字符对象
     */
    public static void lowerFirst(StringBuilder sb) {
        if ((null != sb) && (sb.length() > 0)) {
            sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        }
    }

    /**
     * 根据指定的delima标志获取输入字符串的后缀
     * @param str 输入字符串
     * @param delima 指定的标志,一般是一个字符，如“.”
     * @return 输入字符串子的后缀
     */
    public static String getLastSuffix(String str, String delima) {
        if (zero(delima)) {
            return str;
        }

        String suffix = "";
        if (!zero(str)) {
            int index = str.lastIndexOf(delima);
            if (index >= 0) {
                suffix = str.substring(index + delima.length());
            } else {
                suffix = str;
            }
        }
        return suffix;
    }

    /**
     * 根据指定的delima标志获取输入字符串的前缀
     * @param src 输入字符串
     * @param delima 指定的标志,一般是一个字符，如“.”
     * @return 输入字符串的前缀
     */
    public static String getLastPrefix(String src, String delima) {
        if (zero(delima)) {
            return src;
        }

        String prefix = "";
        if (!zero(src)) {
            int index = src.lastIndexOf(delima);
            if (index >= 0) {
                prefix = src.substring(0, index);
            }
        }
        return prefix;
    }

    /**
     * 将str字符串按照其中出现的delim分割成字符串数组
     * @param str 输入的字符串
     * @param delim 分割标志
     * @return 分割好的数组
     */
    public static String[] split(String str, String delim) {
        if (zero(str) || zero(delim)) {
            return new String[0];
        }
        return str.split(delim);
    }

    /**
     * 将str字符串按照其中出现的delim分割成字符串数组,并能去掉前后空格
     * @param str 输入的字符串
     * @param delim 分割标志
     * @param trim =true 去掉前后空格 =false 不去掉前后空格
     * @return 分割好的数组
     */
    public static String[] split(String str, String delim, boolean trim) {
        String[] set = split(str, delim);
        if (trim) {
            for (int i = 0; i < set.length; i++) {
                set[i] = set[i].trim();
            }
        }
        return set;
    }

    /**
     * 从str字符串的起始位置中，按照words数组中各个元素字符串出现的顺序，去除所有这些元素字符串。
     * 不去分大小写，不考虑whitespace符号。
     * 如果str中不存在这些元素字符串，或者没有按照顺序出现，抛出异常。
     * @param str
     * @param words
     * @return
     * @throws Exception
     */
    public static String removeSequenceHeadingWordsIgnoreCase( String str, String[] words, String delim )
        throws Exception
    {
        if ( isEmpty( str ) || ArrayUtils.empty( words ) )
            return "";

        String[] set = split( str, delim );
        int setIndex = 0;
        for ( int wordIndex = 0; ( setIndex < set.length ) && ( wordIndex < words.length ) ; wordIndex++ ) {
            String s = set[setIndex];
            String w = words[wordIndex];
            if ( isEmpty( w ) )
                continue;

            if ( ! s.trim().equalsIgnoreCase( w.trim() ) )
                throw new Exception( "no word '" + w + "' in the string '" + str + "' of index " + setIndex );

            setIndex ++;
        }
        return join( set, delim, setIndex );
    }

    /**
     * 将set字符串数组从fromIndex开始以后的元素合并成以delim为分割符的字符串
     * @param set
     * @param delim
     * @param fromIndex 以0开始
     * @return
     */
    public static String join( String[] set, String delim, int fromIndex )
    {
        if ( ( null == set ) || ( set.length <= 0 ) || ( fromIndex >= set.length ) )
            return "";

        if ( fromIndex < 0 )
            fromIndex = 0;

        StringBuffer sb = new StringBuffer();
        sb.append( set[fromIndex] );
        for( int i = fromIndex+1; i < set.length; i++ ) {
            sb.append( delim );
            sb.append( set[i] );
        }
        return sb.toString();
    }
    
    public static String join(Collection<?> collection, String separator) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), separator);
    }
    
    public static String join(Iterator<?> iterator, String separator) {

        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return ObjectUtils.toString(first);
        }

        // two or more elements
        StringBuffer buf = new StringBuffer(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }
    
    public static String join(String separator, Object... items) {
		String tmpKey = join(items, separator);
		tmpKey = StringUtil.subStrEndDiffStr(tmpKey, separator);
		tmpKey = StringUtil.subStrStartDiffStr(tmpKey, separator);
		tmpKey = StringUtil.replace(tmpKey, separator + "+", separator);
		return tmpKey;
	}
    
    public static String join(final Object[] array, final String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }
    
    public static String join(final Object[] array, String separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = EMPTY;
        }

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return EMPTY;
        }

        final StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }
    
    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }

        return join(array, separator, 0, array.length);
    }
    
    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return EMPTY;
        }

        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + 1);
        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }
    
	/**
	 * 字符串补齐
	 * @param source  源字符串
	 * @param fillLength 补齐长度
	 * @param fillChar 补齐的字符
	 * @param isLeftFill true为左补齐，false为右补齐
	 * @return
	 */
	public static String stringFill(String source, int fillLength, char fillChar, boolean isLeftFill) {
		if (source == null || source.length() >= fillLength)
			return source;

		StringBuilder result = new StringBuilder(fillLength);
		int len = fillLength - source.length();
		if (isLeftFill) {
			for (; len > 0; len--) {
				result.append(fillChar);
			}
			result.append(source);
		} else {
			result.append(source);
			for (; len > 0; len--) {
				result.append(fillChar);
			}
		}
		return result.toString();
	}
    
    
    /**
     * 把占位符号进行替换.<br>
     *@author:jiuzhou.hu
     *@since :1.0:2009-8-8
     *@param replaceContentSrc:被替换的原字符串
     *@param inputPrifx：input输入框的名称前缀
     *@return 替换好的字符串
     */
    public static StringBuilder replaceSpecialChar(String replaceContentSrc,
            String inputPrifx) {
        String oldReplaceContent = replaceContentSrc;
        StringBuilder builder = new StringBuilder();
        if(StringUtil.isEmpty(oldReplaceContent)){
            return builder;    
        }
        String splitChar = new String("_");
        String replaceStrBegin = "<input type=\"text\" class=\"inputUnderLine2\" name=\"" + inputPrifx;
        String replaceStrMiddle="\" id=\""+inputPrifx+"Id";
        String replaceStrend = "\">&nbsp;&nbsp;&nbsp;";
        // 首先判断开始有没有
        String beginChar = oldReplaceContent.substring(0, splitChar.length());
        if(StringUtil.equals(beginChar, splitChar)) {
            builder.append(replaceStrBegin + 0 +replaceStrMiddle+0+ replaceStrend);
            oldReplaceContent = oldReplaceContent.substring(splitChar.length());
        }
        // 把中间的替换掉
        boolean flagReplace = false;
        String endChar = oldReplaceContent.substring(
                oldReplaceContent.length() - splitChar.length(),
                oldReplaceContent.length());
        if(StringUtil.equals(endChar, splitChar)) {
            oldReplaceContent = oldReplaceContent.substring(0,
                    oldReplaceContent.length() - splitChar.length());
            flagReplace = true;
        }
        // 把中间的去掉
        String[] splitStrs = StringUtil.split(oldReplaceContent, splitChar);
        if(flagReplace) {
            for(int i = 0; i < splitStrs.length; i++) {
                String q = splitStrs[i];
                builder.append(q);
                builder.append(replaceStrBegin + (i + 1) +replaceStrMiddle+(i+1)+ replaceStrend);

            }
        } else {
            for(int i = 0; i < splitStrs.length; i++) {
                String q = splitStrs[i];
                builder.append(q);
                if(i != splitStrs.length - 1) {
                    builder.append(replaceStrBegin + (i + 1) +replaceStrMiddle+(i+1)+ replaceStrend);
                }
            }
        }
        return builder;
    }
    
    
    /**
     * 指定字符串出现的次数.<br>
    *@author:jiuzhou.hu
    *@since :1.0:2009-8-10
    *@param srcStr：查找的字符串
    *@param countStr：指定要查找的字符串
    *@return
     */
    public static int countStringNumber(String srcStr,String countStr){
        int indexCount = 0;
        int index = 0;
        int count=0;
        for(;;) {
            index = srcStr.indexOf(countStr, indexCount);
            if(index == -1){
                break;
            }
            count++;
            indexCount = (index += countStr.length());
        }
        return count;
    } 
    
    /**
	 * 比较第二个字符串数组是否被第一个字符串数组包含
	* @author jiuzhou.hu
	* @version 创建时间：Sep 10, 2010  10:31:55 AM
	* @param arr1 原字符串数组
	* @param arr2 被追加的字符串数组
	* @return 第二个字符串追加到第一个字符串后的String
	 */
	public static String compareAddDiffArr(String[] arr1,String[] arr2){
		String arr2Str = "";
		for(int i=0;i<arr2.length;i++){
			if(i == 0){
				arr2Str = arr2[i];
			}else{
				arr2Str += (","+arr2[i]);
			}
		}
		arr2Str += ",";
		for(int j=0;j<arr1.length;j++)
			arr2Str = arr2Str.replace(arr1[j]+",",""); 
		arr2Str = (arr2Str.endsWith(",")) ? arr2Str.substring(0,arr2Str.length()-1) : arr2Str;
		return arr2Str;
	}
	/**
	 * 比较第二个字符串是否被第一个字符串包含，分割字符串用","
	* @author jiuzhou.hu
	* @version 创建时间：Sep 10, 2010  10:31:55 AM
	* @param arr1 原字符串
	* @param arr2 被追加的字符串
	* @return 第二个字符串追加到第一个字符串后的String
	 */
	public static String addStrDiffStr(String arr1,String arr2) {
		arr1 = arr1.replaceAll(" ", "");
		arr2 = arr2.replaceAll(" ", "");
		String[] arr1s = {};
		String[] arr2s = {};
		String str = "";
		if(arr1 != null && !arr1.equals(""))
			arr1s = arr1.split(",");
		else{
			arr1 = "";
		}
		if(arr2 != null && !arr2.equals(""))
			arr2s = arr2.split(",");
		else{
			arr2 = "";
		}
		String temp[]=new String[arr1s.length+arr2s.length];
		System.arraycopy(arr1s,0,temp,0,arr1s.length);//将数组arr1s的元素复制到temp中
		System.arraycopy(arr2s,0,temp,arr1s.length,arr2s.length);//将数组arr2s的元素复制到temp中
		
		//连接数组完成,开始清除重复元素
		for(int i=0;i<temp.length;i++){
			if(temp[i]!="-1"){
				for(int j=i+1;j<temp.length;j++){
					if(temp[i].equals(temp[j])){
						temp[j]="-1";//将发生重复的元素赋值为"-1"
					}	
				}
			}
		}
		for(int i=0,j=0;j<temp.length && i<temp.length;i++,j++){
			if(temp[i].equals("-1")){
				j--;
		    }
		    else{
		    	str += temp[i];
		    	str += ", ";
		    }
		}
		if(!str.equals("")){
			str = str.replace("-1, ",""); 
			str = str.replace(", -1",""); 
			str = (str.endsWith(", ")) ? str.substring(0,str.length()-2) : str;
		}
		return str;
	}
	
	/**
	 * 
	 * 判断第一个字符串是否为第二个字符串结尾，并且去掉
	 * @param str
	 * @param ch
	 * @return 设定文件
	 * @author hujiuzhou
	 * @return String 返回类型
	 * @throws
	 */
	public static String subStrEndDiffStr(String str,String ch){
		str = toEmptySafe(str);
		ch = toEmptySafe(ch);
		return (str.endsWith(ch)) ? str.substring(0, str.length() - ch.length()) : str;
	}
	
	/**
	 * 
	 * 判断第一个字符串是否为第二个字符串开始，并且去掉
	 * @param str
	 * @param ch
	 * @return 设定文件
	 * @author hujiuzhou
	 * @return String 返回类型
	 * @throws
	 */
	public static String subStrStartDiffStr(String str,String ch){
		str = str.trim();
		ch = ch.trim();
		return (str.startsWith(ch)) ? str.substring(ch.length(), str.length()) : str;
	}
	
    /**
     * 双引号转化成单引号
     * @author jiuzhou.hu
     * @date 2018年1月18日 下午4:37:46
     * @param str
     * @return
     */
    public static String replaceKeyString(String str) {
        if (containsKeyString(str)) {
            return str.replace("'", "\\'").replace("\"", "\\\"").replace("\r",
                    "\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
                    "\b", "\\b").replace("\f", "\\f");
        } else {
            return str;
        }
    }
    
    /**
     * 单引号转化成双引号
     * @author jiuzhou.hu
     * @date 2018年1月18日 下午4:37:39
     * @param str
     * @return
     */
    public static String replaceString(String str) {
        if (containsKeyString(str)) {
            return str.replace("'", "\"").replace("\"", "\\\"").replace("\r",
                    "\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
                    "\b", "\\b").replace("\f", "\\f");
        } else {
            return str;
        }
    }
	
	/**
	 * 替换单引号为字符串形式
	 * @param source
	 * @return
	 */
	public static String invertedCommaFilter(String source) {
		if (source == null)
			return null;
		else
			return StringUtil.replace(source, "'", "\\'");
	}
	
	/**
	 * 替换含{?}字符串
	 * @param source 源字符串
	 * @param values 替换数组
	 * @param level 开始位
	 * @return
	 */
	public static String fillSpacing(String source, String values[], int level) {
		if (source.indexOf("{?}") != -1) {
			String source1 = source.substring(0, source.indexOf("{?}") + 3);
			source = StringUtil.replace(source1, "{?}", values[level]) + source.substring(source.indexOf("{?}") + 3, source.length());
			level++;
			source = fillSpacing(source, values, level);

		}
		return source;
	}

	/**
	 * 分割并且去除空格
	 * @param str 待分割字符串
	 * @param sep 分割符
	 * @param sep2 第二个分隔符
	 * @return 如果str为空，则返回null。
	 * (String str, String sep, String sep2)
	 */
	public static String[] splitAndTrim(String text, String searchString, String replacement) {
		if (isBlank(text)) {
			return null;
		}
		if (isBlank(searchString)) {
		    text = replace(text, searchString, replacement);
		}
		String[] arr = split(text, replacement);
		// trim
		for (int i = 0, len = arr.length; i < len; i++) {
			arr[i] = arr[i].trim();
		}
		return arr;
	}

	/**
	 * 剪切文本。如果进行了剪切，则在文本后加上"..."
	 * @param s 剪切对象。
	 * @param len 编码小于256的作为一个字符，大于256的作为两个字符。
	 * @return
	 */
	public static String textCut(String s, int len, String append) {
		if (s == null) {
			return null;
		}
		int slen = s.length();
		if (slen <= len) {
			return s;
		}
		// 最大计数（如果全是英文）
		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		for (; count < maxCount && i < slen; i++) {
			if (s.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
		}
		if (i < slen) {
			if (count > maxCount) {
				i--;
			}
			if (isNotBlank(append)) {
				if (s.codePointAt(i - 1) < 256) {
					i -= 2;
				} else {
					i--;
				}
				return s.substring(0, i) + append;
			} else {
				return s.substring(0, i);
			}
		} else {
			return s;
		}
	}
	
	/**
	 * 检查字符串中是否包含被搜索的字符串。被搜索的字符串可以使用通配符'*'。
	 * @param str
	 * @param search
	 * @return
	 */
	public static boolean contains(String str, String search) {
		if (isBlank(str) || isBlank(search)) {
			return false;
		}
		String reg = replace(search, "*", ".*");
		Pattern p = Pattern.compile(reg);
		return p.matcher(str).matches();
	}

	public static boolean containsKeyString(String str) {
		if (isBlank(str)) {
			return false;
		}
		if (str.contains("'") || str.contains("\"") || str.contains("\r")
				|| str.contains("\n") || str.contains("\t")
				|| str.contains("\b") || str.contains("\f")) {
			return true;
		}
		return false;
	}
	
	
	public static String addCharForString(String str, int strLength,char c,int position) {
		  int strLen = str.length();
		  if (strLen < strLength) {
			  while (strLen < strLength) {
			  StringBuffer sb = new StringBuffer();
			  if(position==1){
				  //右補充字符c
				  sb.append(c).append(str);
			  }else{
				//左補充字符c
				  sb.append(str).append(c);
			  }
			  str = sb.toString();
			  strLen = str.length();
			  }
			}
		  return str;
	 }

	public static String getSuffix(String str) {
		int splitIndex = str.lastIndexOf(".");
		return str.substring(splitIndex + 1);
	}
	
	public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }
	
	public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return padding(pads, padChar).concat(str);
    }
	
	public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }
	
	private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        final char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }
	
	public static boolean startsWith(String str, String prefix) {
        return startsWith(str, prefix, false);
    }
	
	private static boolean startsWith(String str, String prefix, boolean ignoreCase) {
        if (str == null || prefix == null) {
            return (str == null && prefix == null);
        }
        if (prefix.length() > str.length()) {
            return false;
        }
        return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
    }
	
	/**
	 * mybatis的sql语句中使用
	 * @author jiuzhou.hu
	 * @date 2018年3月2日 下午3:07:36
	 * @param item
	 * @return
	 */
	public static String toBetween(String item) {
	    	if(!isEmpty(item)) {
	    		return "'" + item.replace("_", "' and '") + "'";
	    	} else {
	    		return item;
	    	}
    }
}
