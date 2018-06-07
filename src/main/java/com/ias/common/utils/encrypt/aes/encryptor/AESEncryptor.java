package com.ias.common.utils.encrypt.aes.encryptor;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.ias.common.utils.encrypt.aes.EncryptUtil;
import com.ias.common.utils.encrypt.aes.convertor.Base64Convertor;
import com.ias.common.utils.resource.ConfigurationUtil;
import com.ias.common.utils.string.StringUtil;

public class AESEncryptor implements Encryptor {
	
	private Base64Convertor base64 = new Base64Convertor();
	private SecretKey key;
	
	public AESEncryptor(){
		String masterkey = ConfigurationUtil.getInstance().getUtilityConfiguration("aes.masterkey");	
		key = new SecretKeySpec(base64.parse(masterkey), "AES");
		assert key !=null : "AES Secret Key initiate failed";
	}
	
	@Override
	public String encrypt(String plainText) throws EncryptionException {
		
		return encrypt(plainText,null);
	}
	
	
	public String encrypt(String plainText,String masterkey) throws EncryptionException {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			if(StringUtil.isBlank(masterkey)){
				cipher.init(Cipher.ENCRYPT_MODE, key);
			}
			else{
				cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(base64.parse(masterkey), "AES"));
			}
				
			byte[] c = cipher.doFinal(plainText.getBytes(ConfigurationUtil.DEFAULT_ENCODING));
			return base64.format(c);
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	    } catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	@Override
	public String decrypt(String cipherText) throws EncryptionException {
		
		return decrypt(cipherText,null);
	}
	
	
	public String decrypt(String cipherText,String masterkey) throws EncryptionException {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			if(StringUtil.isBlank(masterkey)){
				cipher.init(Cipher.DECRYPT_MODE, key);
			}
			else{
				cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(base64.parse(masterkey), "AES"));
			}
			byte[] b = cipher.doFinal(base64.parse(cipherText));
			return new String(b, ConfigurationUtil.DEFAULT_ENCODING);
	   } catch (NoSuchAlgorithmException e) {  
	           e.printStackTrace();  
	   } catch (NoSuchPaddingException e) {  
	           e.printStackTrace();  
	   } catch (InvalidKeyException e) {  
	           e.printStackTrace();  
	   } catch (IllegalBlockSizeException e) {  
	           e.printStackTrace();  
	   } catch (BadPaddingException e) {  
	           e.printStackTrace();  
	   } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}  
		return null;  
	}

	public static void main(String[] args) {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			SecureRandom random = EncryptUtil.getSecureRandom();
			String skeylen = ConfigurationUtil.getInstance().getUtilityConfiguration("aes.key.length");
			generator.init(Integer.parseInt(skeylen), random);
			SecretKey key = generator.generateKey();
			System.out.println("========== AES Key ==========");
			Base64Convertor base64 = new Base64Convertor();
			System.out.println("aes.masterkey=" +  base64.format(key.getEncoded()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
