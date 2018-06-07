package com.ias.common.utils.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

import com.ias.common.utils.json.JsonUtil;


public class ResponseUtils {
	/**
	 * 发送文本。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain;charset=UTF-8", text);
	}
	
    public static void renderText(HttpServletResponse response, Object object) {
    	render(response, "text/plain;charset=UTF-8", JsonUtil.buildNormalBinder().toJson(object));
    }

	/**
	 * 发送json。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}

	/**
	 * 发送xml。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderXml(HttpServletResponse response, String text) {
		render(response, "text/xml;charset=UTF-8", text);
	}

	/**
	 * 发送内容。使用UTF-8编码。
	 * 
	 * @param response
	 * @param contentType
	 * @param text
	 */
	public static void render(HttpServletResponse response, String contentType,
			String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter out = null;
		try {
		    //System.out.println(">>>responseText:" + responseText);
		    out = response.getWriter();
		} catch (IOException ex2) {
		    ex2.printStackTrace();
		}
	
		out.print(text);
		out.flush();
	}
    
    /**
	 * 执行post请求
	 * @author 胡久洲
	 * @date 2014-1-22 下午6:14:51
	 * @param url	请求的地址
	 * @param argsjson	请求的参数
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String,String> map,HttpServletRequest request) {
		
		/* 老版本
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 0);// 连接超时
		httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 0); // 读取超时
		
		for(Cookie cookie:request.getCookies()){
			httpclient.getCookieStore().addCookie(new BasicClientCookie(cookie.getName(), cookie.getValue()));
		}
		*/
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		for(Cookie cookie:request.getCookies()){
			cookieStore.addCookie(new BasicClientCookie(cookie.getName(), cookie.getValue()));
		}
		CloseableHttpClient httpclient = HttpClientBuilder.create()
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())  
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .setDefaultRequestConfig(RequestConfig.custom()
                		.setConnectTimeout(5000) //设置连接超时时间，单位毫秒。
                		.setConnectionRequestTimeout(1000) //设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
                        .setSocketTimeout(5000) //请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
                        .build())
                .setDefaultCookieStore(cookieStore)
                .build();
		
		String restr = "";
		try {
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			
			for(String key : map.keySet()) {
				nvps.add(new BasicNameValuePair(key, map.get(key)));
			}
			
			// //设置代理对象 ip/代理名称,端口
			// httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
			// new HttpHost("127.0.0.1", 8080));
			// 实例化验证
			// CredentialsProvider credsProvider = new
			// BasicCredentialsProvider();
			// Credentials defaultcreds = new
			// UsernamePasswordCredentials("jdkleo", "123456");
			//
			// //创建验证
			// credsProvider.setCredentials(new AuthScope("127.0.0.1",8080),
			// defaultcreds);
			// httpclient.setCredentialsProvider(credsProvider);

			// httpclient.getState().setCredentials(new AuthScope("m00yhost",
			// 80, AuthScope.ANY_REALM), defaultcreds);

			HttpPost httpPost = new HttpPost(url);
			Enumeration<?> names = request.getHeaderNames();
	        while(names.hasMoreElements()){
	            String name = (String) names.nextElement();
	            httpPost.addHeader(name, request.getHeader(name));
	        }
//			Base64Utils base64code = new Base64Utils();
//			httpPost.setHeader("soauser", "liu");
//			httpPost.setHeader("yscw", "123");
			// httpPost.setHeader("Accept-Encoding", "gzip,deflate");
			// httpPost.setHeader("user-agent",
			// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; Alexa Toolbar; Maxthon 2.0)");
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			
			HttpHost target = null;

	        URI requestURI = httpPost.getURI();
	        if (requestURI.isAbsolute()) {
	            target = URIUtils.extractHost(requestURI);
	            if (target == null) {
	                throw new ClientProtocolException(
	                        "URI does not specify a valid host name: " + requestURI);
	            }
	        }
			
			restr = httpclient.execute(httpPost, responseHandler);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			httpclient.getConnectionManager().shutdown();
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// other
		// HttpEntity entity = response.getEntity();

		// System.out.println("Login form get: " + response.getStatusLine());
		// 调用HttpClient实例的execute方法:
		// (1)对于执行结果为2XX的情况，BasicResponseHandler会自动把 response
		// body以String方式返回
		// (2)对于3xx的response它会在内部自动重定向
		// (3)对于没有response body的情况，他会返回null。
		// 显示结果
		// BufferedReader reader = new BufferedReader(new
		// InputStreamReader(entity.getContent(), "UTF-8"));
		// StringBuffer stringBuffer = new StringBuffer();
		// String line = null;
		// while ((line = reader.readLine()) != null) {
		// stringBuffer.append(line);
		// }
		// if (entity != null) {
		// entity.consumeContent();
		// }
		
		return restr;
	}
}
