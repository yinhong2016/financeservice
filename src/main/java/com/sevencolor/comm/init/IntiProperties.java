/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 读取自定义配置文件
 * @author: yinhong
 * @date: 2016年11月24日 下午1:24:03
 * @version: V1.0
 */
package com.sevencolor.comm.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sevencolor.comm.util.PropertiesUtil;

/**
 * @Description: 将自定义配置文件内容读取到系统中
 */
public class IntiProperties {

  private static final Logger logger = LoggerFactory.getLogger(IntiProperties.class);

  /**
   * @Description: 读入自定义配置文件
   * @return: void
   */
  public synchronized static void init() {

    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    Properties props = new Properties();
    if (PropertiesUtil.appProperties == null) {
      PropertiesUtil.appProperties = new Properties();
    }

    InputStream in = null;
    try {
      in = cl.getResourceAsStream("config.properties");
      props.load(in);
      for (Object key : props.keySet()) {
        PropertiesUtil.appProperties.put(key, props.get(key));
      }
    } catch (IOException e) {
      logger.error("load config.properties failed.", e);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          logger.error("close config.properties input stream failed.", e);
        }
      }
    }

    return;
  }

}
