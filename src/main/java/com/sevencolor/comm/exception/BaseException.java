/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 服务端抛出的异常基类
 * @author: yinhong
 * @date: 2016年11月26日 上午11:16:17
 * @version: V1.0
 */
package com.sevencolor.comm.exception;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.sevencolor.comm.util.MessageUtil;

/**
 * @Description: 服务端异常基类
 */
public class BaseException extends NestedException {

  private static final long serialVersionUID = 8458544317507845657L;

  private String errorCode;
  private Object[] messageArgs;

  public BaseException(String errorCode) {
    super(getErrorMessage(errorCode, null));
  }

  public BaseException(Throwable cause) {
    super(cause);
  }

  public BaseException(String errorCode, Throwable cause) {
    super(getErrorMessage(errorCode, null), cause);
    this.errorCode = errorCode;
  }

  public BaseException(String errorCode, Throwable cause, Object[] messageArgs) {
    super(getErrorMessage(errorCode, messageArgs), cause);
    this.errorCode = errorCode;
    this.messageArgs = messageArgs;
  }

  @Override
  public String getMessage() {
    StringBuilder sb = new StringBuilder();
    if (!StringUtils.isBlank(errorCode)) {
      sb.append("Code: ").append(errorCode).append("\rMessage: ").append(super.getMessage());
      return sb.toString();
    }
    return super.getMessage();
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public Object[] getMessageArgs() {
    return messageArgs;
  }

  public void setMessageArgs(Object[] messageArgs) {
    this.messageArgs = messageArgs;
  }

  /**
   * @Description: 根据errorCode获取errorMessage
   * @return: String
   */
  private static String getErrorMessage(String errorCode, Object[] messageArgs) {
    String errorMessage = "";
    if (!StringUtils.isBlank(errorCode) && !ArrayUtils.isEmpty(messageArgs)) {
      errorMessage = MessageUtil.getMessage(errorCode, messageArgs);
    } else if (!StringUtils.isBlank(errorCode) && ArrayUtils.isEmpty(messageArgs)) {
      errorMessage = MessageUtil.getMessage(errorCode);
    }
    return errorMessage;
  }

}
