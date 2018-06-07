package com.ias.common.utils.encrypt.aes.convertor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ias.common.utils.encrypt.aes.EncryptUtil;
/**
 * map与bean的互相转换
 * @author Justin Hu
 *
 */
public class MapAndBeanConvertor {

	private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);	
	
	public static Map<String,Object> toMap(Object thisObj)  
	  {  
	    Map<String,Object> map = new HashMap<String,Object>();  
	    Class<?> c;  
	    try  
	    {  
	      c = Class.forName(thisObj.getClass().getName());  
	      Method[] m = c.getMethods();  
	      for (int i = 0; i < m.length; i++)  
	      {  
	        String method = m[i].getName();  
	        if (method.startsWith("get")&& !method.equals("getClass"))  
	        {  
	          try{  
	          Object value = m[i].invoke(thisObj);  
	          if (value != null)  
	          {  
	            String key=method.substring(3);  
	            key=key.substring(0,1).toLowerCase()+key.substring(1);  
	            map.put(key, value);  
	          }  
	          }catch (Exception e) {  
	            logger.error("error:"+method);
	          }  
	        }  
	      }  
	    }  
	    catch (Exception e)  
	    {  
	    	logger.error("",e);
	    }  
	    return map;  
	  }  
	
	
	public static void main(String[] args)throws Exception{
		
		TMapTest tt=new TMapTest();
		tt.setId(3l);
		tt.setName("sdf3d在的33在的在");
		
		Map<String,Object> map=toMap(tt);
		
		System.out.println(map.get("name"));
	}
}

class TMapTest{
	private Long id;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
