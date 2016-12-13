/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信视屏信息回复消息体
 * @author: yinhong
 * @date: 2016年11月29日 下午2:18:25
 * @version: V1.0
 */
package com.sevencolor.web.wx.msg.response;

import com.sevencolor.web.wx.msg.WxVideo;

/**
 * @Description: 微信视屏信息回复消息体
 */
public class WxRespVideoMsg extends WxRespBaseMsg {

  private WxVideo Video;

  public WxVideo getVideo() {
    return Video;
  }

  public void setVideo(WxVideo video) {
    Video = video;
  }
}
