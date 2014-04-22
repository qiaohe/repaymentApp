package com.huayuan.integration.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huayuan.domain.member.Member;
import com.huayuan.domain.member.SexEnum;
import com.huayuan.integration.wechat.domain.EventMessage;
import com.huayuan.integration.wechat.domain.User;
import com.huayuan.service.MemberService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Johnson on 4/14/14.
 */
@Controller(value = "messageController")
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    private static final String ACCESS_TOKEN_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={appSecret}";
    private static final String GET_USER_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={accessToken}&openid={openid}&lang=zh_CN";
    private static final String RESPONSE_URL_PATTERN = "{0}/index.html?memberId={1}&status={2}";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Inject
    private MemberService memberService;
    @Inject
    private Unmarshaller unmarshaller;
    @Inject
    private Marshaller marshaller;
    @Inject
    private RestTemplate restTemplate;
    @Value("${weChat.integration.baseUrl}")
    private String baseUrl;


    @RequestMapping(value = "/huayuan158", method = RequestMethod.GET)
    @ResponseBody
    public String init(@RequestParam String signature, @RequestParam String timestamp,
                       @RequestParam String nonce, @RequestParam String echostr) throws IOException {

        if (checkSignaturePass(signature, timestamp, nonce))
            return echostr;
        return null;
    }

    @RequestMapping(value = "/huayuan158", method = RequestMethod.POST)
    public void handleMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        EventMessage eventMessage = (EventMessage) unmarshaller.unmarshal(new StreamSource(request.getInputStream()));
        if (eventMessage.isSubscribeEvent()) {
            addMember(getUser(eventMessage.getFromUserName()));
        }
        final String rm = getReplyMessage(eventMessage);
        response.getWriter().println(rm);
    }

    private String getResponseMessageUrl(Long memberId, Integer status) {
        return MessageFormat.format(RESPONSE_URL_PATTERN, baseUrl, memberId, status);
    }

    private String getContent(EventMessage message) {
        final Long memberId = memberService.findMemberBy(message.getFromUserName()).getId();
        if (message.isSubscribeEvent()) {
            return MessageFormat.format("欢迎关注“化缘“。\n" +
                    "\n" +
                    "卡中羞涩？来化缘吧！足不出户就申请到借款，偿还信用卡账，最高比还信用卡最低还款额省50%。\n" +
                    "\n" +
                    "心动了吗？<a href=\"http://180.168.35.37/repaymentApp/index.html?memberId={0}\">点此先做个信用评估</a>", memberId);
        } else if (message.isCustomMenuEvent()) {
            if (message.getEventKey().equalsIgnoreCase("M_001_CREDIT_ESTIMATION")) {
                return MessageFormat.format("<a href=\"http://180.168.35.37/repaymentApp/index.html?memberId={0}\">点此先做个信用评估</a>", memberId);
            } else if (message.getEventKey().equalsIgnoreCase("M_002_APPLY_LOAN")) {
                return MessageFormat.format("<a href=\"http://180.168.35.37/repaymentApp/index.html#loan?memberId={0}\">点此申请借款</a>", memberId);
            } else if (message.getEventKey().equalsIgnoreCase("M_003_REPAYMENT"))
                return MessageFormat.format("<a href=\"http://180.168.35.37/repaymentApp/index.html#repay?memberId={0}\">点此归还还款</a>", memberId);
        }
        return "";
    }

    private String getReplyMessage(EventMessage eventMessage) {
        EventMessage rm = new EventMessage();
        rm.setFromUserName(eventMessage.getToUserName());
        rm.setToUserName(eventMessage.getFromUserName());
        rm.setMsgType("text");
        rm.setCreateTime(new Date().getTime());
        rm.setFuncFlag("0");
        rm.setContent(getContent(eventMessage));
        StringWriter sw = new StringWriter();
        try {
            marshaller.marshal(rm, new StreamResult(sw));
            return sw.toString();
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        return null;
    }

    private void addMember(User user) {
        Member member = new Member(user.getOpenid());
        member.setSex(user.getSex() == 1 ? SexEnum.MALE : SexEnum.FEMALE);
        member.setWcProvince(user.getProvince());
        member.setWcCity(user.getCity());
        member.setWcSignature(user.getNickname());
        member.setWcUserName(user.getNickname());
        memberService.update(member);
    }

    public String getAccessToken() {
        String token = restTemplate.getForObject(ACCESS_TOKEN_URL_PATTERN,
                String.class, Configuration.appId(), Configuration.appSecret());
        System.out.println(StringUtils.mid(token, 17, token.length() - 37));
        return StringUtils.mid(token, 17, token.length() - 37);
    }

    public User getUser(final String openId) {
        String userResponseJson = restTemplate.getForObject(GET_USER_URL_PATTERN, String.class, getAccessToken(), openId);
        try {
            return MAPPER.readValue(userResponseJson, User.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }


    private static boolean checkSignaturePass(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{Configuration.token(), timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }
        final String checkedSignature = DigestUtils.shaHex(content.toString());
        return StringUtils.isNotEmpty(checkedSignature) && checkedSignature.equals(signature.toUpperCase());
    }
}
