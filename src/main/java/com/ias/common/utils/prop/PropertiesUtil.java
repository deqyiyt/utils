package com.ias.common.utils.prop;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *********************************************** 
 * @Copyright (c) by ysc All right reserved.
 * @Create Date: 2014-2-26 下午5:27:12
 * @Create Author: hujz
 * @File Name: PropertiesUtil
 * @Function: 配置文件操作类
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class PropertiesUtil {
	protected final static Log logger = LogFactory.getLog(PropertiesUtil.class);
	
	private static Map<String,PropertiesUtil> systemParamMap;
	
	private Map<String, String> param;
	
	/**
	 * @Title fileName
	 * @type String
	 * @date 2014-2-26 下午5:26:29
	 * 含义 配置文件的名称，不要后缀名
	 */
	private String fileName;
	
	private PropertiesUtil(String fileName){
		this.fileName = fileName;
		this.init();
	}
	
	
	/**
	 * 获得配置文件
	 * @author 胡久洲
	 * @date 2014-2-26 下午5:26:47
	 * @param fileName	配置文件的名称，不要后缀名
	 * @return
	 */
	public static PropertiesUtil getInstance(String fileName){
		if(systemParamMap == null) {
			systemParamMap = new HashMap<String, PropertiesUtil>();
		}
		if(systemParamMap.get(fileName) == null) {
			systemParamMap.put(fileName, new PropertiesUtil(fileName));
		}
		return systemParamMap.get(fileName);
	}
	
	private void init(){
		ResourceBundle param = ResourceBundle.getBundle(fileName);
		Enumeration<String> keys = param.getKeys();
		this.param = new HashMap<String, String>();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			this.param.put(key, param.getString(key));
		}
		
	}
	
	/**
	 * 读取.properties配置文件的内容至Map中
	 * @param propertiesFile
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map read(String propertiesFile) {
		ResourceBundle rb = ResourceBundle.getBundle(propertiesFile);
		Map map = new HashMap();
		Enumeration enu = rb.getKeys();
		while (enu.hasMoreElements()) {
			Object obj = enu.nextElement();
			Object objv = rb.getObject(obj.toString());
			
			if( logger.isDebugEnabled()){
				logger.debug("property ["+ obj +"]:"+ objv);
			}
			
			map.put(obj, objv);
		}
		return map;
	}


	public Map<String, String> getParam() {
		return param;
	}
	
	public String getValue(String key){
		return param.get(key);
	}

}
