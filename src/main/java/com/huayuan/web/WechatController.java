package com.huayuan.web;

import com.huayuan.domain.Member;
import com.huayuan.domain.wechat.AcceptMessage;
import com.huayuan.domain.wechat.UserInfo;
import com.huayuan.domain.wechat.ValidationUrl;
import com.huayuan.service.MemberService;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Li JiWang on 14-3-24.
 */

@Controller
public class WechatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);
    private final String APP_ID = "wxd0ad97bfca9f7b8d";
    private final String APP_SECRET = "a4a50ae52b356be5a370e9173a6f19b9";
    private final String TOKEN = "huayuan158";
    private final String TO_USER_NAME = "ToUserName";
    private final String FROM_USER_NAME = "FromUserName";
    private final String MSG_TYPE = "MsgType";
    private final String CONTENT = "Content";
    private final String EVENT = "Event";
    private final String EVENT_KEY = "EventKey";
    @Resource
    private MemberService memberService;

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    @RequestMapping(value = "/huayuan158", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String wechatStart(HttpServletRequest request) throws IOException {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        ValidationUrl validationUrl = new ValidationUrl();
        validationUrl.setSignature(signature);
        validationUrl.setTimestamp(timestamp);
        validationUrl.setNonce(nonce);
        validationUrl.setEchostr(echostr);

        if (!StringUtils.isEmpty(validationUrl.getEchostr())) {
            if (checkSignature(validationUrl.getSignature(), validationUrl.getTimestamp(), validationUrl.getNonce())) {
                return validationUrl.getEchostr();
            } else {
                return "";
            }
        } else {
            return messageHandle(request);
        }
    }

    public boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{TOKEN, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            byte[] digest = MessageDigest.getInstance("SHA-1").digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    public AcceptMessage parserMessageXml(HttpServletRequest request) {
        AcceptMessage acceptMessage = new AcceptMessage();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            List<Element> elementList = root.elements();
            for (Element element : elementList){
                String key = element.getName();
                String value = element.getText();
                if (key.equals(TO_USER_NAME)) {
                    acceptMessage.setToUserName(value);
                } else if (key.equals(FROM_USER_NAME)) {
                    acceptMessage.setFromUserName(value);
                } else if (key.equals(MSG_TYPE)) {
                    acceptMessage.setMsgType(value);
                } else if (key.equals(CONTENT)) {
                    acceptMessage.setContent(value);
                } else if (key.equals(EVENT)) {
                    acceptMessage.setEvent(value);
                } else if (key.equals(EVENT_KEY)) {
                    acceptMessage.setEventKey(value);
                }
            }
            inputStream.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        inputStream = null;
        return acceptMessage;
    }

    public String messageHandle(HttpServletRequest request) {
        AcceptMessage acceptMessage = parserMessageXml(request);
        if (acceptMessage.getMsgType().equals("event")) {
            String event = acceptMessage.getEvent();
            if (event.equals("subscribe")) {
                acceptMessage.setContent("您好，欢迎关注 : " + acceptMessage.getFromUserName());
                RestTemplate restTemplate = new RestTemplate();

                //get access_token
                String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={appSecret}";
                String accessTokenJson = restTemplate.getForObject(accessTokenUrl, String.class, APP_ID, APP_SECRET);
                String accessToken = accessTokenJson.substring(accessTokenJson.indexOf("access_token") + 15, accessTokenJson.indexOf("expires_in") - 3);

                //获得微信用户基本信息
                String userListUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={accessToken}&openid={apenId}&lang=zh_CN";
                String userListJson = restTemplate.getForObject(accessTokenUrl, String.class, accessToken, acceptMessage.getFromUserName());

                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    UserInfo userInfo = objectMapper.readValue(userListJson, UserInfo.class);
                    if(userInfo.getErrcode() < 0){
                        LOGGER.error("error: user list not is data!");
                        return  "error:-1";
                    }
                    Member member = userInfoToMember(userInfo);
                    memberService.register(member);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (event.equals("unsubscribe")) {
                acceptMessage.setContent("谢谢您的关注!");
            }
        }
        return acceptMessage.getContent();
    }

    public Member userInfoToMember(UserInfo userInfo){
        Member member = new Member();
        member.setWcCity(userInfo.getCity());
        member.setWcNo(userInfo.getOpenid());
        member.setWcUserName(userInfo.getNickname());
        member.setWcProvince(userInfo.getProvince());
        member.setWcSignature("");
        return  member;
    }
}
