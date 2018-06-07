package com.ias.common.utils.encrypt.aes.constants;

import com.ias.common.utils.encrypt.aes.EncryptUtil;

public class AesEncrypt {

	public static String encrypt(String plainText) throws Exception{
		
		return EncryptUtil.getInstance().encrypt(plainText, SdkContext.getSecret());
	}
	
	
	public static String decrypt(String cipherText) throws Exception{
		
		return EncryptUtil.getInstance().decrypt(cipherText, SdkContext.getSecret());
	}
}
