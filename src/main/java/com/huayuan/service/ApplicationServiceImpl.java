package com.huayuan.service;

import com.huayuan.domain.loanapplication.*;
import com.huayuan.domain.member.Member;
import com.huayuan.repository.applicationloan.AScoreRepository;
import com.huayuan.repository.applicationloan.ApplicationRepository;
import com.huayuan.repository.applicationloan.ApprovalRepository;
import com.huayuan.repository.applicationloan.TvRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
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

    @Override
    public Application getApplication(String appNo) {
        return applicationRepository.findOne(appNo);
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
}
