package com.huayuan.service;

import com.huayuan.common.MemberStatusChangeEvent;
import com.huayuan.domain.accounting.Account;
import com.huayuan.domain.credit.TvExecution;
import com.huayuan.domain.credit.TvQuestionGenerator;
import com.huayuan.domain.idgenerator.IdSequenceGenerator;
import com.huayuan.domain.loanapplication.*;
import com.huayuan.integration.wechat.domain.ReplyAnswer;
import com.huayuan.repository.account.AccountRepository;
import com.huayuan.repository.applicationloan.ApplicationRepository;
import com.huayuan.repository.applicationloan.ApprovalRepository;
import com.huayuan.repository.applicationloan.TelephoneTVRepository;
import com.huayuan.repository.credit.CreditResultRepository;
import com.huayuan.repository.credit.StaffRepository;
import com.huayuan.repository.credit.TvExecutionRepository;
import com.huayuan.repository.credit.TvRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.Date;
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
    @Inject
    private TvExecutionRepository tvExecutionRepository;
    @Inject
    private ApplicationRepository applicationRepository;
    @Inject
    private TelephoneTVRepository telephoneTVRepository;
    @Inject
    private ApprovalRepository approvalRepository;
    @Value("${weChat.tvApproveResult}")
    private String tvApproveResultTemplate;
    @Value("${weChat.baseUrl}")
    private String baseUrl;
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

    private String getApproveResultMessage(Approval approval) {
        String status = approval.isApproved() ? "5.1" : "5.2" + "&&random=" + RandomStringUtils.randomNumeric(15);
        return MessageFormat.format(tvApproveResultTemplate, baseUrl, approval.getApplication().getMember().getId(), status);
    }

    @Override
    public Approval approve(Approval approval) {
        final Application application = approval.getApplication();
        Account account = accountRepository.findByMemberId(application.getMember().getId());
        if (account == null && approval.isDeclined()) return null;
        if (account == null) {
            account = new Account();
            account.setMember(application.getMember());
        }
        account.setCrl(approval.getSugCrl());
        if (approval.isApproved()) account.setCrlUsed(account.getCrlUsed() + approval.getAmt());
        accountRepository.save(account);
        Approval result = approvalRepository.save(approval);
        MemberStatusChangeEvent event = new MemberStatusChangeEvent(this, account.getMember().getWcNo(), getApproveResultMessage(approval));
        publisher.publishEvent(event);
        return result;
    }

    @Scheduled(cron = "0 30 8,12,18 * * ?")
    @Transactional
    @Override
    public void telephoneVerification() {
        for (TelephoneVerification tv : tvRepository.findByTypeAndDecision(0, StringUtils.EMPTY)) {
            if (getTvExecution(tv.getApplication().getApplicationNo()) != null) continue;
            final String q = tvQuestionGenerator.generate(tv);
            TvExecution te = new TvExecution();
            te.setApplication(tv.getApplication());
            te.setQuestion(q);
            tvExecutionRepository.save(te);
            MemberStatusChangeEvent event = new MemberStatusChangeEvent(this, tv.getApplication().getMember().getWcNo(), q);
            publisher.publishEvent(event);
        }
    }

    @Override
    public TvExecution getTvExecution(String appNo) {
        return tvExecutionRepository.findByApplication_ApplicationNo(appNo);
    }

    @Override
    public void replyTv(Long memberId, String replyAnswer) {
        ReplyAnswer rn = new ReplyAnswer(replyAnswer);
        if (!rn.isValidAnswer()) return;
        List<Application> apps = applicationRepository.findByMemberId(memberId);
        TvExecution tvExecution = getTvExecution(apps.get(0).getApplicationNo());
        if (tvExecution.ignoreReplyIfNeeded()) return;
        tvExecution.setAnswer1(rn.getRealAnswer1(tvExecution.getQuestion()));
        tvExecution.setAnswer2(rn.getRealAnswer2(tvExecution.getQuestion()));
        tvExecution.setReplyDate(new Date());
        tvExecution.setStatus(1);
        tvExecutionRepository.save(tvExecution);
    }

    @Override
    public TelephoneTV makeTelephoneTv(TelephoneTV telephoneTV) {
        return telephoneTVRepository.save(telephoneTV);
    }

    @Override
    public List<TelephoneTV> getTelephoneTVs(String appNo) {
        return telephoneTVRepository.findByApplication_ApplicationNo(appNo);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:\\development\\working\\repaymentApp\\repaymentApp\\src\\main\\resources\\applicationContext.xml");
        CreditService creditService = applicationContext.getBean("creditService", CreditService.class);
        creditService.telephoneVerification();
//        creditService.replyTv(10l, "#A");
        ApplicationRepository applicationRepository = applicationContext.getBean("applicationRepository", ApplicationRepository.class);
//        applicationRepository.findAllApplications();


    }
}
