package com.ias.common.utils.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

import com.ias.common.utils.date.TimeUtil;;

@SuppressWarnings({"unchecked","rawtypes"})
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils{

    private BeanUtils() {}
    
    /**
     * 将java对象的值复制到Map的同名属性
     */
    public static Map copyPojo2Map(Object dataBean, boolean ignoreEmptyString) throws Exception {
        Map targetMap = new HashMap();
        try {
            PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(dataBean);

            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name))
                    continue;

                Object obj = PropertyUtils.getProperty(dataBean, name);
                if (obj == null) {
                    continue;
                }

                if ((obj.toString().trim()).length() == 0 && ignoreEmptyString) {
                    continue;
                }
                
                if (obj instanceof Date){
                    //obj = CommonMethod.dateFormat((Date)obj);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    obj = sdf.format((Date)obj);
                    
                }
                //obj = convertValue(origDescriptors[i], obj);
                targetMap.put(name, obj);
                // BeanUtils.copyProperty(targetMap, name, obj);

            }// for end
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return targetMap;
    }
    
    /**
     * 只拷贝超类里的数据
     * @param arg0
     * @param arg1
     * @throws Exception
     */
    public static void copySupperPropertys(Object arg0,Object arg1) throws Exception{
        if(null!=arg0&&null!=arg1){
            Object value=null;
            if(arg1 instanceof Map) {
                for (String key : ((Map<String,Object>)arg1).keySet()) {
                    value=ReflectUtils.forceGetProperty(arg1, key);
                    ReflectUtils.forceSetProperty(arg0, key, value);
                }
            }else{
                Field[] field= ReflectUtils.getObjSupperProperty(arg1);
                if(null!=field){
                    for (int i = 0; i < field.length; i++) {
                        value=ReflectUtils.forceGetProperty(arg1, field[i].getName());
                        ReflectUtils.forceSetProperty(arg0, field[i].getName(), value);
                    }
                }
            }
        }else{
            throw new Exception("参数为空");
        }
    }
    
    /**
     * 只拷贝超类里的数据
     * @param arg0
     * @param arg1
     * @throws Exception
     */
    public static void copyAllPropertys(Object arg0,Object arg1) throws Exception{
        if(null!=arg0&&null!=arg1){
            Object value=null;
            if(arg1 instanceof Map) {
                for (String key : ((Map<String,Object>)arg1).keySet()) {
                    value=ReflectUtils.forceGetProperty(arg1, key);
                    ReflectUtils.forceSetProperty(arg0, key, value);
                }
            }else{
                Field[] field= ReflectUtils.getObjAllProperty(arg1);
                if(null!=field){
                    for (int i = 0; i < field.length; i++) {
                        if("goodsSudate".equals(field[i].getName())){
                            System.out.println(field[i].getName());
                            System.out.println("==");
                        }
                        value=ReflectUtils.forceGetProperty(arg1, field[i].getName());
                        ReflectUtils.forceSetProperty(arg0, field[i].getName(), value);
                    }
                }
            }
        }else{
            throw new Exception("参数为空");
        }
    }
    /**
     * 拷贝的数据(不包括继承的)
     * @param arg0
     * @param arg1
     * @throws Exception
     */
    public static void copyImplPropertys(Object arg0,Object arg1) throws Exception{
        if(null!=arg0&&null!=arg1){
            Object value=null;
            if(arg1 instanceof Map) {
                for (String key : ((Map<String,Object>)arg1).keySet()) {
                    value=ReflectUtils.forceGetProperty(arg1, key);
                    ReflectUtils.forceSetProperty(arg0, key, value);
                }
            }else{
                Field[] field= ReflectUtils.getObjProperty(arg1);
                if(null!=field){
                    for (int i = 0; i < field.length; i++) {
                        value=ReflectUtils.forceGetProperty(arg1, field[i].getName());
                        ReflectUtils.forceSetProperty(arg0, field[i].getName(), value);
                    }
                }
            }
        }else{
            throw new Exception("参数为空");
        }
    }
    
    /**
     * 将Map的值复制到java对象的同名属性
     * 
     * @param targetBean
     * @param dataMap
     * @param ignoreEmptyString
     * @throws Exception
     */
    public static void copyMap2Pojo(Object targetBean, Map dataMap,
            boolean ignoreEmptyString) throws Exception {
        try {
            PropertyDescriptor origDescriptors[] = PropertyUtils
                    .getPropertyDescriptors(targetBean);

            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if (name.equals("class")) {
                    continue;
                }

                if (PropertyUtils.isWriteable(targetBean, name)) {
                    Object obj = dataMap.get(name);
                    if (obj == null) {
                        continue;
                    }

                    if ((obj.toString().trim()).length() == 0
                            && ignoreEmptyString) {
                        continue;
                    }
                    obj = convertValue(origDescriptors[i], obj);
                    copyProperty(targetBean, name, obj);
                }
            }// for end
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 将一个对象的属性值取出来放置到Map中。Map的Key为对象属性名称
     * 
     * @param bean
     * @return
     * @throws Exception
     */
    public static Map getProperties(Object bean) throws Exception {
        if (bean == null) {
            return null;
        }

        Map dataMap = new HashMap();
        try {
            PropertyDescriptor origDescriptors[] = PropertyUtils
                    .getPropertyDescriptors(bean);

            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if (name.equals("class")) {
                    continue;
                }

                if (PropertyUtils.isReadable(bean, name)) {
                    Object obj = PropertyUtils.getProperty(bean, name);
                    if (obj == null) {
                        continue;
                    }
                    obj = convertValue(origDescriptors[i], obj);
                    dataMap.put(name, obj);
                }
            }// for end
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return dataMap;
    }
    
    public static void copyPropertiesNotNull(Object dest, Object orig)
        throws IllegalAccessException, InvocationTargetException {
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty origDescriptors[] =
                ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if (propertyUtilsBean.isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    if (value != null) {
                        copyProperty(dest, name, value);
                    }
                }
            }
        } else if (orig instanceof Map) {
            Iterator names = ((Map) orig).keySet().iterator();
            while (names.hasNext()) {
                String name = (String) names.next();
                if (propertyUtilsBean.isWriteable(dest, name)) {
                    Object value = ((Map) orig).get(name);
                    if (value != null) {
                        copyProperty(dest, name, value);
                    }
                }
            }
        } else {
            PropertyDescriptor origDescriptors[] =
                propertyUtilsBean.getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (propertyUtilsBean.isReadable(orig, name) &&
                    propertyUtilsBean.isWriteable(dest, name)) {
                    try {
                        Object value =
                            propertyUtilsBean.getSimpleProperty(orig, name);
                        if (value != null) {
                            copyProperty(dest, name, value);
                        }
                    } catch (NoSuchMethodException e) {
                        ; // Should not happen
                    }
                }
            }
        }
    }
    
    private static Object convertValue(PropertyDescriptor origDescriptor,
            Object obj) throws Exception {
        if (obj == null) {
            return null;
        }

        if (obj.toString().trim().length() == 0) {
            return null;
        }
        if (origDescriptor.getPropertyType() == java.util.Date.class) {
            //同一个时间，第一次从界面层传过来时，obj为String类型;转化后为Date类型
             if (obj instanceof Date) {
                 return obj;
            }else{
                obj = TimeUtil.toCalendar(obj.toString()).getTime();
            }
        }
        return obj;
    }
}
