/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 加密工具类
 * @author: yinhong
 * @date: 2016年11月26日 上午11:16:17
 * @version: V1.0
 */
package com.sevencolor.comm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sevencolor.comm.constant.EncryptType;

/**
 * @Description: 加密工具类
 */
public final class EncryptUtil {

  /**
   * @Description: 对字符串进行MD5加密
   * @return: String
   */
  public static String EncryptByMD5(String str) throws NoSuchAlgorithmException {
    return Encrypt(str, EncryptType.MD5);
  }


  /**
   * @Description: 对字符串进行SHA1加密
   * @return: String
   */
  public static String EncryptBySHA1(String str) throws NoSuchAlgorithmException {
    return Encrypt(str, EncryptType.SHA_1);
  }

  /**
   * @Description: 对字符串进行SHA256加密
   * @return: String
   */
  public static String EncryptBySHA256(String str) throws NoSuchAlgorithmException {
    return Encrypt(str, EncryptType.SHA_256);
  }

  private static String Encrypt(String strSrc, String encName) throws NoSuchAlgorithmException {
    byte[] bt = strSrc.getBytes();
    if (encName == null || encName.equals("")) {
      encName = EncryptType.MD5;
    }
    MessageDigest md = MessageDigest.getInstance(encName);
    md.update(bt);
    return bytes2Hex(md.digest());
  }

  private static String bytes2Hex(byte[] bts) {
    String des = "";
    String tmp = null;
    for (int i = 0; i < bts.length; i++) {
      tmp = (Integer.toHexString(bts[i] & 0xFF));
      if (tmp.length() == 1) {
        des += "0";
      }
      des += tmp;
    }
    return des;
  }

}
