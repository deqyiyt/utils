package com.ias.common.utils.http;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.ias.common.utils.cn.CharsetUtil;
import com.ias.common.utils.date.DateUtil;
import com.ias.common.utils.string.StringUtil;
import com.ias.common.utils.validate.RegexUtils;

/**
 * Created by Administrator on 2016/5/13 0013.
 */
public class HttpUtil {

    final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    
    private HttpUtil() {}

    public static String getURL(HttpServletRequest req) {
        String scheme = req.getScheme(); // http
        String serverName = req.getServerName(); // hostname.com
        int serverPort = req.getServerPort(); // 80
        String contextPath = req.getContextPath(); // /mywebapp
        String servletPath = req.getServletPath(); // /servlet/MyServlet
        String pathInfo = req.getPathInfo(); // /a/b;c=123
        String queryString = req.getQueryString(); // d=789

        // Reconstruct original requesting URL
        StringBuffer url = new StringBuffer();
        url.append(scheme).append("://").append(serverName);

        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath).append(servletPath);

        if (pathInfo != null) {
            url.append(pathInfo);
        }
        if (queryString != null) {
            url.append("?").append(queryString);
        }
        return url.toString();
    }

    public static String getReqURLWithoutHost(HttpServletRequest req) {
        String contextPath = req.getContextPath(); // /mywebapp
        String servletPath = req.getServletPath(); // /servlet/MyServlet
        String pathInfo = req.getPathInfo(); // /a/b;c=123
        String queryString = req.getQueryString(); // d=789

        // Reconstruct original requesting URL
        StringBuffer url = new StringBuffer();

        url.append(contextPath).append(servletPath);

        if (pathInfo != null) {
            url.append(pathInfo);
        }
        if (queryString != null) {
            url.append("?").append(queryString);
        }
        return url.toString();
    }

    public static String getRemoteAddr(HttpServletRequest req) {
        String ipAddress = req.getHeader("X-real-ip");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }
        logger.debug("<<<<<>>>>>获取到的ip={}", ipAddress);
        if(StringUtils.hasText(ipAddress)) {
            return StringUtils.trimWhitespace(ipAddress.split(",")[0]);
        }
        return "";
    }
    
    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
	}


    public static void writeOutWithTitle(String filename, String title, List<String> content, HttpServletResponse response) {
        List<String> result = new ArrayList<>();
        result.add(title);
        result.addAll(content);
        writeOut(filename, result, response);
    }

    public static void writeOut(String filename, List<String> content, HttpServletResponse response) {
        writeOut(filename, "txt", content, response);
    }


    public static void writeOut(String filename, String fileSuffix, List<String> content, HttpServletResponse response) {
        try {
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode(filename + "_" + DateUtil.getStringDateShort() + "." + fileSuffix, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("编码转换异常", e);
        }
        try (final PrintWriter pw = response.getWriter()) {
            content.forEach(str -> {
                pw.println(str);
            });
            pw.flush();
        } catch (Exception e) {
            logger.error("写出文件出现异常", e);
        }
    }
    
    /**
     * 解析出url参数中的键值对
     * 如 "Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param urlParam  	参数
     * @return
     */
    public static Map<String, String> ParamRequest(String urlParam) {
    	Map<String, String> mapRequest = new HashMap<String, String>();
    	String[] arrSplit=null;

    	if(urlParam==null) {
    		return mapRequest;
    	}
    	//每个键值为一组 www.2cto.com
    	arrSplit=urlParam.split("[&]");
    	for(String strSplit:arrSplit) {
    		String[] arrSplitEqual=null;
    		arrSplitEqual= strSplit.split("[=]");
    		//解析出键值
    		if(arrSplitEqual.length>1) {
    			//正确解析
    			mapRequest.put(arrSplitEqual[0], CharsetUtil.URLDecodeFromUTF8(arrSplitEqual[1]));
    		}
    		else {
    			if(arrSplitEqual[0]!="") {
	    			//只有参数没有值，不加入
	    			mapRequest.put(arrSplitEqual[0], "");
    			}
    		}
    	}
    	return mapRequest;
    }
    
    /**
	 * 判断是否内网IP
	 * @param ipAddress IPv4标准地址
	 */
	public static boolean checkRemoteIpAddress(String ipAddress){/*判断是否是内网IP*/
		if(StringUtils.isEmpty(ipAddress)) {
			return true;
		}
		if(ipAddress.equals("localhost")) {
			return true;
		}
		if(!RegexUtils.checkIpAddress(ipAddress)) {
			return true;
		}
		
	    boolean isInnerIp = false;//默认给定IP不是内网IP    
	    long ipNum = getIpNum(ipAddress);    
	    /**
	     * 私有IP：A类  10.0.0.0    -10.255.255.255
	     *       B类  172.16.0.0  -172.31.255.255   
	     *       C类  192.168.0.0 -192.168.255.255  
	     *       D类   127.0.0.0   -127.255.255.255(环回地址) 
	     **/   
	    long aBegin = getIpNum("10.0.0.0");    
	    long aEnd = getIpNum("10.255.255.255");    
	    long bBegin = getIpNum("172.16.0.0");    
	    long bEnd = getIpNum("172.31.255.255");    
	    long cBegin = getIpNum("192.168.0.0");    
	    long cEnd = getIpNum("192.168.255.255"); 
	    long dBegin = getIpNum("127.0.0.0");    
	    long dEnd = getIpNum("127.255.255.255");
	    isInnerIp = isInner(ipNum,aBegin,aEnd) || isInner(ipNum,bBegin,bEnd) || isInner(ipNum,cBegin,cEnd) || isInner(ipNum,dBegin,dEnd);    
	    return isInnerIp;
	}
	 
	private static long getIpNum(String ipAddress) {/*获取IP数*/    
		String [] ip = ipAddress.split("\\.");    
		long a = Integer.parseInt(ip[0]);    
		long b = Integer.parseInt(ip[1]);    
		long c = Integer.parseInt(ip[2]);    
		long d = Integer.parseInt(ip[3]);
		long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;    
		return ipNum;    
	}   

	private static boolean isInner(long userIp,long begin,long end){    
		return (userIp>=begin) && (userIp<=end);    
	} 
	
	/**
	 * 更改URL的参数，如果没有，则增加
	 * @author jiuzhou.hu
	 * @date 2017年9月21日 下午8:09:50
	 * @param url
	 * @param name
	 * @param value
	 * @return
	 */
	public static String changeUrlParam(String url, String name, String value) {
	    if(StringUtil.isNotBlank(url) && StringUtil.isNotBlank(value)) {  
            int index = url.indexOf(name + "=");  
            if(index > -1) {  
                StringBuilder sb = new StringBuilder();  
                sb.append(url.substring(0, index)).append(name + "=")  
                  .append(value);  
                int idx = url.indexOf("&", index);  
                if(idx != -1) {  
                    sb.append(url.substring(idx));  
                }  
                url = sb.toString();  
            } else if(url.indexOf("?") > -1) {
                url += "&" + name + "=" +value;
            } else {
                url += "?" + name + "=" +value;
            }
        }
        return url;
    }
	
	/**
     * 检测当前URL是否可连接或是否有效, 最多连接网络 5 次, 如果 5 次都不成功说明该地址不存在或视为无效地址.
     * @param url 指定URL网络地址
     * @return 状态码
     */
    public static synchronized int isConnect(String url) {
        URL urlStr;
        int state = -1;
        HttpURLConnection connection;
        int counts = 0;
        if (url == null || url.length() <= 0) {
            return state;
        }
        while (counts < 5) {
            try {
                urlStr = new URL(url);
                connection = (HttpURLConnection) urlStr.openConnection();
                state = connection.getResponseCode();
                break;
            } catch (Exception ex) {
                counts++;
                logger.info("loop :" + counts);
                continue;
            }
        }
        return state;
    }
    
    /**
     * 处理url
     * url为null返回null，url为空串或以http://或https://开头，则加上http://，其他情况返回原参数。
     * @param url
     * @return
     */
    public static String handelUrl(String url) {
        if (url == null) {
            return null;
        }
        url = url.trim();
        if (url.equals("") || url.startsWith("http://")
                || url.startsWith("https://")) {
            return url;
        } else {
            return "http://" + url.trim();
        }
    }

    
	public static void main(String[] args) {
//		System.out.println(checkRemoteIpAddress("localhost"));
		System.out.println(changeUrlParam("http://localhost:8301/charts/hm", "hospName", "六院东院"));
	}
}
