/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信消息处理转发器
 * @author: yinhong
 * @date: 2016年11月29日 下午1:42:03
 * @version: V1.0
 */
package com.sevencolor.web.wx.comm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sevencolor.comm.wx.constant.WxMsgType;
import com.sevencolor.web.wx.msg.WxArticle;
import com.sevencolor.web.wx.msg.response.WxRespNewsMsg;

/**
 * @Description: 微信消息处理转发器，根据不同类型消息进行不同处理
 */
public class WxMsgDispatcher {

	private static final Logger logger = LoggerFactory.getLogger(WxMsgDispatcher.class);

	public static String processMessage(Map<String, String> map) {

		String openid = map.get("FromUserName");
		String mpid = map.get("ToUserName");
		String msgType = map.get("MsgType");
		String content = map.get("Content");
		String result = "";

		switch (msgType) {

		// 文本消息
		case WxMsgType.REQ_MESSAGE_TYPE_TEXT: {

			List<WxArticle> list = new ArrayList<WxArticle>();

			WxRespNewsMsg newmsg = new WxRespNewsMsg();
			newmsg.setToUserName(openid);
			newmsg.setFromUserName(mpid);
			newmsg.setCreateTime(new Date().getTime());
			newmsg.setMsgType(WxMsgType.RESP_MESSAGE_TYPE_NEWS);

			WxArticle article = new WxArticle();
			article.setPicUrl("http://www.7colorworld.com/financeservice/pic.jpg");

			if (content.equalsIgnoreCase("d") || content.equalsIgnoreCase("D")) {
				article.setDescription("日收益排名靠前组合购买股票 ");
				article.setTitle("日收益排名靠前组合购买股票");
				article.setUrl("http://www.7colorworld.com/financeservice/topcubedaily.jsp");
			} else if (content.equalsIgnoreCase("m") || content.equalsIgnoreCase("M")) {
				article.setDescription("月收益排名靠前组合购买股票 ");
				article.setTitle("月收益排名靠前组合购买股票");
				article.setUrl("http://www.7colorworld.com/financeservice/topcubemonthly.jsp");
			} else if (content.equalsIgnoreCase("y") || content.equalsIgnoreCase("Y")) {
				article.setDescription("年收益排名靠前组合购买股票 ");
				article.setTitle("年收益排名靠前组合购买股票");
				article.setUrl("http://www.7colorworld.com/financeservice/topcubeyearly.jsp");
			} else if (content.equalsIgnoreCase("dy") || content.equalsIgnoreCase("DY")
					|| content.equalsIgnoreCase("Dy") || content.equalsIgnoreCase("dY")
					|| content.equalsIgnoreCase("yd") || content.equalsIgnoreCase("YD")
					|| content.equalsIgnoreCase("yD") || content.equalsIgnoreCase("Yd")) {
				article.setDescription("年与月收益排名靠前组合都购买的股票 ");
				article.setTitle("年与月收益排名靠前组合都购买的股票");
				article.setUrl("http://www.7colorworld.com/financeservice/topcubesummery.jsp");
			}

			list.add(article);
			newmsg.setArticleCount(list.size());
			newmsg.setArticles(list);
			result = WxMsgUtil.newsMessageToXml(newmsg);

			break;
		}

		// 图文消息
		case WxMsgType.REQ_MESSAGE_TYPE_IMAGE: {
			// WxRespNewsMsg newmsg = new WxRespNewsMsg();
			// newmsg.setToUserName(openid);
			// newmsg.setFromUserName(mpid);
			// newmsg.setCreateTime(new Date().getTime());
			// newmsg.setMsgType(WxMsgType.RESP_MESSAGE_TYPE_NEWS);
			//
			// WxArticle article = new WxArticle();
			// article.setDescription("这是图文消息 ");
			// article.setPicUrl(
			// "http://p4.gexing.com/G1/M00/8D/97/rBACFFOmEOyRqIiyAAAhbwMbRv4671_200x200_3.jpg?recache=20131108");
			// article.setTitle("小美女");
			// article.setUrl("http://image.baidu.com");
			// List<WxArticle> list = new ArrayList<WxArticle>();
			// list.add(article);
			//
			// newmsg.setArticleCount(list.size());
			// newmsg.setArticles(list);
			// result = WxMsgUtil.newsMessageToXml(newmsg);
			break;
		}

		// 链接消息
		case WxMsgType.REQ_MESSAGE_TYPE_LINK: {
			break;
		}

		// 位置消息
		case WxMsgType.REQ_MESSAGE_TYPE_LOCATION: {
			break;
		}

		// 视屏消息
		case WxMsgType.REQ_MESSAGE_TYPE_VIDEO: {
			break;
		}

		// 语音消息
		case WxMsgType.REQ_MESSAGE_TYPE_VOICE: {
			break;
		}

		// 默认
		default: {
			logger.info("no webchat event hanlder.");
			break;
		}

		}

		return result;
	}
}
