package com.huayuan.service;

import com.huayuan.domain.member.Member;
import com.huayuan.domain.wechat.Message;
import com.huayuan.domain.wechat.MessageTemplate;

/**
 * Created by Johnson on 6/26/14.
 */
public interface WeChatService {
    public void sendHintMessage(Long memberId, Integer status);

    public Member getMemberBy(String openId);

    public String createReplyMessage(Message inBoundMessage, String replyMessageContent);

    public String createReplyNews(Message inBoundMessage);

    public boolean checkSignaturePass(String signature, String timestamp, String nonce);

    public MessageTemplate getTemplate(String menu_Key, String status);

    public String getReplyMessage(String keyword);
}
