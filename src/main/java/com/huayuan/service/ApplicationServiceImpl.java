package com.huayuan.service;

import com.huayuan.domain.credit.ApplicationSummary;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.Approval;
import com.huayuan.domain.member.CreditCard;
import com.huayuan.domain.member.Member;
import com.huayuan.repository.applicationloan.AScoreRepository;
import com.huayuan.repository.applicationloan.ApplicationRepository;
import com.huayuan.repository.applicationloan.ApprovalRepository;
import com.huayuan.repository.member.CreditCardRepository;
import com.huayuan.repository.member.MemberRepository;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Johnson on 4/7/14.
 */
@Service(value = "applicationService")
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
    @Inject
    private ApplicationRepository applicationRepository;
    @Inject
    private AScoreRepository aScoreRepository;
    @Inject
    private ApprovalRepository approvalRepository;
    @Inject
    private CreditCardRepository creditCardRepository;
    @Inject
    private MemberRepository memberRepository;

    @Override
    public Application getApplication(String appNo) {
        return applicationRepository.findOne(appNo);
    }

    @Override
    public Application getApplicationBy(Long memberId) {
        List<Application> apps = applicationRepository.findByMemberId(memberId);
        return CollectionUtils.isEmpty(apps) ? null : apps.get(0);
    }

    @Override
    public Application applyLoan(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public void approve(Approval approval) {
        approvalRepository.save(approval);
    }

    @Override
    public Application bindCreditCard(Long memberId, String creditCardNo) {
        Member member = memberRepository.findOne(memberId);
        Application application = applicationRepository.findByMemberIdAndStatusAndApproval_Decision(memberId, member.getExistingFlag().equals(2) ? 7 : 5, "A");
        if (application == null)
            throw new IllegalStateException("member id:" + memberId + "'s application is in illegal status");
        CreditCard creditCard = creditCardRepository.findByCardNo(creditCardNo).get(0);
        application.setCreditCard(creditCard);
        application.setStatus(7);
        member.setExistingFlag(2);
        memberRepository.save(member);
        return applicationRepository.save(application);
    }

    @Override
    public List<ApplicationSummary> getApplicationSummaries() {
        return applicationRepository.findApplicationSummaries();
    }

    @Override
    public List<ApplicationSummary> getApplicationSummaries(Integer curPage, String query) {
        return applicationRepository.findApplicationSummaries(curPage, query);
    }

    @Override
    public long getApplicationCount() {
        return applicationRepository.countByApplyTimeGreaterThan(DateTime.now().withTime(0, 0, 0, 0).toDate());
    }
}
