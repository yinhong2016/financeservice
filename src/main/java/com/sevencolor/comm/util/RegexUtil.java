/**  
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 正则表达式验证工具
 * @author: yinhong  
 * @date: 2016年11月24日 下午1:24:19
 * @version: V1.0  
 */
package com.sevencolor.comm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;

/**
 * @Description: 正则表达式验证工具
 */
public class RegexUtil {

	private final static Pattern SPECIAL_PATTERN = Pattern
			.compile("[`~!@#$%^&*()+=|{}':;',//[//]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");

	/**
	 * 
	 * @param value
	 *            需要被验证的字符串
	 * @return 字符串如果为小数格式，则返回true； 否则返回false
	 */
	public static boolean isFloat(String value) {
		return StringUtils.isNotBlank(value) && value.matches("^[+|-]?(\\d+\\.)?\\d+$");
	}

	/**
	 * 
	 * @param value
	 *            需要被验证的字符串
	 * @return 字符串如果为整数格式，则返回true； 否则返回false
	 */
	public static boolean isInt(String value) {
		return StringUtils.isNotBlank(value) && value.matches("^[+|-]?\\d+$");
	}

	/**
	 * 
	 * <Description>验证字符串value是否为邮箱格式</Description>
	 *
	 * @param value
	 *            需要被验证的字符串
	 * @return 字符串如果为邮箱格式，则返回true； 否则返回false
	 */
	public static boolean isEmail(String value) {
		return StringUtils.isNotBlank(value)
				&& value.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
	}

	/**
	 * 验证手机号格式
	 * 
	 * @param phone
	 *            需要被验证的字符串
	 * @return 字符串如果为手机号格式，则返回true；否则返回false
	 */
	public static boolean isPhone(String value) {
		return StringUtils.isNotBlank(value) && value.matches("^(13|14|15|18)[0-9]{9}$");
	}

	/**
	 * 判断字符串中是否包含中文
	 * 
	 * @param value
	 *            需要被验证的字符串
	 * @return 参数value中如果包含中文字符，则返回true；否则返回false
	 */
	public static boolean isContainChinese(String value) {
		return StringUtils.isNotBlank(value) && value.matches(".*[\u4e00-\u9fa5].*");
	}

	/**
	 * 判断字符串是否完全由中文构成
	 * 
	 * @param value
	 *            需要被验证的字符串
	 * @return 如果参数value中的字符全部为中文，则返回true；否则返回false
	 */
	public static boolean isChinese(String value) {
		return StringUtils.isNotBlank(value) && value.matches("^[\u4e00-\u9fa5]+$");
	}

	/**
	 * 
	 * @param dateStr
	 *            需要被验证的字符串
	 * @return 如果参数dateStr为yyyy-MM-dd的日期格式，则返回true；否则返回false
	 */
	public static boolean isDate(String dateStr) {
		return StringUtils.isNotBlank(dateStr) && dateStr.matches("\\d{4}-\\d{2}-\\d{2}");
	}

	/**
	 * 
	 * @param dateTimeStr
	 *            需要被验证的字符串
	 * @return 如果参数dateTimeStr为yyyy-MM-dd_HH:mm:ss或yyyy-MM-dd
	 *         HH:mm:ss的格式，则返回true；否则返回false
	 */
	public static boolean isDateTime(String dateTimeStr) {
		return StringUtils.isNotBlank(dateTimeStr)
				&& dateTimeStr.matches("\\d{4}-\\d{2}-\\d{2}[_|\\s]\\d{1,2}:\\d{1,2}:\\d{1,2}");
	}

	/**
	 * 过滤所有特殊字符
	 * 
	 * @param str
	 *            需要被过滤的字符串
	 * @return 将参数str中包含的SPECIAL_PATTERN指定的特殊字符，全部替换为空字符""，然后返回
	 * @throws PatternSyntaxException
	 */
	public static String specialFilter(String str) throws PatternSyntaxException {
		Matcher m = SPECIAL_PATTERN.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 
	 * @param content
	 *            包含数字的字符串
	 * @return 截取字符串中从左至右出现的第一串连续的数字字符串，并将其返回
	 */
	public static String getNumbers(String content) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

}
