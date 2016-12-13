/**  
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 配置文件常量类
 * @author: yinhong  
 * @date: 2016年11月24日 下午1:24:19
 * @version: V1.0  
 */
package com.sevencolor.comm.util;

import java.util.Properties;

/**
 * @Description: 获取配置文件中定义的常量
 * @date: 2016年11月24日 下午1:24:19
 */
public class PropertiesUtil {

	public static Properties appProperties;

	/**
	 * @Description: 根据KEY获取配置文件中的Value
	 * @return: String
	 */
	public static String getProperty(String key) {
		return (String) appProperties.get(key);
	}

}