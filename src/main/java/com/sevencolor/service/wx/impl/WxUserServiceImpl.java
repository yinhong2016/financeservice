/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信用户业务类
 * @author: yinhong
 * @date: 2016年12月1日 下午5:47:04
 * @version: V1.0
 */
package com.sevencolor.service.wx.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevencolor.domain.comm.EntityStateConst;
import com.sevencolor.domain.wx.dao.WxUserInfoMapper;
import com.sevencolor.domain.wx.pojo.WxUserInfo;
import com.sevencolor.service.wx.WxUserServiceI;

/**
 * @Description: 微信用户业务类
 */
@Service("wxUserService")
public class WxUserServiceImpl implements WxUserServiceI {

  @Autowired
  private WxUserInfoMapper wxUserInfoMapper;


  /*
   * @Description: 将用户信息保存到数据，如果已经存在则更新
   * 
   * @param user
   * 
   * @see com.sevencolor.service.wx.WxUserServiceI#addUser(com.sevencolor.domain.wx.pojo.WxUserInfo)
   */
  @Override
  public void addUser(WxUserInfo user) {

    // 查询用户是否已经存在
    WxUserInfo selectedUser = wxUserInfoMapper.selectByOpenid(user.getOpenid());

    // 新添加的用户设置用户为活跃
    user.setState(EntityStateConst.VALID);

    // 如果用户已经存在，则更新数据；如果不存在，则保存数据
    Date date = new Date();
    if (selectedUser == null) {
      // 设置创建时间和更新时间
      user.setCreatedtime(date);
      user.setUpdatedtime(date);
      wxUserInfoMapper.insert(user);
    } else {
      // 设置更新时间
      user.setUpdatedtime(date);
      user.setId(selectedUser.getId());
      wxUserInfoMapper.updateByPrimaryKey(user);
    }

  }


  @Override
  public WxUserInfo getUserById(Long userId) {
    return wxUserInfoMapper.selectByPrimaryKey(userId);
  }

  public WxUserInfo getUserByOpenId(String openid) {
    return wxUserInfoMapper.selectByOpenid(openid);
  }

  /*
   * @Description: TODO
   * 
   * @param openid
   * 
   * @see com.sevencolor.service.wx.WxUserServiceI#invanlidUser(java.lang.String)
   */
  @Override
  public void invanlidUser(String openid) {

    // 获取用户信息
    WxUserInfo selectedUser = wxUserInfoMapper.selectByOpenid(openid);

    // 设置状态和更新时间
    selectedUser.setState(EntityStateConst.INVALID);
    selectedUser.setUpdatedtime(new Date());

    // 数据库更新
    wxUserInfoMapper.updateByPrimaryKeySelective(selectedUser);

  }

}
