package com.ias.common.utils.encrypt;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.imageio.ImageIO;

public class Base64Util {
	
	/**
	 * 对base64加密的byte[]类型的数据进行base64解密
	 * 
	 */
	public static byte[] decode(byte[] businessXmlData) {
		if (businessXmlData == null) {
			return null;
		}
		return Base64.getDecoder().decode(businessXmlData);
	}

	/**
	 * 对byte[]加密生成的String类型的数据进行base64解密
	 * 
	 */
	public static byte[] decode(String businessXmlData) {
		return decode(businessXmlData.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 对byte[]类型的数据进行base64加密
	 * 
	 */
	public static String encode(byte[] byteData) {
		if (byteData == null) {
			return null;
		}
		return Base64.getEncoder().encodeToString(byteData);
	}

	/**
	 * 对String类型的数据进行base64加密
	 * 
	 */
	public static String encode(String strData) {
		if (strData == null) {
			return null;
		}
		return encode(strData.getBytes());
	}
	
	
	public static String encode(BufferedImage bufferedImage, String imgType) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, imgType, outputStream);
        return encode(outputStream);
    }

    public static String encode(ByteArrayOutputStream outputStream) {
        return encode(outputStream.toByteArray());
    }
}
