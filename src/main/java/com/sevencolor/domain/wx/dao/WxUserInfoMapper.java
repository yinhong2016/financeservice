/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信用户数据库操作类，不需要写实现类（MapperScannerConfigurer创建代理实现）
 * @author: yinhong
 * @date: 2016年11月26日 上午11:16:17
 * @version: V1.0
 */
package com.sevencolor.domain.wx.dao;

import org.springframework.stereotype.Repository;

import com.sevencolor.domain.wx.pojo.WxUserInfo;

/**
 * @Description: 微信用户数据库操作类
 */
@Repository
public interface WxUserInfoMapper {

  int deleteByPrimaryKey(Long id);

  int insert(WxUserInfo record);

  int insertSelective(WxUserInfo record);

  WxUserInfo selectByPrimaryKey(Long id);

  WxUserInfo selectByOpenid(String openid);

  int updateByPrimaryKeySelective(WxUserInfo record);

  int updateByPrimaryKey(WxUserInfo record);

}
