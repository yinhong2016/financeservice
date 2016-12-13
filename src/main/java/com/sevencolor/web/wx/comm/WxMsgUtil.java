/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 微信XML消息体处理工具
 * @author: yinhong
 * @date: 2016年11月29日 下午1:15:16
 * @version: V1.0
 */
package com.sevencolor.web.wx.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sevencolor.web.wx.msg.WxArticle;
import com.sevencolor.web.wx.msg.response.WxRespImageMsg;
import com.sevencolor.web.wx.msg.response.WxRespMusicMsg;
import com.sevencolor.web.wx.msg.response.WxRespNewsMsg;
import com.sevencolor.web.wx.msg.response.WxRespTextMsg;
import com.sevencolor.web.wx.msg.response.WxRespVideoMsg;
import com.sevencolor.web.wx.msg.response.WxRespVoiceMsg;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @Description: 微信XML消息体处理工具
 */
public class WxMsgUtil {

  /**
   * @Description: 解析微信发来的请求（XML）,目前解析两层XML
   * @return: Map<String,String>
   * @throws IOException
   * @throws DocumentException
   */
  @SuppressWarnings("unchecked")
  public static Map<String, String> parseXml(HttpServletRequest request)
      throws IOException, DocumentException {

    Map<String, String> map = new HashMap<String, String>();
    InputStream inputStream = null;

    try {
      inputStream = request.getInputStream();

      if (inputStream != null) {
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        if (document != null) {
          Element root = document.getRootElement();
          if (root != null) {
            List<Element> elementList = root.elements();
            if (elementList != null) {
              for (Element e : elementList) {
                map.put(e.getName(), e.getText());

                List<Element> innerElementList = e.elements();
                if (innerElementList != null) {
                  for (Element innerElement : innerElementList) {
                    map.put(innerElement.getName(), innerElement.getText());
                  }
                }

              }
            }
          }
        }
      }
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }

    return map;
  }

  /**
   * @Description: 文本消息对象转换成 xml
   * @return: String
   */
  public static String textMessageToXml(WxRespTextMsg textMessage) {
    xstream.alias("xml", textMessage.getClass());
    return xstream.toXML(textMessage);
  }

  /**
   * @Description: 图文消息对象转换成 xml
   * @return: String
   */
  public static String newsMessageToXml(WxRespNewsMsg newsMessage) {
    xstream.alias("xml", newsMessage.getClass());
    xstream.alias("item", new WxArticle().getClass());
    return xstream.toXML(newsMessage);
  }

  /**
   * @Description: 图片消息对象转换成 xml
   * @return: String
   */
  public static String imageMessageToXml(WxRespImageMsg imageMessage) {
    xstream.alias("xml", imageMessage.getClass());
    return xstream.toXML(imageMessage);
  }

  /**
   * @Description: 语音消息对象转换成 xml
   * @return: String
   */
  public static String voiceMessageToXml(WxRespVoiceMsg voiceMessage) {
    xstream.alias("xml", voiceMessage.getClass());
    return xstream.toXML(voiceMessage);
  }

  /**
   * @Description: 视频消息对象转换成 xml
   * @return: String
   */
  public static String videoMessageToXml(WxRespVideoMsg videoMessage) {
    xstream.alias("xml", videoMessage.getClass());
    return xstream.toXML(videoMessage);
  }

  /**
   * @Description: 音乐消息对象转换成 xml
   * @return: String
   */
  public static String musicMessageToXml(WxRespMusicMsg musicMessage) {
    xstream.alias("xml", musicMessage.getClass());
    return xstream.toXML(musicMessage);
  }

  /**
   * 对象到 xml 的处理
   */
  private static XStream xstream = new XStream(new XppDriver() {
    public HierarchicalStreamWriter createWriter(Writer out) {
      return new PrettyPrintWriter(out) {
        // 对所有 xml 节点的转换都增加 CDATA 标记
        boolean cdata = true;

        @SuppressWarnings("rawtypes")
        public void startNode(String name, Class clazz) {
          super.startNode(name, clazz);
        }

        protected void writeText(QuickWriter writer, String text) {
          if (cdata) {
            writer.write("<![CDATA[");
            writer.write(text);
            writer.write("]]>");
          } else {
            writer.write(text);
          }
        }
      };
    }
  });

  /**
   * 判断是否是QQ表情
   * 
   * @param content
   * @return
   */
  public static boolean isQqFace(String content) {
    boolean result = false;
    // 判断QQ表情的正则表达式
    String qqfaceRegex =
        "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
    Pattern p = Pattern.compile(qqfaceRegex);
    Matcher m = p.matcher(content);
    if (m.matches()) {
      result = true;
    }
    return result;
  }

  /**
   * emoji表情转换(hex -> utf-16)
   * 
   * @param hexEmoji
   * @return
   */
  public static String emoji(int hexEmoji) {
    return String.valueOf(Character.toChars(hexEmoji));
  }

}
