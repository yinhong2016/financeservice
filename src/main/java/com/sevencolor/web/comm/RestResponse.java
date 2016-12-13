/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 服务端REST接口返回对象
 * @author: yinhong
 * @date: 2016年11月24日 下午3:29:10
 * @version: V1.0
 */
package com.sevencolor.web.comm;

/**
 * @Description: 服务端REST接口返回对象
 * @param <T>
 */
public class RestResponse<T> {

  /**
   * @Description: 状态：成功，失败
   */
  private String status;

  /**
   * @Description: 状态相关信息
   */
  private String message;

  /**
   * @Description: 返回具体内容
   */
  private T content;

  public RestResponse() {
    this.message = "";
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getContent() {
    return content;
  }

  public void setContent(T content) {
    this.content = content;
  }
}
