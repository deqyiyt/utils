package com.ias.common.utils.collection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.ias.common.utils.string.StringUtil;


/**
 * 功能：常用的排序工具方法
 * 创建者: hujiuzhou@hotoa.com
 * 修改者                   修改时间
 * 
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class SortUtil {
	public static void main(String[] args) {
		int[] arr = {221,4,19,312,23,442,121};
		int[] newArr = bubbleSort(arr);
		System.out.println(newArr[newArr.length-1]);
	}
    /**
     * 冒泡排序
     *
     * @param info 要求排序数组
     * @return 已经排序好的数组,按照从小到大的顺序排s序
     */
    public static int[] bubbleSort(int[] info) {
        int i;
        int j;
        int tempVariables;
        for (i = 0; i < info.length; i++) {
            for (j = info.length - 1; j > i; j--) {
                if (info[j - 1] >info[j]) {
                    tempVariables = info[j - 1];
                    info[j - 1] = info[j];
                    info[j] = tempVariables;
                }
            }
        }
        return info;
    }

    /**
     * 
     * @Title: sort
     * @Description: 通用集合排序
     * @param cla 排序的实体bean对象
     * @param list 需要排序的集合
     * @param method 排序的字段
     * @param sort 需要排序的方式
     * @author hujiuzhou
     * @return void 返回类型
     * @throws
     */
	public static void sort(final Class cla,List list,final String orderKey,final String orderType){
		Collections.sort(list,new Comparator(){
			public int compare(Object a, Object b) {
				int ret = 0;
				try {
					String method = "get"+StringUtil.upperFirst(orderKey);
					Method m2 = cla.getMethod(method);
					Object o1 = m2.invoke( b);
					Object o2 = m2.invoke(a);
					if(null != o1 && null != o2){
						if(orderType != null && "desc".equals(orderType)){
							ret = o1.toString().compareTo(o2.toString());
						} else{
							ret = o2.toString().compareTo(o1.toString());
						}
					}else if(null != o1 && null == o2){
						ret = 1;
					}else if(null == o1 && null != o2){
						ret = -1;
					}
				} catch (NoSuchMethodException ne) {
					System.out.println(ne);
				}catch (IllegalAccessException ie) {
					System.out.println(ie);
				}catch (InvocationTargetException it) {
					System.out.println(it);
				}
				return ret;
			}
			
		});
	}
    
    public static Comparator sort(Class cla,String orderKey,final String orderType){
		String getMethodName = "get"+StringUtil.upperFirst(orderKey);
		try {
			final Method getMethod = cla.getMethod(getMethodName,
					new Class[] {});
			Comparator comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					int val = 0;
					try {
						Object v1 = getMethod.invoke(o1, new Object[] {});
						Object v2 = getMethod.invoke(o2, new Object[] {});
						if (v1 instanceof Integer) {
							if (Integer.parseInt(v1.toString()) < Integer.parseInt(v2.toString()))
								val = 1;
						}else if(v1 instanceof Float){
							if (Float.parseFloat(v1.toString()) < Float.parseFloat(v2.toString()))
								val = 1;
						}else if(v1 instanceof Double){
							if (Double.parseDouble(v1.toString()) < Double.parseDouble(v2.toString()))
								val = 1;
						}else if (v1 instanceof String) {
							v1.toString().compareTo(v2.toString());
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					if(StringUtil.equals(orderType, "desc")){
						return val==0?1:0;
					}else
						return val;
				}
			};
			return comp;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /**
     * 
    *对所有集合进行排序.<br>
    *工程名:cctccati<br>
    *包名:com.soft.sb.util.order<br>
    *方法名:sortObjectAsc方法.<br>
    *
    *@author:hujiuzhou@hotoa.com
    *@since :1.0:2009-8-12
    *@param list 需要排序的集合
    *@return 排序后的集合
     */
    public static List<Object> sortObjectAsc(List<Object> list) {
        List<Object> sortResultList = new ArrayList<Object>();
        // 1、用链表做排序——提高性能
        LinkedList<Object> tmpList = new LinkedList<Object>();
        for(int i = 0; i < list.size(); i++) {
            Object object = (Object) list.get(i);
            tmpList.add(object);
        }
        sortBypaperTypeSort(tmpList);
        if(tmpList != null) {
            for(int i = 0; i < tmpList.size(); i++) {
                sortResultList.add(tmpList.get(i));
            }
        }
        return sortResultList;
    }
    

    /**
     * 
    * 按照指定的排序字段进行冒泡升序排序,形成顺序.<br>
    *工程名:cctccati<br>
    *包名:com.soft.sb.util.order<br>
    *方法名:sortBypaperTypeSort方法.<br>
    *
    *@author:hujiuzhou@hotoa.com
    *@since :1.0:2009-8-12
    *@param ll 需要排序的集合
     */
    private static void sortBypaperTypeSort(LinkedList<Object> ll) {
        Object tmpObj = new Object();
        // 1、 按照指定的排序字段进行冒泡升序排序,形成顺序
        int count = 0;
        for(int j = 0; j < (ll.size() - 1); j++) {
            // 2、判断本次循环是否有次序变动，如果没有，则表示已完成排序，跳出循环
            boolean isContinue = false;
            for(int i = 0; i < (ll.size() - 1 - count); i++) {
                Object objectBefore = ll.get(i);
                Object objectAfter = ll.get(i + 1);
                int sortBefore = 0;
                int sortAfter = 0;
                if(sortBefore > sortAfter) {
                    tmpObj = objectBefore;
                    ll.set(i, objectAfter);
                    ll.set(i + 1, tmpObj);
                    isContinue = true;
                }
            }
            count++;
            if(isContinue == false) {
                break;
            }
        }
    }
    
}
