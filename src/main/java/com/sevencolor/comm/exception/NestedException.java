/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 服务端抛出的抽象异常
 * @author: yinhong
 * @date: 2016年11月26日 下午1:23:33
 * @version: V1.0
 */
package com.sevencolor.comm.exception;

/**
 * @Description: 服务端抛出的抽象异常
 */
public abstract class NestedException extends Exception {

  private static final long serialVersionUID = 9052291235071709561L;

  public NestedException(Throwable cause) {
    super(cause);
  }

  public NestedException(final String message) {
    super(message);
  }

  public NestedException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public String getMessage() {
    return ExceptionUtil.buildMessage(super.getMessage(), getCause());
  }

  public Throwable getRootCause() {
    Throwable rootCause = null;
    Throwable cause = getCause();
    while (cause != null && cause != rootCause) {
      rootCause = cause;
      cause = cause.getCause();
    }
    return rootCause;
  }

  public Throwable getMostSpecificCause() {
    Throwable rootCause = getRootCause();
    return (rootCause != null ? rootCause : this);
  }

  @SuppressWarnings("rawtypes")
  public boolean contains(Class exType) {
    if (exType == null) {
      return false;
    }
    if (exType.isInstance(this)) {
      return true;
    }
    Throwable cause = getCause();
    if (cause == this) {
      return false;
    }
    if (cause instanceof NestedException) {
      return ((NestedException) cause).contains(exType);
    } else {
      while (cause != null) {
        if (exType.isInstance(cause)) {
          return true;
        }
        if (cause.getCause() == cause) {
          break;
        }
        cause = cause.getCause();
      }
      return false;
    }
  }
}
