/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信用户信息
 * @author: yinhong
 * @date: 2016年11月26日 上午11:16:17
 * @version: V1.0
 */
package com.sevencolor.domain.wx.pojo;

import com.sevencolor.domain.comm.BaseEntity;

/**
 * @Description: 微信用户信息
 */
public class WxUserInfo extends BaseEntity {

  private static final long serialVersionUID = 8524050338159777232L;

  private String nickname;

  private String sex;

  private String city;

  private String province;

  private String country;

  private String openid;

  private String headimgurl;

  private String subscribe;

  private String language;

  private String subscribeTime;

  private String remark;

  private String groupid;

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname == null ? null : nickname.trim();
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex == null ? null : sex.trim();
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city == null ? null : city.trim();
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province == null ? null : province.trim();
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country == null ? null : country.trim();
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid == null ? null : openid.trim();
  }

  public String getHeadimgurl() {
    return headimgurl;
  }

  public void setHeadimgurl(String headimgurl) {
    this.headimgurl = headimgurl == null ? null : headimgurl.trim();
  }

  public String getSubscribe() {
    return subscribe;
  }

  public void setSubscribe(String subscribe) {
    this.subscribe = subscribe == null ? null : subscribe.trim();
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language == null ? null : language.trim();
  }

  public String getSubscribeTime() {
    return subscribeTime;
  }

  public void setSubscribeTime(String subscribeTime) {
    this.subscribeTime = subscribeTime == null ? null : subscribeTime.trim();
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark == null ? null : remark.trim();
  }

  public String getGroupid() {
    return groupid;
  }

  public void setGroupid(String groupid) {
    this.groupid = groupid == null ? null : groupid.trim();
  }
}
