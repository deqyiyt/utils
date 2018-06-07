package com.ias.common.utils.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.ias.common.utils.date.TimeUtil;
import com.ias.common.utils.string.StringUtil;

/**
 * 反射的Utils函数集合
 */
@SuppressWarnings({"unchecked","rawtypes","unused"})
public class ReflectUtils {
	
	private ReflectUtils() {
	}

	public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
		return getDeclaredField(object.getClass(), propertyName);
	}

	
	public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
	}
	/**
	 * 直接获取属性值
	 * @param object
	 * @param propertyName
	 * @return
	 */
	public static Object getNameProperty(Object object, String propertyName) {
		Field field=null;
		try {
			field = getDeclaredField(object, propertyName);
		} catch (NoSuchFieldException e1) {
		}
		Object result = null;
		if(null!=field){
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			try {
				result = field.get(object);
			} catch (Exception e) {
			}
			field.setAccessible(accessible);
		}
		return result;
	}
	/**
	 * 支持a.b.c
	 * @param object
	 * @param propertyName
	 * @return
	 */
	public static Object newForceGetProperty(Object object, String propertyName) {
		if(null==object)return null;
		if(StringUtil.isBlank(propertyName))return null;
		String[] s=propertyName.split("\\.");
		if(null==s)return null;
		for (int i = 0; i < s.length; i++) {
			object=forceGetProperty(object, s[i]);
		}
		return object;
	}
	public static Object forceGetProperty(Object object, String propertyName) {
		Object result=null;
		try {
		    if(object instanceof Map) {
		        result = ((Map)object).get(propertyName);
		    } else {
		        result = getObjValue(object, propertyName, null);
		    }
		} catch (Exception e) {
		}
		return result;
	}
	/**
	 * 直接赋属性值
	 * @param object
	 * @param propertyName
	 * @return
	 */
	public static void setNameProperty(Object object, String propertyName, Object newValue)
		throws NoSuchFieldException {
		
		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (Exception e) {
		}
		field.setAccessible(accessible);
	}
	
	/**
	 * 通过set方法赋值
	 * @param object
	 * @param propertyName
	 * @param newValue
	 */
	public static void forceSetProperty(Object object, String propertyName, Object newValue) {
		if(null==object||StringUtil.isBlank(propertyName))return;
		String[] s=propertyName.split("\\.");
		if(null==s)return;
		for (int i = 0; i < s.length-1; i++) {
			object=forceGetProperty(object, s[i]);
		}
		try {
		    if(object instanceof Map) {
		        ((Map)object).put(propertyName, newValue);
		    } else {
		        setObjValue(object,propertyName,newValue);
		    }
		} catch (Exception e) {
		}
	}

	
	public static Object invokePrivateMethod(Object object, String methodName, Object... params)
			throws NoSuchMethodException {
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}

		Class clazz = object.getClass();
		Method method = null;
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			} catch (NoSuchMethodException e) {
				
			}
		}

		if (method == null)
			throw new NoSuchMethodException("No Such Method:" + clazz.getSimpleName() + methodName);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try {
			result = method.invoke(object, params);
		} catch (Exception e) {
		}
		method.setAccessible(accessible);
		return result;
	}
	/**
	 * 通过反射动态调用方法
	 * @param classpath 类
	 * @param methodname 方法名称
	 * @param types[] 方法参数数组
	 * @return
	 */
	public static Method transferMethoder(String classpath,String methodname,Class types[]) {
		try {
			Class clazz = Class.forName(classpath);
			for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				return superClass.getMethod(methodname,types);
			}
		} catch (Exception e) {
			return null;
		}
		return null; 
	}
	/**
	 * 通过反射动态调用方法
	 * @param classpath 类
	 * @param methodname 方法名称
	 * @param types[] 方法参数数组
	 * @return
	 */
	public static Method transferMethoder(Object obj,String methodname,Class types[]) {
		try {
			Class clazz = obj.getClass();
			for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				return superClass.getMethod(methodname,types);
			}
		} catch (Exception e) {
			return null;
		}
		return null; 
	}
	/**
	 * 获取对象的属性(不包括继承的)
	 * @param obj
	 * @return Field[]
	 */
	public static Field[] getObjProperty(Object obj){
		Class c = obj.getClass();
		Field[] field=c.getDeclaredFields();
		return field;
	}
	
	
	
	/**
	 * 获取对象祖先的属性
	 * @param obj
	 * @return Field[]
	 */
	public static Field[] getObjSupperProperty(Object obj){
		Class c = obj.getClass();
		Class supper=c.getSuperclass();
		List<Field> list = new ArrayList<Field>();
		if(null!=supper){
			for (Class superClass = supper; superClass != Object.class; superClass = superClass.getSuperclass()) {
				Field[] fieldchild=superClass.getDeclaredFields();
				if(null!=fieldchild){
					for (Field field2 : fieldchild) {
						list.add(field2);
					}
				}
			}
		}
		Field[] field=new Field[list.size()];
		field=list.toArray(field);
		return field;
	}
	/**
	 * 获取对象祖先的属性,不包括supperbean
	 * @param obj
	 * @return Field[]
	 */
	public static Field[] getObjOpSupperProperty(Object obj){
		Class c = obj.getClass();
		Class supper=c.getSuperclass();
		List<Field> list = new ArrayList<Field>();
		if(null!=supper){
			for (Class superClass = supper; superClass != Object.class; superClass = superClass.getSuperclass()) {
				Field[] fieldchild=superClass.getDeclaredFields();
				if(null!=fieldchild){
					for (Field field2 : fieldchild) {
						list.add(field2);
					}
				}
			}
		}
		Field[] field=new Field[list.size()];
		field=list.toArray(field);
		return field;
	}
	/**
	 * 获取对象所有的属性(包括继承的)
	 * @param obj
	 * @return Field[]
	 */
	public static Field[] getObjAllProperty(Object obj){
		List<Field> list = new ArrayList<Field>();
		for (Class superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fieldchild=superClass.getDeclaredFields();
			if(null!=fieldchild){
				for (Field field2 : fieldchild) {
					list.add(field2);
				}
			}
		}
		Field[] field=new Field[list.size()];
		field=list.toArray(field);
		return field;
	}
	/**
	 * 获取对象所有的属性(包括继承的),不包括supperbean
	 * @param obj
	 * @return Field[]
	 */
	public static Field[] getObjAllOpProperty(Object obj){
		List<Field> list = new ArrayList<Field>();
		for (Class superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fieldchild=superClass.getDeclaredFields();
			if(null!=fieldchild){
				for (Field field2 : fieldchild) {
					list.add(field2);
				}
			}
		}
		Field[] field=new Field[list.size()];
		field=list.toArray(field);
		return field;
	}
	/**
	 * 获取对应的名称的get方法
	 * @param proName
	 * @return
	 */
	public static String getProNameMethod(String proName){
		String methodName="";
		if(StringUtil.isNotBlank(proName)){
			methodName="get"+StringUtil.upperFirst(proName);
		}
		return methodName;
	}
	/**
	 * 获取对应的名称的set方法
	 * @param proName
	 * @return
	 */
	public static String getProSetNameMethod(String proName){
		String methodName="";
		if(StringUtil.isNotBlank(proName)){
			methodName="set"+StringUtil.upperFirst(proName);
		}
		return methodName;
	}
	/**
	 * 获取对象里对应的属性值(通过get方法)
	 * @param obj
	 * @param name
	 * @param defObj 默认值
	 * @return
	 */
	public static Object getObjValue(Object obj,String name,Object defObj){
		Object valueObj=null;
		String methodName=getProNameMethod(name);
		Method method=transferMethoder(obj, methodName, new Class[0]);
		if(null!=method){
			try {
				valueObj=method.invoke(obj);
				if(null==valueObj){
					valueObj=defObj;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return valueObj;
	}
	/**
	 * 赋值对象里对应的属性值(通过set方法)
	 * @param obj
	 * @param name
	 * @param defObj 值
	 * @return
	 */
	public static void setObjValue(Object obj,String name,Object defObj){
		String methodName=getProSetNameMethod(name);
		try {
			Field field = getDeclaredField(obj, name);
			Class fclass=field.getType();
			Object valueobj=getValueByType(fclass.getName(), defObj);
			Class[] types={fclass};
			Method method=transferMethoder(obj, methodName, types);
			if(null!=method){
				method.invoke(obj,valueobj);
			}
		} catch (Exception e) {
		} 
	}
	/**
	 * @param className
	 * @param defObj
	 * @return
	 */
	public static Object getValueByType(String className,Object defObj){
		Object obj=null;
		if(className.indexOf("String")>=0){
			if(null==defObj){
				obj=null;
			}else{
				obj=defObj+"";
			}
		}else if(className.indexOf("int")>=0){
			if(StringUtil.isBlank(String.valueOf(defObj))){
				defObj="0";
			}
			obj=Long.valueOf(String.valueOf(defObj)).intValue();
		}else if(className.indexOf("Long")>=0){
			if(StringUtil.isBlank(String.valueOf(defObj))){
				defObj="0";
			}
			obj=Long.valueOf(String.valueOf(defObj));
		}else if(className.indexOf("Double")>=0){
			if(StringUtil.isBlank(String.valueOf(defObj))){
				defObj="0";
			}
			obj=Double.valueOf(String.valueOf(defObj));
		}else if(className.indexOf("double")>=0){
			if(StringUtil.isBlank(String.valueOf(defObj))){
				defObj="0";
			}
			obj=Double.valueOf(String.valueOf(defObj));
		}else if(className.indexOf("Date")>=0){
			if(null!=defObj&&StringUtil.isNotBlank(String.valueOf(defObj))){
				obj = TimeUtil.toCalendar(String.valueOf(defObj)).getTime();
				if(obj == null){
					obj = defObj;
				}
			}
		}else if(className.indexOf("Integer")>=0){
			if(StringUtil.isBlank(String.valueOf(defObj))){
				defObj="0";
			}
			obj=Integer.valueOf(String.valueOf(defObj));
		}else if(className.indexOf("boolean")>=0){
			if(StringUtil.isBlank(String.valueOf(defObj))){
				defObj="false";
			}
			if("true".equals(String.valueOf(defObj))){
				obj=true;
			}else{
				obj=false;
			}
		}else if(className.indexOf("Boolean")>=0){
			if(StringUtil.isBlank(String.valueOf(defObj))){
				defObj="false";
			}
			if("true".equals(String.valueOf(defObj))){
				obj=true;
			}else{
				obj=false;
			}
		}
		return obj;
	}
	/**
	 * 赋值对象里对应的属性值(通过set方法) defObj固定式string,要转换
	 * @param obj
	 * @param name
	 * @param defObj 值
	 * @return
	 */
	public static void setObjValue(Object obj,String name,String defObj){
		String methodName=getProSetNameMethod(name);
		try {
			Field field = getDeclaredField(obj, name);
			Class fclass=field.getType();
			Class[] types={fclass};
			Method method=transferMethoder(obj, methodName, types);
			if(null!=method){
				method.invoke(obj,getStringToType(fclass,defObj));
			}
		} catch (Exception e) {
		} 
	}
	public static Object getObject(Object obj,String name,String defObj){
		String methodName=getProSetNameMethod(name);
		try {
			Field field = getDeclaredField(obj, name);
			Class fclass=field.getType();
			Class[] types={fclass};
			return getStringToType(fclass,defObj);
		} catch (Exception e) {
		} 
		return null;
	}
	public static String getObjectHql(Object obj,String name,List<Object> paramlist,Object value){
		String methodName=getProSetNameMethod(name);
		try {
			Field field = getDeclaredField(obj, name);
			Class fclass=field.getType();
			Class[] types={fclass};
			return getStringToHql(fclass,name,paramlist,value);
		} catch (Exception e) {
		} 
		return null;
	}
	/**
	 * 把string 转化成对应的类型
	 * @param typeClass
	 * @param value
	 * @return
	 */
	public static Object getStringToType(Class typeClass,String value){
		Object obj=null;
		if(typeClass.equals(String.class)){
			if(null==value||StringUtil.isBlank(value)){
				obj="";
			}else{
				obj=String.valueOf(value);
			}
		}else if(typeClass.equals(Double.class)){
			if(null==value||StringUtil.isBlank(value)){
				obj=0D;
			}else{
				obj=Double.valueOf(value);
			}
		}else if(typeClass.equals(Integer.class)){
			if(null==value||StringUtil.isBlank(value)){
				obj=0;
			}else{
				obj=Integer.valueOf(value);
			}
		}else if(typeClass.equals(Date.class)){
			if(null==value||StringUtil.isBlank(value)){
				obj=null;
			}else{
				obj = TimeUtil.toCalendar(value).getTime();
			}
		}else if(typeClass.equals(Long.class)){
			if(null==value||StringUtil.isBlank(value)){
				obj=0L;
			}else{
				obj=Long.valueOf(value);
			}
		}else{
			obj=0;
		}
		return obj;
	}
	@SuppressWarnings("deprecation")
	public static String getStringToHql(Class typeClass,String name,List<Object> paramlist,Object value){
		String obj=null;
		if(typeClass.equals(String.class)){
			obj= "'--'";
			paramlist.add(null==value||"".equals(value)?"--":value);
		}else if(typeClass.equals(Double.class)){
			obj="0";
			paramlist.add(null==value||"".equals(value)?0D:value);
		}else if(typeClass.equals(Integer.class)){
			obj="0";
			paramlist.add(null==value||"".equals(value)?0:value);
		}else if(typeClass.equals(Date.class)){
			obj="to_date('1991.01.01','yyyy.mm.dd')";
			paramlist.add(null==value||"".equals(value)?new Date("1991.01.01"):value);
		}else if(typeClass.equals(Long.class)){
			obj="0";
			paramlist.add(null==value||"".equals(value)?0L:value);
		}else{
			obj="0";
			paramlist.add(null==value||"".equals(value)?0:value);
		}
		return obj;
	}
	
}

