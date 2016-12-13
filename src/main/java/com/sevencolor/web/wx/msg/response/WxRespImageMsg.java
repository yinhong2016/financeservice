/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信图片信息回复消息体
 * @author: yinhong
 * @date: 2016年11月29日 下午2:16:53
 * @version: V1.0
 */
package com.sevencolor.web.wx.msg.response;

import com.sevencolor.web.wx.msg.WxImage;

/**
 * @Description: 微信图片信息回复消息体
 */
public class WxRespImageMsg extends WxRespBaseMsg {

  private WxImage Image;

  public WxImage getImage() {
    return Image;
  }

  public void setImage(WxImage image) {
    Image = image;
  }
}
