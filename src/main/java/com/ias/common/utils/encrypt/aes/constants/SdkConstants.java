package com.ias.common.utils.encrypt.aes.constants;

/**
 * SDK的系统常量
 */
public class SdkConstants {

	private String appKey;
	
	private String secret;
	
	private String domain;
	
	public SdkConstants() {
		super();
	}

	public SdkConstants(String appKey, String secret, String domain) {
		super();
		this.appKey = appKey;
		this.secret = secret;
		this.domain = domain;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
