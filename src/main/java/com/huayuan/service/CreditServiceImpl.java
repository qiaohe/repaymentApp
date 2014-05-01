package com.huayuan.service;

import com.huayuan.common.MemberStatusChangeEvent;
import com.huayuan.domain.accounting.Account;
import com.huayuan.domain.credit.TvQuestionGenerator;
import com.huayuan.domain.idgenerator.IdSequenceGenerator;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.CreditResult;
import com.huayuan.domain.loanapplication.Staff;
import com.huayuan.domain.loanapplication.TelephoneVerification;
import com.huayuan.repository.ValueMobileAreaRepository;
import com.huayuan.repository.account.AccountRepository;
import com.huayuan.repository.credit.CreditResultRepository;
import com.huayuan.repository.credit.StaffRepository;
import com.huayuan.repository.credit.TvRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Johnson on 4/7/14.
 */
@Service(value = "creditService")
@Transactional
public class CreditServiceImpl implements CreditService, ApplicationEventPublisherAware {
    @Inject
    private CreditResultRepository creditResultRepository;
    @Inject
    private StaffRepository staffRepository;
    @Inject
    private IdSequenceGenerator idSequenceGenerator;
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private TvRepository tvRepository;
    @Inject
    private TvQuestionGenerator tvQuestionGenerator;

    private ApplicationEventPublisher publisher;

    @Override
    public void addCreditResult(CreditResult creditResult) {
        creditResultRepository.save(creditResult);
    }

    @Override
    public Iterable<Staff> getStaffs() {
        return staffRepository.findAll();
    }

    @Override
    public void registerStaff(Staff staff) {
        staff.setStaffId(idSequenceGenerator.getStaffNo());
        staffRepository.save(staff);
    }

    @Override
    public void approve(Application application) {
        Account account = new Account();
        account.setMember(application.getMember());
        account.setCrlUsed(Double.valueOf(application.getApproval().getSugCrl().toString()));
        MemberStatusChangeEvent event = new MemberStatusChangeEvent(this, account.getMember().getWcNo(), "");
        publisher.publishEvent(event);
        accountRepository.save(account);
    }

    @Override
    public void telephoneVerification() {
        for (TelephoneVerification tv : tvRepository.findByTypeAndDecision(1, 0)) {
            final String q = tvQuestionGenerator.generate(tv);
            MemberStatusChangeEvent event = new MemberStatusChangeEvent(this, tv.getApplication().getMember().getWcNo(), q);
            publisher.publishEvent(event);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
