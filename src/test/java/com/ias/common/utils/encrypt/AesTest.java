package com.ias.common.utils.encrypt;

import org.junit.Test;

import com.ias.common.utils.encrypt.aes.AesAuthUtil;

public class AesTest {
	
	@Test
	public void encrypt() {
		String ct1 = AesAuthUtil.encrypt("https://wechat.fuyitianjian.net/h5/san.html");
		System.out.println(ct1);
		System.out.println(AesAuthUtil.decrypt(ct1));
	}
	
	@Test
	public void decrypt() {
		String ct = "w8Dlpnrw8jiNAnnCk8Xfcw==";
		System.out.println(AesAuthUtil.decrypt(ct));
	}
}


