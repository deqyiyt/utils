package com.ias.common.utils.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ias.common.utils.date.TimeUtil;
import com.ias.common.utils.string.StringUtil;

public class JsonUtil {
    private ObjectMapper mapper;
    public ObjectMapper getMapper() {
        return mapper;
    }
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    public JsonUtil(Include inclusion){
    	 mapper =new ObjectMapper();
         mapper.setSerializationInclusion(inclusion);
         mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES , false);
         mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
         mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
         mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
         mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
         setDateFormat(TimeUtil.theTimeFormat);
    }
    /**
     * 创建输出全部属性
     * @return
     */
    public static JsonUtil buildNormalBinder(){
        return new JsonUtil(Include.ALWAYS);
    }
    /**
     * 创建只输出非空属性的
     * @return
     */
    public static JsonUtil buildNonNullBinder(){
        return new JsonUtil(Include.NON_NULL);
    }
    /**
     * 创建只输出初始值被改变的属性
     * @return
     */
    public static JsonUtil buildNonDefaultBinder(){
        return new JsonUtil(Include.NON_DEFAULT);
    }
    /**
     * 把json字符串转成对象
     * @param json
     * @param clazz
     * @return
     */
    public <T> T getJsonToObject(String json,Class<T> clazz){
        T object=null;
        if(StringUtil.isNotBlank(json)) {
            try {
                object=getMapper().readValue(json, clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    
    /**
     * 把json字符串转成对象
     * @param json
     * @param clazz
     * @return
     */
    public <T> T getMapToObject(Map<String, Object> map,Class<T> clazz){
        T object=null;
        if(map != null && !map.isEmpty()) {
            try {
                object = getMapper().readValue(this.toJson(map), clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    /**
     * 把JSON转成list
     * @param json
     * @param clazz
     * @return
     */
	public <T> List<T> getJsonToList(String json,Class<T> clazz){
    	List<T> list = null;
        if(StringUtil.isNotBlank(json)) {
           try {
        	   JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        	   list = getMapper().readValue(json,javaType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    /**
     * 把map转成combo数据格式的json格式
     * @return String (json)
     */
    public String getMapToJson(Map<String,String> map) {
        List<String[]> list =new ArrayList<String[]>();
        if (null != map && !map.isEmpty()) {
            for (String key : map.keySet()) {
                String[] strS = new String[2];
                strS[0] = key;
                strS[1] = map.get(key);
                list.add(strS);
            }
        }
        return toJson(list);
    }
    
    /**
     * 把JSON转成Map
     * @param json
     * @param keyclazz
     * @param valueclazz
     * @return
     */
    public <K,V> Map<K,V> getJsonToMap(String json,Class<K> keyclazz,Class<V> valueclazz){
        Map<K,V> object=null;
        if(StringUtil.isNotBlank(json)) {
            try {
                JavaType javaType = mapper.getTypeFactory().constructParametricType(Map.class, keyclazz,valueclazz);
                object=getMapper().readValue(json,javaType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    /**
     * 把JSON转成Object
     * @param json
     * @param keyclazz
     * @param valueclazz
     * @return
     */
	public Object getJsonToObject(String json,Class<?> objclazz,Class<?> ...pclazz){
        Object object=null;
        if(StringUtil.isNotBlank(json)) {
            try {
            	JavaType javaType = mapper.getTypeFactory().constructParametricType(objclazz, pclazz);
                object=getMapper().readValue(json,javaType);
            } catch (Exception e) {
            }
        }
        return object;
    }
    /**
     * 把对象转成字符串
     * @param object
     * @return
     */
    public String toJson(Object object){
        String json=null;
        try {
            json=getMapper().writeValueAsString(object);
        }  catch (Exception e) {
        	e.printStackTrace();
        }
        return json;
    }
    /**
     * 设置日期格式
     * @param pattern
     */
    public void setDateFormat(String pattern){
        if(StringUtil.isNotBlank(pattern)){
            DateFormat df=new SimpleDateFormat(pattern);
            getMapper().setDateFormat(df);
        }
    }
    
    /** 
     * 取出json的key值
     	String json = "{\"status\":0,\"message\":\"成功\",\"size\":1,\"total\":1,\"item\":[{\"name\":\"1234567\",\"create_time\":\"2017-05-10 18:06:10\",\"modify_time\":\"2017-05-13 11:22:53\",\"location\":{\"time\":1494645775,\"longitude\":121.46967776162,\"latitude\":31.027493372123,\"coord_type\":\"bd09ll\",\"floor\":\"\",\"radius\":40,\"speed\":0,\"direction\":0}}]}";
		Integer value = getValueByJson(json, "['item'][0]['location']['time']");
     * @author: jiuzhou.hu
     * @date:2017年5月16日上午11:33:07 
     * @param json
     * @param tagPath
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T> T readValueFromJson(String json, String tagPath) {
		ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
		try {
			engine.eval("var value = "+json+tagPath+";");
			return (T)engine.get("value");
		} catch (ScriptException e) {}
		return null;
	}
    
    /**
     * [{"a":"b","c":"d",...},{"a":"b","c":"d",...},...]
     * @author zhili.yang
     * @date 2017年11月16日 下午12:11:05
     * @param json
     * @return
     */
    public List<Map<String, String>> getJsonToMapList(String json) {
    	json = json.replaceAll("\n", "").replaceAll("\r", "");
		try {
			List<Map<String, String>> list = new ArrayList<>();
			JsonNode tree = mapper.readTree(json);
			for (JsonNode node : tree) {
				Map<String, String> map = new HashMap<>();
				Iterator<String> names = node.fieldNames();
				while (names.hasNext()) {
					String name = names.next();
					map.put(name, node.get(name).asText());
				}
				list.add(map);
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
}