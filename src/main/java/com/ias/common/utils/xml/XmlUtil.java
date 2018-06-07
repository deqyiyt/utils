package com.ias.common.utils.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ias.common.utils.json.JsonUtil;
import com.ias.common.utils.string.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 输出xml和解析xml的工具类
 *@ClassName:XmlUtil
 *@date :2012-9-29 上午9:51:28
 */
@SuppressWarnings({ "rawtypes", "unchecked" }) 
public class XmlUtil{
	
	private static final Logger log = LoggerFactory.getLogger(XmlUtil.class);
	
    /**
     * java 转换成xml
     * @Title: toXml 
     * @param obj 对象实例
     * @return String xml字符串
     */
    public static String toXml(Object obj){
    	XStream xstream = new XStream(new XppDriver(new NoNameCoder()) {

            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    // 对所有xml节点的转换都增加CDATA标记
                    boolean cdata = true;

                    @Override
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                    }

                    @Override
                    public String encodeNode(String name) {
                        return name;
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (cdata) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
//            XStream xstream=new XStream(new DomDriver()); //直接用jaxp dom来解释
//            XStream xstream=new XStream(new DomDriver("utf-8")); //指定编码解析器,直接用jaxp dom来解释
        
        ////如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性
        xstream.processAnnotations(obj.getClass()); //通过注解方式的，一定要有这句话
        return xstream.toXML(obj);
    }
    
    /**
     *  将传入xml文本转换成Java对象
     * @Title: toBean 
     * @param xmlStr
     * @param cls  xml对应的class类
     * @return T   xml对应的class类的实例对象
     * 
     * 调用的方法实例：PersonBean person=XmlUtil.toBean(xmlStr, PersonBean.class);
     */
	public static <T> T  toBean(String xmlStr,Class<T> cls){
        //注意：不是new Xstream(); 否则报错：java.lang.NoClassDefFoundError: org/xmlpull/v1/XmlPullParserFactory
        XStream xstream=new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T obj=(T)xstream.fromXML(xmlStr);
        return obj;            
    } 

   /**
     * 写到xml文件中去
     * @Title: writeXMLFile 
     * @param obj 对象
     * @param absPath 绝对路径
     * @param fileName    文件名
     * @return boolean
     */
  
    public static boolean toXMLFile(Object obj, String absPath, String fileName ){
        String strXml = toXml(obj);
        String filePath = absPath + fileName;
        File file = new File(filePath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("创建{"+ filePath +"}文件失败!!!" + e.getStackTrace());
                return false ;
            }
        }// end if 
        OutputStream ous = null ;
        try {
            ous = new FileOutputStream(file);
            ous.write(strXml.getBytes());
            ous.flush();
        } catch (Exception e1) {
            log.error("写{"+ filePath +"}文件失败!!!" + e1.getStackTrace());
            return false;
        }finally{
            if(ous != null )
                try {
                    ous.close();
                } catch (IOException e) {
                    log.error("写{"+ filePath +"}文件关闭输出流异常!!!" + e.getStackTrace());
                }
        }
        return true ;
    }
    
    /**
     * 从xml文件读取报文
     * @Title: toBeanFromFile 
     * @param absPath 绝对路径
     * @param fileName 文件名
     * @param cls
     * @throws Exception 
     * @return T
     */
	public static <T> T  toBeanFromFile(String absPath, String fileName,Class<T> cls) throws Exception{
        String filePath = absPath +fileName;
        InputStream ins = null ;
        try {
            ins = new FileInputStream(new File(filePath ));
        } catch (Exception e) {
            throw new Exception("读{"+ filePath +"}文件失败！", e);
        }
        
        XStream xstream=new XStream(new DomDriver("UTF-8"));
        xstream.processAnnotations(cls);
        T obj =null;
        try {
            obj = (T)xstream.fromXML(ins);
        } catch (Exception e) {
            throw new Exception("解析{"+ filePath +"}文件失败！",e);
        }
        if(ins != null)
            ins.close();
        return obj;            
    }
    
    /** 
     * 获取xml中的空属性
     * @param xmlStr 
     * @param needRootKey 是否需要在返回的map里加根节点键 
     * @return 
     * @throws DocumentException 
     */  
    public static List<String> xmlNullValue(String xmlStr) throws DocumentException {
    	Map<String, Object> map = xml2map(xmlStr, false);
    	List<String> list = new ArrayList<String>();
    	for(String key:map.keySet()) {
    		if(StringUtil.isNotBlank(map.get(key))) {
    			if (map.get(key) instanceof Map) {
    				xmlNullValue((Map)map.get(key), list);
        		}
    		} else {
    			list.add(key);
    		}
    	}
        return list;  
    }
    
    /** 
     * 获取xml中的空属性
     * @param xmlStr 
     * @param needRootKey 是否需要在返回的map里加根节点键 
     * @return 
     * @throws DocumentException 
     */  
    private static List<String> xmlNullValue(Map map, List<String> list) throws DocumentException {
    	for(Object key:map.keySet()) {
    		if(StringUtil.isNotBlank(map.get(key))) {
    			if (map.get(key) instanceof Map) {
    				xmlNullValue((Map)map.get(key), list);
        		}
    		} else {
    			list.add((String)key);
    		}
    	}
        return list;  
    }
          
    /** 
     * xml转map 不带属性 
     * @param xmlStr 
     * @param needRootKey 是否需要在返回的map里加根节点键 
     * @return 
     * @throws DocumentException 
     */  
    public static Map<String,Object> xml2map(String xmlStr, boolean needRootKey) throws DocumentException {  
        Document doc = DocumentHelper.parseText(xmlStr);  
        Element root = doc.getRootElement();  
        Map<String, Object> map = xml2map(root);  
        if(root.elements().size()==0 && root.attributes().size()==0){  
            return map;  
        }  
        if(needRootKey){  
            //在返回的map里加根节点键（如果需要）  
            Map<String, Object> rootMap = new HashMap<String, Object>();  
            rootMap.put(root.getName(), map);  
            return rootMap;  
        }  
        return map;  
    }  
  
    /** 
     * xml转map 带属性 
     * @param xmlStr 
     * @param needRootKey 是否需要在返回的map里加根节点键 
     * @return 
     * @throws DocumentException 
     */  
    public static Map xml2mapWithAttr(String xmlStr, boolean needRootKey) throws DocumentException {  
        Document doc = DocumentHelper.parseText(xmlStr);  
        Element root = doc.getRootElement();  
        Map<String, Object> map = (Map<String, Object>) xml2mapWithAttr(root);  
        if(root.elements().size()==0 && root.attributes().size()==0){  
            return map; //根节点只有一个文本内容  
        }  
        if(needRootKey){  
            //在返回的map里加根节点键（如果需要）  
            Map<String, Object> rootMap = new HashMap<String, Object>();  
            rootMap.put(root.getName(), map);  
            return rootMap;  
        }  
        return map;  
    }  
  
    /** 
     * xml转map 不带属性 
     * @param e 
     * @return 
     */  
    private static Map<String,Object> xml2map(Element e) {  
        Map<String,Object> map = new LinkedHashMap<String,Object>();  
        List<Object> list = e.elements();  
        if (list.size() > 0) {  
            for (int i = 0; i < list.size(); i++) {  
                Element iter = (Element) list.get(i);  
                List<Object> mapList = new ArrayList<Object>();  
  
                if (iter.elements().size() > 0) {  
                    Map m = xml2map(iter);  
                    if (map.get(iter.getName()) != null) {  
                        Object obj = map.get(iter.getName());  
                        if (!(obj instanceof List)) {
                            mapList = new ArrayList<Object>();  
                            mapList.add(obj);  
                            mapList.add(m);  
                        }  
                        if (obj instanceof List) {  
                            mapList = (List) obj;  
                            mapList.add(m);  
                        }  
                        map.put(iter.getName(), mapList);  
                    } else  
                        map.put(iter.getName(), m);  
                } else {  
                    if (map.get(iter.getName()) != null) {  
                        Object obj = map.get(iter.getName());  
                        if (!(obj instanceof List)) {  
                            mapList = new ArrayList<Object>();  
                            mapList.add(obj);  
                            mapList.add(iter.getText());  
                        }  
                        if (obj instanceof List) {  
                            mapList = (List) obj;  
                            mapList.add(iter.getText());  
                        }  
                        map.put(iter.getName(), mapList);  
                    } else  
                        map.put(iter.getName(), iter.getText());  
                }  
            }  
        } else  
            map.put(e.getName(), e.getText());  
        return map;  
    }  
  
    /** 
     * xml转map 带属性 
     * @param e 
     * @return 
     */  
    private static Map xml2mapWithAttr(Element element) {  
        Map<String, Object> map = new LinkedHashMap<String, Object>();  
  
        List<Element> list = element.elements();  
        List<Attribute> listAttr0 = element.attributes(); // 当前节点的所有属性的list  
        for (Attribute attr : listAttr0) {  
            map.put("@" + attr.getName(), attr.getValue());  
        }  
        if (list.size() > 0) {  
  
            for (int i = 0; i < list.size(); i++) {  
                Element iter = list.get(i);  
                List<Object> mapList = new ArrayList<Object>();  
  
                if (iter.elements().size() > 0) {  
                    Map m = xml2mapWithAttr(iter);  
                    if (map.get(iter.getName()) != null) {  
                        Object obj = map.get(iter.getName());  
                        if (!(obj instanceof List)) {  
                            mapList = new ArrayList<Object>();  
                            mapList.add(obj);  
                            mapList.add(m);  
                        }  
                        if (obj instanceof List) {  
                            mapList = (List) obj;  
                            mapList.add(m);  
                        }  
                        map.put(iter.getName(), mapList);  
                    } else  
                        map.put(iter.getName(), m);  
                } else {  
  
                    List<Attribute> listAttr = iter.attributes(); // 当前节点的所有属性的list  
                    Map<String, Object> attrMap = null;  
                    boolean hasAttributes = false;  
                    if (listAttr.size() > 0) {  
                        hasAttributes = true;  
                        attrMap = new LinkedHashMap<String, Object>();  
                        for (Attribute attr : listAttr) {  
                            attrMap.put("@" + attr.getName(), attr.getValue());  
                        }  
                    }  
  
                    if (map.get(iter.getName()) != null) {  
                        Object obj = map.get(iter.getName());  
                        if (!(obj instanceof List)) {  
                            mapList = new ArrayList<Object>();  
                            mapList.add(obj);  
                            // mapList.add(iter.getText());  
                            if (hasAttributes) {  
                                attrMap.put("#text", iter.getText());  
                                mapList.add(attrMap);  
                            } else {  
                                mapList.add(iter.getText());  
                            }  
                        }  
                        if (obj instanceof List) {  
                            mapList = (List) obj;  
                            // mapList.add(iter.getText());  
                            if (hasAttributes) {  
                                attrMap.put("#text", iter.getText());  
                                mapList.add(attrMap);  
                            } else {  
                                mapList.add(iter.getText());  
                            }  
                        }  
                        map.put(iter.getName(), mapList);  
                    } else {  
                        // map.put(iter.getName(), iter.getText());  
                        if (hasAttributes) {  
                            attrMap.put("#text", iter.getText());  
                            map.put(iter.getName(), attrMap);  
                        } else {  
                            map.put(iter.getName(), iter.getText());  
                        }  
                    }  
                }  
            }  
        } else {  
            // 根节点的  
            if (listAttr0.size() > 0) {  
                map.put("#text", element.getText());  
            } else {  
                map.put(element.getName(), element.getText());  
            }  
        }  
        return map;  
    }  
      
    /** 
     * map转xml map中没有根节点的键 
     * @param map 
     * @param rootName 
     * @throws DocumentException 
     * @throws IOException 
     */  
    public static Document map2xml(Map<String, Object> map, String rootName) throws DocumentException, IOException  {  
        Document doc = DocumentHelper.createDocument();  
        Element root = DocumentHelper.createElement(rootName);  
        doc.add(root);  
        map2xml(map, root);  
        //System.out.println(doc.asXML());  
        //System.out.println(formatXml(doc));  
        return doc;  
    }  
      
    /** 
     * map转xml map中含有根节点的键 
     * @param map 
     * @throws DocumentException 
     * @throws IOException 
     */  
    public static Document map2xml(Map<String, Object> map) throws DocumentException, IOException  {  
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();  
        if(entries.hasNext()){ //获取第一个键创建根节点  
            Map.Entry<String, Object> entry = entries.next();  
            Document doc = DocumentHelper.createDocument();  
            Element root = DocumentHelper.createElement(entry.getKey());  
            doc.add(root);  
            map2xml((Map)entry.getValue(), root);  
            //System.out.println(doc.asXML());  
            //System.out.println(formatXml(doc));  
            return doc;  
        }  
        return null;  
    }  
      
    /** 
     * map转xml 
     * @param map 
     * @param body xml元素 
     * @return 
     */  
    private static Element map2xml(Map<String, Object> map, Element body) {  
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();  
        while (entries.hasNext()) {  
            Map.Entry<String, Object> entry = entries.next();  
            String key = entry.getKey();  
            Object value = entry.getValue();  
            if(key.startsWith("@")){    //属性  
                body.addAttribute(key.substring(1, key.length()), value.toString());  
            } else if(key.equals("#text")){ //有属性时的文本  
                body.setText(value.toString());  
            } else {  
                if(value instanceof java.util.List ){  
                    List list = (List)value;  
                    Object obj;  
                    for(int i=0; i<list.size(); i++){  
                        obj = list.get(i);  
                        //list里是map或String，不会存在list里直接是list的，  
                        if(obj instanceof java.util.Map){  
                            Element subElement = body.addElement(key);  
                            map2xml((Map)list.get(i), subElement);  
                        } else {  
                            body.addElement(key).setText((String)list.get(i));  
                        }  
                    }  
                } else if(value instanceof java.util.Map ){  
                    Element subElement = body.addElement(key);  
                    map2xml((Map)value, subElement);  
                } else {  
                    body.addElement(key).setText(value.toString());  
                }  
            }  
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
        }  
        return body;  
    }  
      
    /** 
     * 格式化输出xml 
     * @param xmlStr 
     * @return 
     * @throws DocumentException 
     * @throws IOException 
     */  
    public static String formatXml(String xmlStr) throws DocumentException, IOException  {  
        Document document = DocumentHelper.parseText(xmlStr);  
        return formatXml(document);  
    }  
      
    /** 
     * 格式化输出xml 
     * @param document 
     * @return 
     * @throws DocumentException 
     * @throws IOException 
     */  
    public static String formatXml(Document document) throws DocumentException, IOException  {  
        // 格式化输出格式  
        OutputFormat format = OutputFormat.createPrettyPrint();  
        //format.setEncoding("UTF-8");  
        StringWriter writer = new StringWriter();  
        // 格式化输出流  
        XMLWriter xmlWriter = new XMLWriter(writer, format);  
        // 将document写入到输出流  
        xmlWriter.write(document);  
        xmlWriter.close();  
        return writer.toString();  
    }  
    
    /**
     * 格式化xml中value值里面的非法字符
     * @Project SC
     * @Package com.ias.common.utils.type
     * @Method replaceXml方法.<br>
     * @author hjz
     * @date 2014-12-17 下午2:38:20
     * @param xml
     * @return
     */
    public static String replaceXml(String xml) {
        String pat = "[\"\"](.*?)[\"\"]";
        String[] str = xml.split(pat);
        
        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(xml);
        List<String> values = new ArrayList<String>();
        while(m.find()){
            values.add(m.group().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
        }
        StringBuffer sf = new StringBuffer();
        for(int i = 0; i < str.length; i++) {
            sf.append(str[i]);
            if(values.size() > i) {
                sf.append(values.get(i));
            }
        }
        
        return sf.toString();
    }
    
    public static void main(String[] args) throws DocumentException, IOException {
    	String xmlStr = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"
    	+"<property>"
    	+"<returncode>200</returncode>"
    	+"<key>03643.com</key>"
    	+"<original>211 : Domain name is not available</original>"
    	+"</property>";
    	TestBean test = XmlUtil.toBean(xmlStr, TestBean.class);
    	System.out.println(test.getUrl());
    	
        Map<String, Object> map = xml2map(xmlStr, false);  
        // long begin = System.currentTimeMillis();  
        // for(int i=0; i<1000; i++){  
        // map = (Map<String, Object>) xml2mapWithAttr(doc.getRootElement());  
        // }  
        // System.out.println("耗时:"+(System.currentTimeMillis()-begin));  
        System.out.println(JsonUtil.buildNormalBinder().toJson(map)); // 格式化输出  
          
        Document doc = map2xml(map, "root");  
        //Document doc = map2xml(map); //map中含有根节点的键  
        System.out.println(formatXml(doc));  
        
        System.out.println(xmlNullValue(xmlStr)); // 格式化输出  
	}
    
}

@XStreamAlias("property")
class TestBean {
	@XStreamAlias("returncode")
	private Integer returncode;
	
	@XStreamAlias("key")
	private String url;
	
	@XStreamAlias("original")
	private String original;

	/**
	 * returncode的获取.
	 * @return Integer
	 */
	public Integer getReturncode() {
		return returncode;
	}

	/**
	 * 设定returncode的值.
	 * @param Integer
	 */
	public void setReturncode(Integer returncode) {
		this.returncode = returncode;
	}

	/**
	 * original的获取.
	 * @return String
	 */
	public String getOriginal() {
		return original;
	}

	/**
	 * 设定original的值.
	 * @param String
	 */
	public void setOriginal(String original) {
		this.original = original;
	}

	/**
	 * url的获取.
	 * @return String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设定url的值.
	 * @param String
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
