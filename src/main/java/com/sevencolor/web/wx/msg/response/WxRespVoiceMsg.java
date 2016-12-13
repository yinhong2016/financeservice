/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信语音信息回复消息体
 * @author: yinhong
 * @date: 2016年11月29日 下午2:19:07
 * @version: V1.0
 */
package com.sevencolor.web.wx.msg.response;

import com.sevencolor.web.wx.msg.WxVoice;

/**
 * @Description: 微信语音信息回复消息体
 */
public class WxRespVoiceMsg extends WxRespBaseMsg {

  private WxVoice Voice;

  public WxVoice getVoice() {
    return Voice;
  }

  public void setVoice(WxVoice voice) {
    Voice = voice;
  }

}
