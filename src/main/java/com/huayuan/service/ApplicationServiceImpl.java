package com.huayuan.service;

import com.huayuan.common.MemberStatusChangeEvent;
import com.huayuan.domain.credit.ApplicationSummary;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.Approval;
import com.huayuan.domain.member.CreditCard;
import com.huayuan.repository.applicationloan.AScoreRepository;
import com.huayuan.repository.applicationloan.ApplicationRepository;
import com.huayuan.repository.applicationloan.ApprovalRepository;
import com.huayuan.repository.member.CreditCardRepository;
import com.huayuan.repository.member.MemberRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Johnson on 4/7/14.
 */
@Service(value = "applicationService")
@Transactional
public class ApplicationServiceImpl implements ApplicationService, ApplicationEventPublisherAware {
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
    @Value("${weChat.bindCreditCardSuccess}")
    private String bindCreditCardSuccess;
    @Value("${weChat.bindCreditCardFail}")
    private String bindCreditCardFail;
    private ApplicationEventPublisher publisher;

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
    public Application bindCreditCard(Long memberId, String creditCArdNo) {
        Application application = applicationRepository.findByMemberIdAndStatusAndApproval_Decision(memberId, 5, "A");
        if (application == null) {
            MemberStatusChangeEvent event = new MemberStatusChangeEvent(this, memberRepository.findOne(memberId).getWcNo(), bindCreditCardFail);
            publisher.publishEvent(event);
            throw new IllegalStateException("member id:" + memberId + "'s application is in illegal status");
        }
        CreditCard creditCard = creditCardRepository.findByCardNo(creditCArdNo).get(0);
        application.setCreditCard(creditCard);
        application.setStatus(7);
        MemberStatusChangeEvent event = new MemberStatusChangeEvent(this, memberRepository.findOne(memberId).getWcNo(), bindCreditCardSuccess);
        publisher.publishEvent(event);
        return applicationRepository.save(application);
    }

    @Override
    public List<ApplicationSummary> getApplicationSummaries() {
        return applicationRepository.findApplicationSummaries();
    }

    @Override
    public List<ApplicationSummary> getApplicationSummaries(String query) {
        return applicationRepository.findApplicationSummaries(query);
    }

//    @Override
//    public Object getApplicationBy(String appNo) {
//        return applicationRepository.findApplicationBy(appNo);
//    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
