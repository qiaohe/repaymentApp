package com.huayuan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huayuan.common.event.MemberStatusChangeEvent;
import com.huayuan.domain.member.Member;
import com.huayuan.domain.member.SexEnum;
import com.huayuan.domain.wechat.*;
import com.huayuan.repository.FeedbackArticleRepository;
import com.huayuan.repository.integration.HintMessageRepository;
import com.huayuan.repository.integration.MenuRepository;
import com.huayuan.repository.integration.ReplyMessageRepository;
import com.huayuan.repository.member.MemberRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.huayuan.common.util.Constants.*;

/**
 * Created by Johnson on 6/26/14.
 */
@Service(value = "weChatService")
@Transactional
public class WeChatServiceImpl implements WeChatService, ApplicationListener<MemberStatusChangeEvent> {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatService.class);
    @Inject
    private Marshaller marshaller;
    @Inject
    private RestTemplate restTemplate;
    @Inject
    private MenuRepository menuRepository;
    @Inject
    private HintMessageRepository hintMessageRepository;
    @Inject
    private MemberRepository memberRepository;
    @Inject
    private FeedbackArticleRepository feedbackArticleRepository;
    @Inject
    private ReplyMessageRepository replyMessageRepository;

    @Value("${weChat.appSecret}")
    private String appSecret;
    @Value("${weChat.token}")
    private String appToken;
    @Value("${weChat.appId}")
    private String appId;
    private List<Menu> menus;
    private List<HintMessage> hintMessages;
    private List<ReplyMessage> replyMessages;

    @PostConstruct
    private void init() {
        menus = menuRepository.findAll();
        hintMessages = hintMessageRepository.findAll();
        replyMessages = replyMessageRepository.findAll();
    }

    private String getAccessToken() {
        String token = restTemplate.getForObject(ACCESS_TOKEN_URL_PATTERN, String.class, appId, appSecret);
        return StringUtils.mid(token, 17, token.length() - 37);
    }

    public MessageTemplate getTemplate(String menu_Key, String status) {
        for (Menu menu : menus) {
            if (menu.getMenu_key().equalsIgnoreCase(menu_Key)) {
                for (MessageTemplate template : menu.getTemplates()) {
                    if (StringUtils.contains(template.getStatuses(), status)) return template;
                }
            }
        }
        return null;
    }

    @Override
    public String getReplyMessage(String keyword) {
        for (ReplyMessage m : replyMessages) {
            if (StringUtils.isNotEmpty(keyword) && StringUtils.contains(m.getKeywords(), keyword))
                return m.getReplyMessage();
        }
        return null;
    }

    public boolean checkSignaturePass(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{appToken, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }
        final String checkedSignature = DigestUtils.shaHex(content.toString());
        return StringUtils.isNotEmpty(checkedSignature) && checkedSignature.equals(signature.toUpperCase());
    }

    private WeChatUser getUser(final String openId) {
        String userResponseJson = restTemplate.getForObject(GET_USER_URL_PATTERN, String.class, getAccessToken(), openId);
        try {
            return MAPPER.readValue(userResponseJson, WeChatUser.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Member getMemberBy(String openId) {
        Member member = memberRepository.findByWcNo(openId);
        if (member != null) return member;
        member = new Member(openId);
        WeChatUser weChatUser = getUser(openId);
        member.setSex(weChatUser.getSex() == 1 ? SexEnum.MALE : SexEnum.FEMALE);
        member.setWcProvince(weChatUser.getProvince());
        member.setWcCity(weChatUser.getCity());
        member.setWcSignature(weChatUser.getNickname());
        member.setWcUserName(weChatUser.getNickname());
        return memberRepository.save(member);
    }

    @Override
    public void sendHintMessage(Long memberId, Integer status) {
        Member member = memberRepository.findOne(memberId);
        String hintMessage = hintMessages.get(status + 1).getTemplate();
        MemberStatusChangeEvent event = new MemberStatusChangeEvent(this, member.getWcNo(), hintMessage);
        onApplicationEvent(event);
    }

    public String createReplyMessage(Message inBoundMessage, String replyMessageContent) {
        Message rm = new Message();
        rm.setFromUserName(inBoundMessage.getToUserName());
        rm.setToUserName(inBoundMessage.getFromUserName());
        rm.setMsgType("text");
        rm.setCreateTime(new Date().getTime());
        rm.setFuncFlag("0");
        rm.setContent(replyMessageContent);
        StringWriter sw = new StringWriter();
        try {
            marshaller.marshal(rm, new StreamResult(sw));
            return sw.toString();
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public String createReplyNews(Message inBoundMessage) {
        Message news = new Message();
        news.setFromUserName(inBoundMessage.getToUserName());
        news.setToUserName(inBoundMessage.getFromUserName());
        news.setMsgType("news");
        news.setCreateTime(new Date().getTime());
        Message.Articles articles = new Message.Articles();
        List<FeedbackArticle> articleList = feedbackArticleRepository.findByMenuEventId(inBoundMessage.getEventKey());
        news.setArticleCount(articleList.size());
        for (FeedbackArticle fa : articleList) {
            articles.addArticle(new Message.Article(fa.getTitle(), fa.getDescription(), fa.getPicUrl(), fa.getUrl()));
        }
        news.setArticles(articles);
        StringWriter sw = new StringWriter();
        try {
            marshaller.marshal(news, new StreamResult(sw));
            return sw.toString();
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        return null;
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
