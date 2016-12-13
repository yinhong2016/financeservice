/**  
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 获取信息配置文件里面内容
 * @author: yinhong  
 * @date: 2016年11月25日 下午5:30:46
 * @version: V1.0  
 */
package com.sevencolor.comm.util;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * @Description: 信息配置文件内容获取工具
 */
public class MessageUtil {

	/**
	 * @Description: 静态变量不能使用autowired注入
	 */
	private static MessageSource messageSource;

	public static void setMessageSource(MessageSource messageSource) {
		MessageUtil.messageSource = messageSource;
	}

	/**
	 * @Description: 根据KEY获取对应的内容
	 * @return: String
	 */
	public static String getMessage(String keyCode, Object[] args, String defaultMessage, Locale locale) {
		String msg = messageSource.getMessage(keyCode, args, defaultMessage, locale);
		return msg != null ? msg.trim() : defaultMessage;
	}

	/**
	 * @Description: 根据KEY获取对应的内容
	 * @return: String
	 */
	public static String getMessage(String keyCode, Object[] args, String defaultMessage) {
		String msg = messageSource.getMessage(keyCode, args, defaultMessage, Locale.getDefault());
		return msg != null ? msg.trim() : defaultMessage;
	}

	/**
	 * @Description: 根据KEY获取对应的内容
	 * @return: String
	 */
	public static String getMessage(String keyCode, Object[] args) {
		String msg = messageSource.getMessage(keyCode, args, "", Locale.getDefault());
		return msg != null ? msg.trim() : "";
	}

	/**
	 * @Description: 根据KEY获取对应的内容
	 * @return: String
	 */
	public static String getMessage(String keyCode) {
		String msg = messageSource.getMessage(keyCode, null, "", Locale.getDefault());
		return msg != null ? msg.trim() : "";
	}
}
