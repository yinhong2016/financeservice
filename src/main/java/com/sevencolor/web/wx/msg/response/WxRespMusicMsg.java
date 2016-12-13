/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信音乐信息回复消息体
 * @author: yinhong
 * @date: 2016年11月29日 下午2:17:44
 * @version: V1.0
 */
package com.sevencolor.web.wx.msg.response;

import com.sevencolor.web.wx.msg.WxMusic;

/**
 * @Description: 微信音乐信息回复消息体
 */
public class WxRespMusicMsg extends WxRespBaseMsg {

  // 音乐
  private WxMusic Music;

  public WxMusic getMusic() {
    return Music;
  }

  public void setMusic(WxMusic music) {
    Music = music;
  }
}
