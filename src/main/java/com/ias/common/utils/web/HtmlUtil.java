package com.ias.common.utils.web;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ias.common.utils.id.UUIDUtils;
import com.ias.common.utils.string.StringUtil;

public class HtmlUtil {
	
	/**
	 * 将html内容分解为class和body内容
	 * @author jiuzhou.hu
	 * @date 2016年10月21日 下午2:24:01
	 * @param content
	 * @return
	 */
	public static Map<String,String> split(String content) {
		StringBuffer classStyle = new StringBuffer();
        if(StringUtil.isNotBlank(content)) {
	        Document document = Jsoup.parse(content);
			Elements elements = document.getElementsByAttribute("style");
			for(Element e:elements) {
				String className = "i-"+UUIDUtils.createSystemDataPrimaryKey();
				classStyle.append(".")
				.append(className)
				.append("{")
				.append(e.attr("style"))
				.append("}");
				e.removeAttr("style");
				e.addClass(className);
			}
			content = document.body().html();
        }
        Map<String, String> map = new HashMap<>();
        map.put("content", content);
        map.put("class", classStyle.toString());
        return map;
	}
	
	public static String removeHtmlTag(String inputString) {  
        if (inputString == null)  
            return null;  
        String htmlStr = inputString; // 含html标签的字符串  
        String textStr = "";  
        java.util.regex.Pattern p_script;  
        java.util.regex.Matcher m_script;  
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;  
        java.util.regex.Matcher m_html;  
        try {  
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>  
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";   
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>  
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; 
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
            m_script = p_script.matcher(htmlStr);  
            htmlStr = m_script.replaceAll(""); // 过滤script标签  
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
            m_style = p_style.matcher(htmlStr);  
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
            m_html = p_html.matcher(htmlStr);  
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            htmlStr = htmlStr.replaceAll("<a href[^>]*>", "").replaceAll("</a>", "").replaceAll("<img[^>]*/>", " ");
            textStr = htmlStr;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return textStr;// 返回文本字符串  
    }
	
	/**
     * p换行
     * @param inputString
     * @return
     */
    public static String removeHtmlTagP(String inputString) {  
        if (inputString == null)  
            return null;  
        String htmlStr = inputString; // 含html标签的字符串  
        String textStr = "";  
        java.util.regex.Pattern p_script;  
        java.util.regex.Matcher m_script;  
        java.util.regex.Pattern p_style;  
        java.util.regex.Matcher m_style;  
        java.util.regex.Pattern p_html;  
        java.util.regex.Matcher m_html;  
        try {  
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>  
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";   
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>  
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";   
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
            m_script = p_script.matcher(htmlStr);  
            htmlStr = m_script.replaceAll(""); // 过滤script标签  
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
            m_style = p_style.matcher(htmlStr);  
            htmlStr = m_style.replaceAll(""); // 过滤style标签  
            htmlStr.replace("</p>", "\n");
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
            m_html = p_html.matcher(htmlStr);  
            htmlStr = m_html.replaceAll(""); // 过滤html标签  
            textStr = htmlStr;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return textStr;// 返回文本字符串  
    }
    
    /**
     * 文本转html
     * 
     * @param txt
     * @return
     */
    public static String txt2htm(String txt) {
        if (StringUtil.isBlank(txt)) {
            return txt;
        }
        StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
        char c;
        boolean doub = false;
        for (int i = 0; i < txt.length(); i++) {
            c = txt.charAt(i);
            if (c == ' ') {
                if (doub) {
                    sb.append(' ');
                    doub = false;
                } else {
                    sb.append("&nbsp;");
                    doub = true;
                }
            } else {
                doub = false;
                switch (c) {
                case '&':
                    sb.append("&amp;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '\n':
                    sb.append("<br/>");
                    break;
                default:
                    sb.append(c);
                    break;
                }
            }
        }
        return sb.toString();
    }
    
    public static void main(String args[]) {
//      System.out.println(replaceKeyString("&nbsp;\r" + "</p>"));
        System.out.println(removeHtmlTag("<span>asdsa</a>"));
    }
}
