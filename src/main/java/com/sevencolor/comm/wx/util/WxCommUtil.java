/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信公共工具类
 * @author: yinhong
 * @date: 2016年12月1日 上午9:01:41
 * @version: V1.0
 */
package com.sevencolor.comm.wx.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sevencolor.comm.constant.EncryptType;
import com.sevencolor.comm.util.CommUtil;
import com.sevencolor.comm.util.HttpReqestUtil;
import com.sevencolor.comm.util.PropertiesUtil;
import com.sevencolor.comm.wx.WxOAuthToken;
import com.sevencolor.domain.wx.pojo.WxUserInfo;

import net.sf.json.JSONObject;

/**
 * @Description: 微信公共工具类
 */
public class WxCommUtil {

	private static Logger logger = LoggerFactory.getLogger(WxCommUtil.class);

	/**
	 * @Description: 微信客户端根据用户openid获取用户基本信息
	 * @return: WxUserInfo
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 */
	public static WxUserInfo getUserInfoForWxClient(String openid)
			throws ClientProtocolException, URISyntaxException, IOException {

		// HashMap<String, String> params = new HashMap<String, String>();
		// params.put("access_token",
		// PropertiesUtil.getProperty("basic_access_token"));
		// params.put("openid", openid);
		// params.put("lang", "zh_CN");
		//
		// String subscribers =
		// HttpReqestUtil.sendGet(PropertiesUtil.getProperty("webchat.cgi.userinfo.url"),
		// params);

		// WxUserInfo wxUserInfo = null;
		// if (StringUtils.isNotBlank(subscribers)) {
		// wxUserInfo = new WxUserInfo();
		// wxUserInfo.setNickname(JSONObject.fromObject(subscribers).getString("nickname"));
		// wxUserInfo.setSex(JSONObject.fromObject(subscribers).getString("sex"));
		// wxUserInfo.setCity(JSONObject.fromObject(subscribers).getString("city"));
		// wxUserInfo.setProvince(JSONObject.fromObject(subscribers).getString("province"));
		// wxUserInfo.setCountry(JSONObject.fromObject(subscribers).getString("country"));
		// wxUserInfo.setOpenid(JSONObject.fromObject(subscribers).getString("openid"));
		// wxUserInfo.setHeadimgurl(JSONObject.fromObject(subscribers).getString("headimgurl"));
		// wxUserInfo.setSubscribe(JSONObject.fromObject(subscribers).getString("subscribe"));
		// wxUserInfo.setLanguage(JSONObject.fromObject(subscribers).getString("language"));
		// wxUserInfo.setSubscribeTime(JSONObject.fromObject(subscribers).getString("subscribe_time"));
		// wxUserInfo.setRemark(JSONObject.fromObject(subscribers).getString("remark"));
		// wxUserInfo.setGroupid(JSONObject.fromObject(subscribers).getString("groupid"));
		// } else {
		// logger.info("get user info failed by user {}.", openid);
		// }

		WxUserInfo wxUserInfo = new WxUserInfo();
		wxUserInfo.setOpenid(openid);

		return wxUserInfo;
	}

	/**
	 * @Description: 获取前端jssdk页面配置需要用到的配置参数
	 * @return: HashMap<String,String>
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("deprecation")
	public static HashMap<String, String> getJsSDKParams(String url)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String nonce_str = create_nonce_str();
		String timestamp = CommUtil.createTimeStamp();
		String jsapi_ticket = PropertiesUtil.getProperty("jsapi_ticket");

		// 注意这里参数名必须全部小写，且必须有序
		String params = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url="
				+ url;

		MessageDigest crypt = MessageDigest.getInstance(EncryptType.SHA_1);
		crypt.reset();
		crypt.update(params.getBytes(HTTP.UTF_8));
		String signature = CommUtil.byteToHex(crypt.digest());
		HashMap<String, String> returnParams = new HashMap<String, String>();
		returnParams.put("appId", PropertiesUtil.getProperty("webchat.appid"));
		returnParams.put("timestamp", timestamp);
		returnParams.put("nonceStr", nonce_str);
		returnParams.put("signature", signature);

		return returnParams;
	}

	/**
	 * @Description: 获取网页授权凭证
	 * @return: WxOAuthToken
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static WxOAuthToken getOauth2Token(String appId, String appSecret, String code)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {

		String oauthTokenUrl = PropertiesUtil.getProperty("webchat.oauth_token.url");
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appId);
		params.put("secret", appSecret);
		params.put("code", code);
		params.put("grant_type", PropertiesUtil.getProperty("webchat.oauth_token.type"));

		String oauthTokenUrlStr = HttpReqestUtil.buildURL(oauthTokenUrl, params);
		// 获取网页授权凭证
		JSONObject jsonObject = HttpReqestUtil.httpsRequest(oauthTokenUrlStr, HttpReqestUtil.HTTP_REQUESTMETHOD_GET,
				null);

		WxOAuthToken wxOauth2Token = null;
		if (null != jsonObject) {
			wxOauth2Token = new WxOAuthToken();
			wxOauth2Token.setAccessToken(jsonObject.getString("access_token"));
			wxOauth2Token.setExpiresIn(jsonObject.getInt("expires_in"));
			wxOauth2Token.setRefreshToken(jsonObject.getString("refresh_token"));
			wxOauth2Token.setOpenId(jsonObject.getString("openid"));
			wxOauth2Token.setScope(jsonObject.getString("scope"));
		}

		return wxOauth2Token;
	}

	/**
	 * @Description: 通过网页授权获取用户信息
	 * @return: WxUser
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static WxUserInfo getUserInfobyOAuth2(String accessSnsToken, String openId)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {

		String userInfoUrl = PropertiesUtil.getProperty("webchat.sns.userinfo.url");
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessSnsToken);
		params.put("openid", openId);
		String userInfoUrlFinal = HttpReqestUtil.buildURL(userInfoUrl, params);

		// 通过网页授权获取用户信息
		JSONObject jsonObject = HttpReqestUtil.httpsRequest(userInfoUrlFinal, HttpReqestUtil.HTTP_REQUESTMETHOD_GET,
				null);

		WxUserInfo wcu = null;
		if (null != jsonObject) {
			wcu = new WxUserInfo();
			wcu.setOpenid(jsonObject.getString("openid"));
			wcu.setNickname(jsonObject.getString("nickname"));
			// 性别（1是男性，2是女性，0是未知）
			wcu.setSex(jsonObject.getString("sex"));
			wcu.setCountry(jsonObject.getString("country"));
			wcu.setProvince(jsonObject.getString("province"));
			wcu.setCity(jsonObject.getString("city"));
			wcu.setHeadimgurl(jsonObject.getString("headimgurl"));
		}

		return wcu;
	}

	/**
	 * @Description: 从内存获取基本Access Tocken，有定时任务自动获取后放入内存中
	 * @return: String
	 */
	public static String getBasicAccessToken() {
		return PropertiesUtil.getProperty("basic_access_token");
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

}
