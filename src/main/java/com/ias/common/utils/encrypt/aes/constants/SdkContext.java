package com.ias.common.utils.encrypt.aes.constants;

/**
 * 系统参量的初始配置
 * @author Justin Hu
 *
 */
public class SdkContext {

	private static SdkConstants SdkConstants=null;
	
	/**
	 * 在使用系统context之前，需要设置常量类
	 * 常量类一般由客户端项目进行初始化
	 * @param sdkCon
	 */
	public static void init(SdkConstants sdkCon){
		SdkConstants=sdkCon;
	}
	/**
	 * 获取appkey
	 * @return
	 */
	public static String getAppKey(){
		if(SdkConstants!=null)
			return SdkConstants.getAppKey();
		else 
			return null;
	}
	
	/**
	 * 获取密钥
	 * @return
	 */
	public static String getSecret(){
		if(SdkConstants!=null)
			return SdkConstants.getSecret();
		else 
			return null;
	}
	
	/**
	 * 获取url域
	 * @return
	 */
	public static String getDomain(){
		if(SdkConstants!=null)
			return SdkConstants.getDomain();
		else 
			return null;
	}
}
