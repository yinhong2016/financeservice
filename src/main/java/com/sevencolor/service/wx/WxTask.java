/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信定时任务体
 * @author: yinhong
 * @date: 2016年11月29日 下午3:34:19
 * @version: V1.0
 */
package com.sevencolor.service.wx;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sevencolor.comm.util.HttpReqestUtil;
import com.sevencolor.comm.util.PropertiesUtil;

import net.sf.json.JSONObject;

/**
 * @Description: 微信定时任务体
 */
public class WxTask {

  private static final Logger logger = LoggerFactory.getLogger(WxTask.class);

  /**
   * @Description: 获取微信基本Access_Token和JsApi_Ticket,并放入内存
   * @return: void
   */
  public void getBasicAccessToken_JsApiTicket() {
    getBasicAccessToken();
    getJSApiTicket();
  }

  /**
   * @Description: 获取微信基本Access_Token,并放入内存中
   * @return: void
   */
  private void getBasicAccessToken() {

    Map<String, String> params = new HashMap<String, String>();
    params.put("grant_type", PropertiesUtil.getProperty("webchat.token.type"));
    params.put("appid", PropertiesUtil.getProperty("webchat.appid"));
    params.put("secret", PropertiesUtil.getProperty("webchat.appsecret"));

    // 获取到token并赋值保存
    String accessTokenReturn = null;
    try {
      accessTokenReturn =
          HttpReqestUtil.sendGet(PropertiesUtil.getProperty("webchat.token.url"), params);
      if (accessTokenReturn != null) {
        String access_token = JSONObject.fromObject(accessTokenReturn).getString("access_token");
        PropertiesUtil.appProperties.put("basic_access_token", access_token);
        logger.info("basic_access_token: {}", access_token);
      }
    } catch (IOException | URISyntaxException e) {
      logger.error("get basic access token failed.", e);
    }
  }

  /**
   * @Description: 获取JsApi_Ticket,并放入内存中
   * @return: void
   */
  private void getJSApiTicket() {

    Map<String, String> params = new HashMap<String, String>();
    params.put("access_token", PropertiesUtil.getProperty("basic_access_token"));
    params.put("type", PropertiesUtil.getProperty("webchat.jsapi_ticket.type"));

    String jsticket = null;
    try {
      jsticket =
          HttpReqestUtil.sendGet(PropertiesUtil.getProperty("webchat.jsapi_ticket.url"), params);
      String jsapi_ticket = JSONObject.fromObject(jsticket).getString("ticket");
      PropertiesUtil.appProperties.put("jsapi_ticket", jsapi_ticket);
      logger.info("jsapi_ticket: {}", jsapi_ticket);
    } catch (IOException | URISyntaxException e) {
      logger.error("get jsapi ticket failed.", e);
    }

  }

}
