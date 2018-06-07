/**
 * Copyright (c) 2012 Baozun All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Baozun.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Baozun.
 *
 * BAOZUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. BAOZUN SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */
package com.ias.common.utils.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 获取资源，目前的主要方法都是读取Classpath下的资源
 * @author liuliu
 *
 */
public class ResourceUtil {
	
	/**
	 * 获取资源流
	 * @param resourceName
	 * @return
	 */
	public static InputStream getResourceAsStream(String resourceName) {
        return getResourceAsStream(resourceName,null);
    }

	/**
	 * 获取资源流
	 * @param resourceName
	 * @param callingClass
	 * @return
	 */
	public static InputStream getResourceAsStream(String resourceName, Class<?> callingClass) {
        URL url = getResource(resourceName, callingClass);
        try {
            return (url != null) ? url.openStream() : null;
        } catch (IOException e) {
            return null;
        }
    }
	
	/**
	 * 获取资源URL
	 * @param resourceName
	 * @return
	 */
	public static URL getResource(String resourceName) {
        return getResource(resourceName, null);
    }
		
	/**
	 * 获取资源URL
	 * @param resourceName
	 * @param callingClass
	 * @return
	 */
	public static URL getResource(String resourceName, Class<?> callingClass) {
        URL url = null;
        url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url == null) {
            url = ResourceUtil.class.getClassLoader().getResource(resourceName);
        }
        if (url == null && callingClass != null) {
            url = callingClass.getClassLoader().getResource(resourceName);
        }
        return url;
    }
}
