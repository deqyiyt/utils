package com.ias.common.utils.encrypt;

import com.ias.common.utils.encrypt.aes.EncryptUtil;

public class CommonUtil {
	
	public static String hash(String plainText, String salt){
		return EncryptUtil.getInstance().hash(plainText, salt);
	}
	
	public static String base64Decode(String param){
		
		return EncryptUtil.getInstance().base64Decode(param);
	}
}
