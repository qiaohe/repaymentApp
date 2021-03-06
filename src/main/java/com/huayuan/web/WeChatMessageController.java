package com.huayuan.web;

import com.huayuan.domain.member.Member;
import com.huayuan.domain.member.MemberStatusEvaluator;
import com.huayuan.domain.wechat.Feedback;
import com.huayuan.domain.wechat.Message;
import com.huayuan.domain.wechat.MessageTemplate;
import com.huayuan.repository.FeedbackRepository;
import com.huayuan.service.AccountService;
import com.huayuan.service.CreditService;
import com.huayuan.service.MemberService;
import com.huayuan.service.WeChatService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

import static com.huayuan.common.util.Constants.toSBC;

/**
 * Created by Johnson on 4/14/14.
 */
@Controller(value = "messageController")
public class WeChatMessageController {
    @Inject
    private MemberService memberService;
    @Inject
    private Unmarshaller unmarshaller;
    @Inject
    private AccountService accountService;
    @Inject
    private CreditService creditService;
    @Inject
    private WeChatService weChatService;
    @Inject
    private FeedbackRepository feedbackRepository;
    @Inject
    private MemberStatusEvaluator memberStatusEvaluator;
    @Value("${weChat.baseUrl}")
    private String baseUrl;
    @Value("${weChat.welcomeTemplate}")
    private String welcomeTemplate;
    @Value("${weChat.tvReplyTemplate}")
    private String tvReplyTemplate;
    @Value("${weChat.feedback}")
    private String feedbackTemplate;


    @RequestMapping(value = "/huayuan", method = RequestMethod.GET, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String init(@RequestParam String signature, @RequestParam String timestamp,
                       @RequestParam String nonce, @RequestParam String echostr) throws IOException {
        if (weChatService.checkSignaturePass(signature, timestamp, nonce))
            return echostr;
        return echostr;
    }

    @RequestMapping(value = "/ttl", method = RequestMethod.GET)
    @ResponseBody
    public Long getDate() {
        return new Date().getTime();
    }


    @RequestMapping(value = "/members/{memberId}/status/{status}", method = RequestMethod.GET)
    @ResponseBody
    public String sendHintMessage(@PathVariable Long memberId, @PathVariable Integer status) throws IOException {
        weChatService.sendHintMessage(memberId, status);
        return "1";
    }

    @RequestMapping(value = "/huayuan", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    public void handleMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Message message = (Message) unmarshaller.unmarshal(new StreamSource(request.getInputStream()));
        Member member = weChatService.getMemberBy(StringUtils.trim(message.getFromUserName()));
        String content;
        if (message.isSubscribe()) {
            content = welcomeTemplate;
        } else if (message.isFeedback()) {
            content = MessageFormat.format(feedbackTemplate, baseUrl, member.getId());
        } else if (message.isAbout() || message.isAboutTeam()) {
            response.getWriter().println(weChatService.createReplyNews(message));
            return;
        } else if (message.isTelephoneVerification()) {
            creditService.replyTv(member.getId(), toSBC(message.getContent()));
            content = tvReplyTemplate;
        } else {
            content = weChatService.getReplyMessage(message.getContent());
            if (content == null)
                content = getContent(member, message);
        }
        final String rm = weChatService.createReplyMessage(message, content);
        response.getWriter().println(rm);
    }

    private String getContent(Member member, Message message) {
        String status = memberStatusEvaluator.evaluate(member);
        MessageTemplate tp = weChatService.getTemplate(message.getEventKey(), status);
        if (tp.isCreditLimit()) {
            return MessageFormat.format(tp.getTemplate(), baseUrl, member.getId(), status,
                    memberService.getCrl(member.getId()));
        }
        if (tp.isUsedCrl()) {
            return MessageFormat.format(tp.getTemplate(), baseUrl, member.getId(), status,
                    memberService.getAvlCrl(member.getId()));
        }
        if (tp.isRepay()) {
            return MessageFormat.format(tp.getTemplate(), baseUrl, member.getId(), status,
                    accountService.getAmtWithinThisPeriod(member.getId()));
        }
        if (tp.isNoRepay()) {
            return MessageFormat.format(tp.getTemplate(), baseUrl, member.getId(), status);
        }
        if (tp.isApplicationNoNeeded()) {
            return MessageFormat.format(tp.getTemplate(), baseUrl, member.getId(), status,
                    memberStatusEvaluator.getApprovingApplication(member.getId()).getApplicationNo());
        }
        if (tp.isUrlNotNeeded()) return tp.getTemplate();
        return MessageFormat.format(tp.getTemplate(), baseUrl, member.getId(), status);
    }

    @RequestMapping(value = "/members/{memberId}/feedback", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void addFeedback(@PathVariable Long memberId, @RequestParam("f") String feedback) {
        Feedback fb = new Feedback();
        fb.setMemberId(memberId);
        fb.setFeedback(feedback);
        feedbackRepository.save(fb);
    }

    public static void main(String[] args) {
        String s = "仅需两步，信用评估就能帮您快速了解能借多少款，<a href=\"{0}#limit?memberId={1, number, #########}&status={2}\">点此马上进行评估</a>";
        System.out.println(MessageFormat.format(s, "www", 999999999, 10));
    }
}
