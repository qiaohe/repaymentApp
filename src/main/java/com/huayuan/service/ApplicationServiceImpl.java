package com.huayuan.service;

import com.huayuan.domain.loanapplication.AScore;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.Approval;
import com.huayuan.domain.loanapplication.TelephoneVerification;
import com.huayuan.repository.applicationloan.AScoreRepository;
import com.huayuan.repository.applicationloan.ApplicationRepository;
import com.huayuan.repository.applicationloan.ApprovalRepository;
import com.huayuan.repository.applicationloan.TvRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

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

    @Override
    public Application getApplication(String appNo) {
        return applicationRepository.findOne(appNo);
    }

    @Override
    public Application applyLoan(Application application) {
        Application app = applicationRepository.save(application);
        applicationRepository.execute(app);
        return app;
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
}
