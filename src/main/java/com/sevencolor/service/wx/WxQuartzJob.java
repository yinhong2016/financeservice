/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 服务端定义的JOB
 * @author: yinhong
 * @date: 2016年11月29日 下午3:50:13
 * @version: V1.0
 */
package com.sevencolor.service.wx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sevencolor.comm.wx.util.WxTask;

/**
 * @Description: 用于定时执行某些任务
 */
public class WxQuartzJob {

  private static final Logger logger = LoggerFactory.getLogger(WxQuartzJob.class);

  /**
   * @Description: 任务定时执行获取微信基本access_token和JsApi Ticket
   * @return: void
   */
  public void getAccessToken_JsApiTicket() {
    try {
      WxTask timer = new WxTask();
      timer.getBasicAccessToken_JsApiTicket();
    } catch (Exception e) {
      logger.error("get basic access token and jsapi ticket failed.", e);
    }
  }
}
