package com.huayuan.integration.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huayuan.common.MemberStatusChangeEvent;
import com.huayuan.domain.member.Member;
import com.huayuan.domain.member.MemberStatusEvaluator;
import com.huayuan.domain.member.SexEnum;
import com.huayuan.integration.wechat.domain.*;
import com.huayuan.repository.integration.HintMessageRepository;
import com.huayuan.repository.integration.MenuRepository;
import com.huayuan.service.AccountService;
import com.huayuan.service.CreditService;
import com.huayuan.service.MemberService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
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
import java.util.List;

/**
 * Created by Johnson on 4/14/14.
 */
@Controller(value = "messageController")
public class MessageController implements ApplicationListener<MemberStatusChangeEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    private static final String ACCESS_TOKEN_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={appSecret}";
    private static final String GET_USER_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={accessToken}&openid={openid}&lang=zh_CN";
    private static final String SEND_MESSAGE_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={accessToken}";
    final String CUSTOM_MESSAGE_TEMPLATE = "'{'\"touser\":\"{0}\",\"msgtype\":\"text\",\"text\":'{'\"content\":\"{1}\"'}}'";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Inject
    private MemberService memberService;
    @Inject
    private Unmarshaller unmarshaller;
    @Inject
    private Marshaller marshaller;
    @Inject
    private RestTemplate restTemplate;
    @Inject
    private MemberStatusEvaluator memberStatusEvaluator;
    @Inject
    private MenuRepository menuRepository;
    @Inject
    private AccountService accountService;
    @Inject
    private CreditService creditService;
    @Inject
    private HintMessageRepository hintMessageRepository;
    private List<Menu> menus;
    private List<HintMessage> hintMessages;

    @Value("${weChat.baseUrl}")
    private String baseUrl;
    @Value("${weChat.welcomeTemplate}")
    private String welcomeTemplate;
    @Value("${weChat.tvReplyTemplate}")
    private String tvReplyTemplate;
    @Value("${weChat.appId}")
    private String appId;
    @Value("${weChat.appSecret}")
    private String appSecret;
    @Value("${weChat.token}")
    private String appToken;

    @PostConstruct
    public void init() {
        menus = menuRepository.findAll();
        hintMessages = hintMessageRepository.findAll();
    }

    public MessageTemplate getTemplates(final String menu_Key, String status) {
        for (Menu menu : menus) {
            if (menu.getMenu_key().equalsIgnoreCase(menu_Key)) {
                for (MessageTemplate template : menu.getTemplates()) {
                    if (StringUtils.contains(template.getStatuses(), status)) return template;
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/huayuan158", method = RequestMethod.GET)
    @ResponseBody
    public String init(@RequestParam String signature, @RequestParam String timestamp,
                       @RequestParam String nonce, @RequestParam String echostr) throws IOException {
        if (checkSignaturePass(signature, timestamp, nonce))
            return echostr;
        return null;
    }

    @RequestMapping(value = "/members/{memberId}/status/{status}", method = RequestMethod.GET)
    @ResponseBody
    public void sendHintMessage(@PathVariable Long memberId, @PathVariable Integer status) throws IOException {
        MemberStatusChangeEvent event = new MemberStatusChangeEvent(this, memberService.find(memberId).getWcNo(),
                hintMessages.get(status + 1).getTemplate());
        onApplicationEvent(event);
    }


    @RequestMapping(value = "/huayuan158", method = RequestMethod.POST)
    public void handleMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        EventMessage eventMessage = (EventMessage) unmarshaller.unmarshal(new StreamSource(request.getInputStream()));
        String content;
        if (eventMessage.isSubscribeEvent()) {
            Member member = memberService.findMemberBy(eventMessage.getFromUserName());
            if (member == null) {
                addMember(getUser(eventMessage.getFromUserName()));
            }
            content = MessageFormat.format(welcomeTemplate, baseUrl, member.getId(), memberStatusEvaluator.evaluate(member.getId()) + "&&random=" + RandomStringUtils.randomNumeric(15));
        } else if (eventMessage.isTvMessage()) {
            creditService.replyTv(memberService.findMemberBy(eventMessage.getFromUserName()).getId(), eventMessage.getContent());
            content = tvReplyTemplate;
        } else {
            content = getContent(eventMessage);
        }
        final String rm = getReplyMessage(eventMessage, content);
        response.getWriter().println(rm);
    }

    private String getContent(EventMessage message) {
        final Long memberId = memberService.findMemberBy(message.getFromUserName()).getId();
        String status = memberStatusEvaluator.evaluate(memberId);
        MessageTemplate tp = getTemplates(message.getEventKey(), status);
        status += "&&random=" + RandomStringUtils.randomNumeric(15);
        if (tp.isCreditLimit())
            return MessageFormat.format(tp.getTemplate(), baseUrl, memberId, status, memberService.getCrl(memberId));
        if (tp.isUrlNotNeeded()) return tp.getTemplate();
        return MessageFormat.format(tp.getTemplate(), baseUrl, memberId, status);
    }

    private String getReplyMessage(EventMessage eventMessage, String content) {
        EventMessage rm = new EventMessage();
        rm.setFromUserName(eventMessage.getToUserName());
        rm.setToUserName(eventMessage.getFromUserName());
        rm.setMsgType("text");
        rm.setCreateTime(new Date().getTime());
        rm.setFuncFlag("0");
        rm.setContent(content);
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
        String token = restTemplate.getForObject(ACCESS_TOKEN_URL_PATTERN, String.class, appId, appSecret);
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


    private boolean checkSignaturePass(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{appToken, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }
        final String checkedSignature = DigestUtils.shaHex(content.toString());
        return StringUtils.isNotEmpty(checkedSignature) && checkedSignature.equals(signature.toUpperCase());
    }

    @Override
    public void onApplicationEvent(MemberStatusChangeEvent event) {
        final String message = MessageFormat.format(CUSTOM_MESSAGE_TEMPLATE, event.getOpenId(), event.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(message, headers);
        restTemplate.postForEntity(SEND_MESSAGE_URL_PATTERN, entity, String.class, getAccessToken());
    }
}
