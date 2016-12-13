/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 异常工具，组装异常信息
 * @author: yinhong
 * @date: 2016年11月26日 上午11:16:17
 * @version: V1.0
 */
package com.sevencolor.comm.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;

/**
 * @Description: 异常工具
 */
public class ExceptionUtil {

  private static final String LINE_SEPARATOR = System.getProperty("line.separator");

  public static String buildMessage(String message, Throwable cause) {
    StringBuilder sb = new StringBuilder();
    if (cause != null && !StringUtils.isBlank(message)) {
      sb.append(message).append("; ");
      sb.append("nested exception is ").append(cause);
    } else if (cause != null && StringUtils.isBlank(message)) {
      sb.append("nested exception is ").append(cause);
    } else if (cause == null && !StringUtils.isBlank(message)) {
      sb.append(message);
    }
    return sb.toString();
  }

  public static Throwable getRootCause(Throwable ex) {
    Throwable rootCause = null;
    Throwable cause = ex.getCause();
    while (cause != null && cause != rootCause) {
      rootCause = cause;
      cause = cause.getCause();
    }
    return rootCause;
  }

  public static Throwable getMostSpecificCause(Throwable ex) {
    Throwable rootCause = getRootCause(ex);
    return (rootCause != null ? rootCause : ex);
  }

  public static String getExceptionStackTrace(Throwable ex) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    pw.close();
    return sw.toString();
  }

  public static String getMostSpecificCauseMessageInfo(Throwable ex) {
    Throwable rootCause = getMostSpecificCause(ex);
    StackTraceElement[] elements = rootCause.getStackTrace();

    String rootException = rootCause.getClass().getName();
    String rootExceptionMsg = rootCause.getLocalizedMessage();

    String rootCauseSpotClass = elements[0].getClassName();
    String rootCauseSpotMethod = elements[0].getMethodName();
    int rootCauseSpotLine = elements[0].getLineNumber();

    StringBuilder sbdr = new StringBuilder(LINE_SEPARATOR);
    sbdr.append("[Root Exception]: ").append(rootException).append(LINE_SEPARATOR)
        .append("[Root Exception Message]: ").append(rootExceptionMsg).append(LINE_SEPARATOR)
        .append("[Root Exception throwed on]: ").append(rootCauseSpotClass).append(".")
        .append(rootCauseSpotMethod).append("  Line:").append(rootCauseSpotLine);

    return sbdr.toString();
  }

}
