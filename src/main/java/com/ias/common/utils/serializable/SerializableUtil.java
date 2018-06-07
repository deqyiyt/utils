package com.ias.common.utils.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 序列化util
 * @author Justin Hu
 *
 */
public class SerializableUtil {

	public static String convert2String(Serializable obj){
		
		try{
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);  
	        objectOutputStream.writeObject(obj);    
	        String serStr = byteArrayOutputStream.toString("ISO-8859-1");  
	        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");  
	          
	        objectOutputStream.close();  
	        byteArrayOutputStream.close();
	        
	        return serStr;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 转成对象
	 * @param serStr
	 * @return
	 */
	public static Object convert2Object(String serStr){
		
		try{
			String redStr = java.net.URLDecoder.decode(serStr, "UTF-8");  
	        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));  
	        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);   
	        Object obj=objectInputStream.readObject();   
	        objectInputStream.close();  
	        byteArrayInputStream.close();  
	        
	        return obj;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}

