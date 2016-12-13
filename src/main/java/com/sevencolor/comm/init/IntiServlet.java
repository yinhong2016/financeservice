/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 应用创建的Servlet，跟随系统自动启动
 * @author: yinhong
 * @date: 2016年11月24日 下午3:29:10
 * @version: V1.0
 */
package com.sevencolor.comm.init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @Description: 用于系统启动时载入自己定义的配置信息
 */
public class IntiServlet extends HttpServlet {

  private static final long serialVersionUID = 3002317714173215727L;

  /*
   * @Description: 初始化
   * 
   * @param config
   * 
   * @throws ServletException
   * 
   * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    IntiProperties.init();
  }

}
