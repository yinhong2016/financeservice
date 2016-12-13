/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信用户业务接口
 * @author: yinhong
 * @date: 2016年12月1日 下午5:42:49
 * @version: V1.0
 */
package com.sevencolor.service.wx;

import com.sevencolor.domain.wx.pojo.WxUserInfo;

/**
 * @Description: 微信用户业务接口
 */
public interface WxUserServiceI {

  void addUser(WxUserInfo user);

  void invanlidUser(String openid);

  WxUserInfo getUserById(Long userId);

  WxUserInfo getUserByOpenId(String openid);
}
