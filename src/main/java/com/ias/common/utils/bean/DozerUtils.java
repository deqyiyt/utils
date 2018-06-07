package com.ias.common.utils.bean;

import org.dozer.DozerBeanMapper;

public class DozerUtils {

    private static final DozerBeanMapper mapper = new DozerBeanMapper();
    
    /**
     * 拷贝对象属性，不抛出异常。深层次拷贝
     * @param dest 目标对象
     * @param src 元对象
     */
    public static void copyProperties(Object dest, Object src) {
        if ((null == src) || (null == dest)) {
            return;
        }
        try {
            mapper.map(src, dest);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
