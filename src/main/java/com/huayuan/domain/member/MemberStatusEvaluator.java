package com.huayuan.domain.member;

import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.CreditResult;
import com.huayuan.repository.account.AccountRepository;
import com.huayuan.repository.applicationloan.ApplicationRepository;
import com.huayuan.repository.credit.CreditResultRepository;
import com.huayuan.repository.member.MemberRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.IntRange;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by dell on 14-4-23.
 */
@Component(value = "memberStatusEvaluator")
public class MemberStatusEvaluator {
    @Inject
    private MemberRepository memberRepository;
    @Inject
    private ApplicationRepository applicationRepository;
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private CreditResultRepository creditResultRepository;

    public MemberStatusEvaluator() {
    }

    public String evaluate(final Long memberId) {
        Member member = memberRepository.findOne(memberId);
        if (member.getPreCrl() == null || member.getPreCrl() == 0) return "1";
        List<Application> applications = applicationRepository.findByMemberIdOrderByApplicationNoDesc(memberId);
        boolean hasApplication = CollectionUtils.isNotEmpty(applications);
        if (!hasApplication) {
            if (member.getPreCrl() > 1000) return "3.1";
            if (member.getPreCrl() < 1000) return "3.2";
        } else {
            Application application = applications.get(0);
            if (new IntRange(0, 4).containsInteger(application.getStatus())) return "4";
            if (application.getStatus() == 5 && !member.getStatus().equals(MemberStatusEnum.REJECTED)) {
                if (application.isApproved()) return "5.1";
                if (application.isDeclined()) return "5.2";
            }
            if (application.getStatus() == 6) return "6";
            if (application.getStatus() == 7) return "7";
        }
        if (StringUtils.containsAny(member.getBlockCode(), new char[]{'\u0000', 'B', 'C'})) return "8";
        if (member.getStatus().equals(MemberStatusEnum.REJECTED)) {
            if (StringUtils.containsAny(member.getBlockCode(), new char[]{'D', 'E', 'F', 'G', 'I'})) return "11";
            if (member.isDeclined()) return "12";
        }
        if (accountRepository.findByMemberId(memberId).getCrlAvl() < 1000) return "9";
        return null;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:\\development\\working\\repaymentApp\\repaymentApp\\src\\main\\resources\\applicationContext.xml");
        MemberStatusEvaluator memberStatusEvaluator = applicationContext.getBean("memberStatusEvaluator", MemberStatusEvaluator.class);
        System.out.println(memberStatusEvaluator.evaluate(2l));
    }
}
