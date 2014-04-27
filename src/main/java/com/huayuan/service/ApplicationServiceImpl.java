package com.huayuan.service;

import com.huayuan.domain.loanapplication.AScore;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.Approval;
import com.huayuan.domain.loanapplication.TelephoneVerification;
import com.huayuan.domain.member.CreditCard;
import com.huayuan.repository.applicationloan.AScoreRepository;
import com.huayuan.repository.applicationloan.ApplicationRepository;
import com.huayuan.repository.applicationloan.ApprovalRepository;
import com.huayuan.repository.applicationloan.TvRepository;
import com.huayuan.repository.member.CreditCardRepository;
import org.apache.commons.collections.CollectionUtils;
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
    private TvRepository tvRepository;

    @Inject
    private CreditCardRepository creditCardRepository;

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
    public void telephoneVerification(TelephoneVerification tv) {
        tvRepository.save(tv);
    }

    @Override
    public void aStore(AScore aScore) {
        aScoreRepository.save(aScore);
    }

    @Override
    public Application bindCreditCard(Application application, String creditCArdNo) {
        CreditCard creditCard = creditCardRepository.findByCardNo(creditCArdNo).get(0);
        application.setCreditCard(creditCard);
        return applicationRepository.save(application);
    }
}
