/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 通用公共工具
 * @author: yinhong
 * @date: 2016年12月3日 下午5:00:13
 * @version: V1.0
 */
package com.sevencolor.comm.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Formatter;

import org.apache.http.protocol.HTTP;

/**
 * @Description: 通用公共工具
 */
public class CommUtil {

  /**
   * @Description: 字节数组转化为16进制字符串
   * @return: String
   */
  public static String byteArrayToHexStr(byte[] byteArray) {
    String strDigest = "";
    for (int i = 0; i < byteArray.length; i++) {
      strDigest += byteToHexStr(byteArray[i]);
    }
    return strDigest;
  }

  /**
   * @Description: 字节转化为16进制字符串
   * @return: String
   */
  public static String byteToHexStr(byte mByte) {
    char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    char[] tempArr = new char[2];
    tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
    tempArr[1] = Digit[mByte & 0X0F];
    String s = new String(tempArr);
    return s;
  }

  /**
   * @Description: 创建当前时间时间戳
   * @return: String
   */
  public static String createTimeStamp() {
    return Long.toString(System.currentTimeMillis() / 1000);
  }

  /**
   * @Description: hash数组补齐为16进制后以字符串形式输出
   * @return: String
   */
  public static String byteToHex(final byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    String result = formatter.toString();
    formatter.close();
    return result;
  }

  /**
   * @Description: 对URL进行UTF-8编码
   * @return: String
   * @throws UnsupportedEncodingException
   */
  @SuppressWarnings("deprecation")
  public static String urlEncodeUTF8(String source) throws UnsupportedEncodingException {
    return URLEncoder.encode(source, HTTP.UTF_8);
  }
}
