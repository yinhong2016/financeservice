/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信事件派发器
 * @author: yinhong
 * @date: 2016年11月29日 下午1:44:33
 * @version: V1.0
 */
package com.sevencolor.web.wx.comm;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sevencolor.comm.wx.constant.WxMsgType;
import com.sevencolor.comm.wx.util.WxCommUtil;
import com.sevencolor.domain.wx.pojo.WxUserInfo;
import com.sevencolor.service.wx.WxUserServiceI;
import com.sevencolor.web.wx.msg.WxArticle;
import com.sevencolor.web.wx.msg.response.WxRespNewsMsg;
import com.sevencolor.web.wx.msg.response.WxRespTextMsg;

/**
 * @Description: 微信事件派发器，根据不同的事件类型进行处理
 */
@Component
public class WxEventDispatcher {

	private static final Logger logger = LoggerFactory.getLogger(WxEventDispatcher.class);

	@Autowired
	private WxUserServiceI wxUserService;

	// 静态方法调用容器Bean的使用方法
	private static WxEventDispatcher webchatEventDispatcher;

	/**
	 * @Description: 静态方法调用容器Bean的使用方法
	 * @return: void
	 */
	@PostConstruct
	public void init() {
		webchatEventDispatcher = this;
		webchatEventDispatcher.wxUserService = this.wxUserService;
	}

	public static String processEvent(Map<String, String> map)
			throws ClientProtocolException, URISyntaxException, IOException {

		String openid = map.get("FromUserName");
		String mpid = map.get("ToUserName");
		String eventType = map.get("Event");
		String resultStr = "";

		switch (eventType) {

		// 关注事件
		case WxMsgType.EVENT_TYPE_SUBSCRIBE: {

			// 对图文消息
			WxRespNewsMsg newmsg = new WxRespNewsMsg();
			newmsg.setToUserName(openid);
			newmsg.setFromUserName(mpid);
			newmsg.setCreateTime(new Date().getTime());
			newmsg.setMsgType(WxMsgType.RESP_MESSAGE_TYPE_NEWS);

			// 保存关注用户基本信息到数据库
			WxUserInfo wxUserInfo = WxCommUtil.getUserInfoForWxClient(openid);
			webchatEventDispatcher.wxUserService.addUser(wxUserInfo);

			WxArticle article = new WxArticle();
			article.setDescription("欢迎关注七彩世界股票：");
			article.setPicUrl(wxUserInfo.getHeadimgurl());
			// article.setTitle("尊敬的：" + wxUserInfo.getNickname() + ",你好！");
			// article.setUrl("http://www.baidu.com");
			List<WxArticle> list = new ArrayList<WxArticle>();
			list.add(article);
			newmsg.setArticleCount(list.size());
			newmsg.setArticles(list);

			resultStr = WxMsgUtil.newsMessageToXml(newmsg);
			break;
		}

		// 取消关注事件
		case WxMsgType.EVENT_TYPE_UNSUBSCRIBE: {
			// 更新用户状态为非活跃
			webchatEventDispatcher.wxUserService.invanlidUser(openid);
			break;
		}

		// 扫描二维码推送事件
		case WxMsgType.EVENT_TYPE_SCAN_WAITMSG: {
			WxRespTextMsg txtmsg = new WxRespTextMsg();
			txtmsg.setToUserName(openid);
			txtmsg.setFromUserName(mpid);
			txtmsg.setCreateTime(new Date().getTime());
			txtmsg.setMsgType(WxMsgType.RESP_MESSAGE_TYPE_TEXT);
			txtmsg.setContent(map.get("ScanResult"));
			resultStr = WxMsgUtil.textMessageToXml(txtmsg);
			break;
		}

		// 位置上报
		case WxMsgType.EVENT_TYPE_LOCATION: {
			break;
		}

		// 自定义菜单点击事件
		case WxMsgType.EVENT_TYPE_CLICK: {
			break;
		}

		// 自定义View事件
		case WxMsgType.EVENT_TYPE_VIEW: {
			break;
		}

		default: {
			logger.info("no webchat message hanlder.");
			break;
		}
		}

		return resultStr;
	}

}
