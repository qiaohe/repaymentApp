package com.huayuan.domain.member;

import com.huayuan.domain.accounting.Loan;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.repository.account.AccountRepository;
import com.huayuan.repository.account.LoanRepository;
import com.huayuan.repository.applicationloan.ApplicationRepository;
import com.huayuan.repository.credit.CreditResultRepository;
import com.huayuan.repository.member.MemberRepository;
import com.huayuan.repository.member.WhiteListRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    @Inject
    private LoanRepository loanRepository;
    @Inject
    private WhiteListRepository whiteListRepository;

    public MemberStatusEvaluator() {
    }

    private String getStatusByLoans(Long memberId) {
        List<Loan> loans = loanRepository.findByMember_IdAndStatusIn(memberId, Arrays.asList(0, 1, 2, 9));
        if (loans.size() == 0) return "7";
        Map<Integer, Integer> maps = new ConcurrentHashMap<>();
        for (Loan loan : loans) {
            if (loan.isOverDueStatus()) return "11";
            maps.put(loan.getStatus(), maps.get(loan.getStatus()) == null ? 1 : maps.get(loan.getStatus()) + 1);
        }
        if (maps.get(8) != null && loans.size() == maps.get(8)) return "7";
        if (maps.get(9) != null && loans.size() == maps.get(9) || !maps.containsKey(0)) {
            if (accountRepository.findByMemberId(memberId).getCrlAvl() < 1000) return "9";
            return "10";
        }
        return "8";
    }

    public Application getApprovingApplication(Long memberId) {
        List<Application> applications = applicationRepository.findByMemberIdOrderByApplicationNoDesc(memberId);
        return CollectionUtils.isNotEmpty(applications) ? applications.get(0) : null;
    }

    public String evaluate(Member member) {
        if (MemberStatusEnum.REJECTED.equals(member.getStatus())) {
            if (StringUtils.containsAny(member.getBlockCode(), new char[]{'D', 'E', 'F', 'G', 'I'})) return "11";
            return "12";
        }
        if (member.getPreCrl() == null || member.getPreCrl() == 0) return "1";
        Application application = getApprovingApplication(member.getId());
        if (application != null) {
            final String status = application.getWeChatStatus(whiteListRepository.findAll());
            if (application.getExistingFlag() == 2 || application.getStatus() == 7)
                return getStatusByLoans(member.getId());
            return status;
        }
        return member.getPreCrl() > 1000 ? "3.1" : "3.2";
    }
}
