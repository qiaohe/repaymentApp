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
        if (member.getPreCrl() > 0 && member.getEducation() != null) return "2";
        List<Application> applications = applicationRepository.findByMemberIdOrderByApplicationNoDesc(memberId);
        boolean hasApplication = CollectionUtils.isNotEmpty(applications);
        if (!hasApplication) {
            if (member.getPreCrl() > 1000) return "3.1";
            if (member.getPreCrl() < 1000) return "3.2";
        } else {
            Application application = applications.get(0);
            if (new IntRange(0, 4).containsInteger(application.getStatus())) return "4";
            if (application.getStatus() == 5 && application.getApproval().getDecision().equals("A")) return "5";
            if (application.getStatus() == 6) return "6";
            if (application.getStatus() == 7) return "7";
        }
        if (StringUtils.containsAny(member.getBlockCode(), new char[]{' ', 'B', 'C'})) return "8";
        CreditResult creditResult = creditResultRepository.findByMemberId(memberId);
        if (member.getStatus().equals(MemberStatusEnum.REJECTED)) {
            if (StringUtils.containsAny(member.getBlockCode(), new char[]{'D', 'E', 'F', 'G', 'I'})) return "11";
        } else if (member.getBlockCode().equals("X") || ((creditResult != null) && (creditResult.getLastReason1().contains("D100")
                || creditResult.getLastReason2().contains("D100") || creditResult.getLastReason3().contains("D100"))) ||
                ((creditResult != null) && (creditResult.getLastReason1().contains("D102")
                        || creditResult.getLastReason2().contains("D102") || creditResult.getLastReason3().contains("D102"))) ||
                ((creditResult != null) && (creditResult.getLastReason1().contains("D108")
                        || creditResult.getLastReason2().contains("D108") || creditResult.getLastReason3().contains("D108")))
                ) return "12";
        if (accountRepository.findByMemberId(memberId).getCrlAvl() < 1000) return "9";
        return null;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:\\development\\working\\repaymentApp\\repaymentApp\\src\\main\\resources\\applicationContext.xml");
        MemberStatusEvaluator memberStatusEvaluator = applicationContext.getBean("memberStatusEvaluator", MemberStatusEvaluator.class);
        System.out.println(memberStatusEvaluator.evaluate(2l));
    }
}
