/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信消息体类型
 * @author: yinhong
 * @date: 2016年11月29日 下午5:07:43
 * @version: V1.0
 */
package com.sevencolor.comm.wx.constant;

/**
 * @Description: 微信消息体类型
 */
public interface WxMsgType {

  /**
   * 返回消息类型：文本
   */
  public static final String RESP_MESSAGE_TYPE_TEXT = "text";

  /**
   * 返回消息类型：音乐
   */
  public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

  /**
   * 返回消息类型：图文
   */
  public static final String RESP_MESSAGE_TYPE_NEWS = "news";

  /**
   * 返回消息类型：图片
   */
  public static final String RESP_MESSAGE_TYPE_Image = "image";

  /**
   * 返回消息类型：语音
   */
  public static final String RESP_MESSAGE_TYPE_Voice = "voice";

  /**
   * 返回消息类型：视频
   */
  public static final String RESP_MESSAGE_TYPE_Video = "video";

  /**
   * 请求消息类型：文本
   */
  public static final String REQ_MESSAGE_TYPE_TEXT = "text";

  /**
   * 请求消息类型：图片
   */
  public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

  /**
   * 请求消息类型：链接
   */
  public static final String REQ_MESSAGE_TYPE_LINK = "link";

  /**
   * 请求消息类型：地理位置
   */
  public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

  /**
   * 请求消息类型：音频
   */
  public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

  /**
   * 请求消息类型：视频
   */
  public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

  /**
   * 请求消息类型：推送
   */
  public static final String REQ_MESSAGE_TYPE_EVENT = "event";

  /**
   * 事件类型：订阅
   */
  public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

  /**
   * 事件类型：取消订阅
   */
  public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

  /**
   * 事件类型：CLICK(自定义菜单点击事件)
   */
  public static final String EVENT_TYPE_CLICK = "CLICK";

  /**
   * 事件类型：VIEW(自定义菜单 URl 视图)
   */
  public static final String EVENT_TYPE_VIEW = "VIEW";

  /**
   * 事件类型：LOCATION(上报地理位置事件)
   */
  public static final String EVENT_TYPE_LOCATION = "LOCATION";

  /**
   * 事件类型：LOCATION(扫码推送事件)
   */
  public static final String EVENT_TYPE_SCAN_WAITMSG = "scancode_waitmsg";
}
