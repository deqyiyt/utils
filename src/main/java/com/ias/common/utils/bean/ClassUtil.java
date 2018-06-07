package com.ias.common.utils.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.util.Assert;

import com.ias.common.utils.date.TimeUtil;
import com.ias.common.utils.number.NumberUtils;
import com.ias.common.utils.string.StringUtil;

/**
 *********************************************** 
 * @Copyright (c) by ysc All right reserved.
 * @Create Date: 2014-2-24 下午2:15:35
 * @Create Author: hujz
 * @File Name: ObjectUtil
 * @Function: 一些通用的与对象相关的操作
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
@SuppressWarnings({"rawtypes"})
public class ClassUtil extends org.apache.commons.lang3.ClassUtils {
    /**
     * 判别两个对象是否相同。
     * @param o1 被判断的第1个对象
     * @param o2 被判断的第2个对象
     * @return =true 相等
     *          =false 不相等
     */
    public static boolean equals(Object o1, Object o2) {
        return ((o1 == o2) || ((o1 != null) && (o1.equals(o2))));
    }

    /**
     * 获取不含有包名称的类名称
     * @param className 带包名的类名称
     * @return 类名
     */
    public static String getBaseClassName(String className) {
        return StringUtil.getLastSuffix(className, ".");
    }

    /**
     * 获取调用的包名称 
     * @param className 类名称
     * @return 包名称
     */
    public static String getPackageName(String className) {
        return StringUtil.getLastPrefix(className, ".");
    }

    /**
     * 获取方法名称
     * @param whole 带类名的方法名称
     * @return 方法名
     */
    public static String getMethodName(String whole) {
        return StringUtil.getLastSuffix(whole, ".");
    }

    /**
     * 获取对象的hash码。
     * @param o 需要获取hash码的对象
     * @return =0 对象为空，否则为hash码
     */
    public static int hashCode(Object o) {
        return ((null == o) ? 0 : o.hashCode());
    }

    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////
    
    /**
     * 通过构造器取得实例
     * @param className 类的全限定名
     * @param intArgsClass 构造函数的参数类型
     * @param intArgs 构造函数的参数值
     * 
     * @return Object 
     */
    @SuppressWarnings("unchecked")
	public static Object getObjectByConstructor(String className,Class[] intArgsClass,Object[] intArgs){
    	Object returnObj= null;
    	try {
			Class classType = Class.forName(className);
			Constructor constructor = classType.getDeclaredConstructor(intArgsClass); //找到指定的构造方法
			constructor.setAccessible(true);//设置安全检查，访问私有构造函数必须
			returnObj = constructor.newInstance(intArgs);
		} catch (NoSuchMethodException ex) {
		    ex.printStackTrace();
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
    	return returnObj;
    }

    /**
     *获取对象的属性
     *
     * @param object 对象
     * @param sProperty 对象的Bean属性
     * @return  对象的属性方法
     */
    public static Object getProperty(Object object, String sProperty) {
        if (null == object || null == sProperty) {
            return null;
        }
        try {
            Method method = object.getClass().getMethod("get" + StringUtil.upperFirst(sProperty));
            return method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 访问类成员变量
     * @param Object 访问对象
     * @param filedName 指定成员变量名
     * @return Object 取得的成员变量的值
     * */
    public static Object getFileValue(Object obj, String fieldName) {
    	Object result = null;
		Field field = getField(obj, fieldName);
		if (field != null) {
			// 取消 Java 语言访问检查
			field.setAccessible(true);
			try {
				result = field.get(obj);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return result;
    }
    
    /**
     * 调用类的方法，包括私有
     * @param Object 访问对象
     * @param methodName 指定成员变量名
     * @param type 方法参数类型
     * @param value 方法参数指
     * @return Object 方法的返回结果对象
     * */
    @SuppressWarnings("unchecked")
	public static Object useMethod(Object object, String methodName,
                                   Class[] type, Object... value) {
        Class classType = object.getClass();
        Method method = null;
        Object fildValue = null;
        try {
            method = classType.getDeclaredMethod(methodName, type);
            method.setAccessible(true);//设置安全检查，访问私有成员方法必须
            fildValue = method.invoke(object, value);
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fildValue;
    }

    /**
     * @Project cw
     * @Package com.soft.sb.util.type
     * @Method getPropertyValue方法.<br>
     * @Description 根据属性名称获得对象中的值
     * @author 胡久洲
     * @date 2013-6-8 上午11:25:42
     * @param object 实体对象
     * @param sProperty 属性名称
     * @return
     */
    public static Object getPropertyValue(Object object, String sProperty){
        try{
            BeanWrapper bw = new BeanWrapperImpl(object);
            return bw.getPropertyValue(sProperty);
        } catch(NotReadablePropertyException e){
            e.printStackTrace();
            System.out.println("没有get方法");
            return null;
        }
    }
    
    /**
	 * 利用反射获取指定对象里面的指定属性
	 * 
	 * @param obj
	 *            目标对象
	 * @param fieldName
	 *            目标属性
	 * @return 目标字段
	 */
	public static Field getField(Object obj, String fieldName) {
		Field field = null;
		for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException e) {
				// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null
			}
		}
		return field;
	}
	
	/**
	 * 利用反射获取指定对象里面的指定属性
	 * 
	 * @param obj
	 *            目标对象
	 * @param fieldName
	 *            目标属性
	 * @return 目标字段
	 */
	public static Field getField(Class clazz, String fieldName) {
		Field field = null;
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException e) {
				// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null
			}
		}
		return field;
	}
    
    /**
     * 将对象转换成字符串输出
     * @param o 对象
     * @return 字符串
     */
    public static String toString(Object o) {
        if (null == o) {
            return "";
        }
        if (o instanceof Object[]) {
            return toArrayString((Object[]) o);
        } else if (o instanceof int[]) {
            return NumberUtils.toString((int[]) o);
        } else if (o instanceof long[]) {
            return NumberUtils.toString((long[]) o);
        } else if (o instanceof char[]) {
            return StringUtil.toString((char[]) o);
        } else if (o instanceof Calendar) {
            return TimeUtil.toString((Calendar) o);
        } else {
            return o.toString();
        }
    }

    /**
     * 将数组转换为字符串。
     * @param array 数组
     * @return  字符串
     */
    public static String toArrayString(Object[] array) {
        if (null == array) {
            return "count=0:[]";
        }

        StringBuffer s = new StringBuffer("count=").append(array.length).append(":[ ");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                s.append(", ");
            }
            Object obj = array[i];
            if (null == obj) {
                continue;
            } else if (obj instanceof Object[]) {
                s.append(ClassUtil.toString((Object[]) obj));
            } else {
                s.append(obj.toString());
            }
        }
        s.append(" ]");
        return s.toString();
    }
    
    /**
     * 
     *通过传入的参数，将相应的值通过反射设置到对应对象中.<br>
     *工程名:cctcpsp<br>
     *包名:com.soft.sb.util.xml<br>
     *方法名:invokeMethod方法.<br>
     * 
     *@author:hujiuzhou@hotoa.com
     *@since :1.0:2009-9-4
     *@param name:名称
     *@param object：节点对象
     *@param configValue:配置值
     *@throws Throwable:转化错误
     */
    public static  void invokeSetGetMethod(Object object, String name, String configValue) {
    	try {
    		String methodName = "set" + Character.toUpperCase(name.charAt(0)) + name
                    .substring(1);
            String getMethodName = "get" + Character.toUpperCase(name.charAt(0)) + name
                    .substring(1);
            Method m = object.getClass().getMethod(getMethodName);
            Method method = object.getClass().getMethod(methodName,
                    m.getReturnType());
            Object valueObj = typeConvert(m.getReturnType().getSimpleName(),
                    configValue);
            method.invoke(object, valueObj);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    	
        
    }
    
    /**
     * 修改成员变量的值
     * @param Object 修改对象
     * @param filedName 指定成员变量名
     * @param filedValue 修改的值
     *
     * @return 
     */
    public static void modifyFileValue(Object object, String filedName, Object filedValue) {
        Class classType = object.getClass();
        Field fild = null;
        try {
            fild = classType.getDeclaredField(filedName);
            fild.setAccessible(true);//设置安全检查，访问私有成员变量必须
            fild.set(object, filedValue);
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *用于将参数的String类型的值转换为对象中需要的值.<br>
     *工程名:cctcpsp<br>
     *包名:com.soft.sb.util.xml<br>
     *方法名:typeConvert方法.<br>
     * 
     *@author:hujiuzhou@hotoa.com
     *@since :1.0:2009-8-3
     *@param className
     *@param value
     *@return Object 返回根据className转换后的value值
     */
    public static Object typeConvert(String className, String value) {
        if(className.equals("String")) {
            return value;
        }else if(className.equals("Integer") || className.equals("int")) {
            return Integer.valueOf(value);
        }else if(className.equals("long") || className.equals("Long")) {
            return Long.valueOf(value);
        }else if(className.equals("boolean") || className.equals("Boolean")) {
            return Boolean.valueOf(value);
        }else if(className.equals("Date")) {
            return new Date(NumberUtils.toLong(value, 0));
        }else if(className.equals("float") || className.equals("Float")) {
            return Float.valueOf(value);
        }else if(className.equals("double") || className.equals("Double")) {
            return Double.valueOf(value);
        }else {
            return null;
        }
    }
    
    /**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException
     *             如果分析类属性失败
     * @throws IllegalAccessException
     *             如果实例化 JavaBean 失败
     * @throws InstantiationException
     *             如果实例化 JavaBean 失败
     * @throws InvocationTargetException
     *             如果调用属性的 setter 方法失败
     */
    public static <T> T convertMap(Class<T> type, Map map) {
    	T obj = null;
    	try{
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        obj = type.newInstance(); // 创建 JavaBean 对象
        
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
    	}catch (Exception e) {
		}
        return obj;
    }
    
    /**
	 * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
	 */
	public static Object getFieldValue(final Object object,
			final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("never happend exception!", e);
		}
		return result;
	}

	/**
	 * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 */
	public static void setFieldValue(final Object object,
			final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("never happend exception!", e);
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 */
	protected static Field getDeclaredField(final Object object,
			final String fieldName) {
		Assert.notNull(object, "对象不能为空");
		return getDeclaredField(object.getClass(), fieldName);
	}

	/**
	 * 循环向上转型,获取类的DeclaredField.
	 */
	protected static Field getDeclaredField(final Class clazz,
			final String fieldName) {
		Assert.notNull(clazz, "clazz不能为空");
		Assert.hasText(fieldName ,"fieldName不能为空");
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 强制转换fileld可访问.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	public static Object getSimpleProperty(Object bean, String propName)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		return bean.getClass().getMethod(getReadMethod(propName)).invoke(bean);
	}

	private static String getReadMethod(String name) {
		return "get" + name.substring(0, 1).toUpperCase(Locale.ENGLISH)
				+ name.substring(1);
	}
    
    /**
     * 获得泛型注册的类
     * @Project SC
     * @Package com.ias.common.utils.type
     * @Method getGenericType方法.<br>
     * @author 胡久洲
     * @date 2014-4-11 上午11:08:54
     * @param clazz
     * @param index
     * @return
     */
    public static Class<?> getGenericType(Class<?> clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) params[index];
    }
    
    /**
     * 获得classpath路径
     * @Project SC
     * @Package com.ias.common.utils.type
     * @Method getClassPath方法.<br>
     * @author hjz
     * @date 2015-1-28 下午8:48:07
     * @return
     */
    public static String getClassPath() {
    	String classpath = ClassUtil.class.getResource("/").getPath();
    	return StringUtil.subStrStartDiffStr(classpath, "/");
    }
}

