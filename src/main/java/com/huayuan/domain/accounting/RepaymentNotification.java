package com.huayuan.domain.accounting;

import com.huayuan.domain.member.Member;
import com.huayuan.domain.member.MemberStatusEvaluator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dell on 14-7-29.
 */

@Component(value = "repaymentNotification")
public class RepaymentNotification {
    private static final List<String> DAYS_CHINESE_INDEX = Arrays.asList("今", "明", "后");
    @Value("${weChat.repaymentNotification}")
    private String repaymentNotificationPattern;
    @Value("${weChat.overDueNotification}")
    private String overDueNotificationPattern;
    @Value("${weChat.baseUrl}")
    private String baseUrl;
    @Inject
    private MemberStatusEvaluator memberStatusEvaluator;

    public String getNotificationMessage(Loan loan, int count) {
        final Member member = loan.getMember();
        final String memberStatus = memberStatusEvaluator.evaluate(member);
        if (loan.repaymentNotificationNeeded()) {
            return MessageFormat.format(repaymentNotificationPattern, baseUrl, member.getId(), memberStatus, DAYS_CHINESE_INDEX.get(loan.daysBetweenDueDateAndNow()), count).replaceAll("\"", "\\\\\"");
        }
        if (loan.overDueNotificationNeeded())
            return MessageFormat.format(overDueNotificationPattern, baseUrl, member.getId(), memberStatus).replaceAll("\"", "\\\\\"");
        return null;
    }
}