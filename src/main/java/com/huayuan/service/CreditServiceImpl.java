package com.huayuan.service;

import com.huayuan.common.event.MemberStatusChangeEvent;
import com.huayuan.domain.accounting.Account;
import com.huayuan.domain.accounting.Loans;
import com.huayuan.domain.credit.ApplicationCreditInfo;
import com.huayuan.domain.credit.Pboc;
import com.huayuan.domain.credit.TvExecution;
import com.huayuan.domain.credit.TvQuestionGenerator;
import com.huayuan.domain.idgenerator.IdSequenceGenerator;
import com.huayuan.domain.loanapplication.*;
import com.huayuan.domain.member.Member;
import com.huayuan.domain.member.MemberStatusEnum;
import com.huayuan.domain.member.WhiteList;
import com.huayuan.domain.wechat.ReplyAnswer;
import com.huayuan.repository.account.AccountRepository;
import com.huayuan.repository.account.LoanRepository;
import com.huayuan.repository.applicationloan.ApplicationRepository;
import com.huayuan.repository.applicationloan.ApprovalRepository;
import com.huayuan.repository.applicationloan.TelephoneTvRepository;
import com.huayuan.repository.credit.*;
import com.huayuan.repository.member.MemberRepository;
import com.huayuan.repository.member.WhiteListRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.Collections;
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
    private TelephoneTvRepository telephoneTVRepository;
    @Inject
    private PbocRepository pbocRepository;
    @Inject
    private MemberRepository memberRepository;
    @Inject
    private ApprovalRepository approvalRepository;
    @Inject
    private LoanRepository loanRepository;
    @Value("${weChat.tvApproveResult}")
    private String tvApproveResultTemplate;
    @Value("${weChat.baseUrl}")
    private String baseUrl;
    private ApplicationEventPublisher publisher;
    @Inject
    private WhiteListRepository whiteListRepository;

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
        return MessageFormat.format(tvApproveResultTemplate, baseUrl, approval.getApplication().getMember().getId(),
                status, approval.getApplication().getApplicationNo()).replaceAll("\"", "\\\\\"");
    }

    private void updateMemberStatusAsReject(Approval approval) {
        Member member = approval.getApplication().getMember();
        if (approval.isRejectNeeded()) {
            member.setStatus(MemberStatusEnum.REJECTED);
            if (approval.isBlockXNeeded()) {
                member.setBlockCode("X");
                member.setBlockTime(new Date());
            }
            member.setCreateTime(new Date());
        }
        memberRepository.save(member);
    }

    private void updateCreditResultUsingLastApplication(Approval approval) {
        CreditResult creditResult = approval.getApplication().getMember().getCreditResult();
        creditResult.setCreateTime(new Date());
        creditResult.setLastApplication(approval.getApplication());
        creditResult.setLastDecision(approval.getDecision());
        creditResult.setLastPbocBackTime(approval.getApplication().getaScore().getPbocBackTime());
        creditResult.setLastRating(approval.getApplication().getaScore().getRating());
        creditResult.setLastReason1(approval.getReason1());
        creditResult.setLastReason2(approval.getReason2());
        creditResult.setLastReason3(approval.getReason3());
        creditResult.setLastScore(approval.getApplication().getaScore().getScore());
        creditResult.setCreateTime(new Date());
        creditResultRepository.save(creditResult);
    }

    private Account createAccount(Approval approval) {
        Account account = accountRepository.findByMemberId(approval.getApplication().getMember().getId());
        if (account == null && approval.isDeclined()) return null;
        if (account == null) {
            account = new Account();
            account.setMember(approval.getApplication().getMember());
        }
        account.setCrl(approval.getSugCrl());
        if (approval.isApproved()) account.setCrlUsed(account.getCrlUsed() + approval.getAmt());
        account.setCrlAvl(account.getCrl() - account.getCrlUsed());
        return accountRepository.save(account);
    }

    private void removeWhiteListBy(Long memberId) {
        WhiteList whiteList = whiteListRepository.findByMemberId(memberId);
        if (whiteList != null) whiteListRepository.delete(whiteList);
    }

    @Override
    public Approval approve(Approval approval) {
        final Application application = approval.getApplication();
        createAccount(approval);
        updateCreditResultUsingLastApplication(approval);
        updateMemberStatusAsReject(approval);
        updateApplicationAsApproved(application);
        approval.setCreateTime(new Date());
        Approval result = approvalRepository.save(approval);
        MemberStatusChangeEvent event = new MemberStatusChangeEvent(this, application.getMember().getWcNo(), getApproveResultMessage(approval));
        publisher.publishEvent(event);
        removeWhiteListBy(application.getMember().getId());
        return result;
    }

    private void updateApplicationAsApproved(Application application) {
        application.setStatus(Application.APPROVED_STATUS);
        application.setCreateTime(new Date());
        applicationRepository.save(application);
    }

    @Override
    public Approval saveTemporarily(Approval approval) {
        final Application application = approval.getApplication();
        application.setStatus(Application.TEMPORARILY_STATUS);
        applicationRepository.save(application);
        return approvalRepository.save(approval);
    }

    @Scheduled(cron = "0 0/20 * * * ?")
    @Transactional
    @Override
    public void telephoneVerification() {
        List<TelephoneVerification> tvs = Collections.unmodifiableList(tvRepository.findByTypeAndDecision(0, StringUtils.EMPTY));
        for (TelephoneVerification tv : tvs) {
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
        ReplyAnswer rn = new ReplyAnswer(replyAnswer.toUpperCase());
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
    public ApplicationCreditInfo buildCreditInfo(String appNo) {
        Application application = applicationRepository.findOne(appNo);
        application.getTelephoneTVs().size();
        Pboc pboc = pbocRepository.findByCertNo(application.getMember().getIdCard().getIdNo());
        Member member = memberRepository.findOne(application.getMember().getId());
        member.getCreditCardBills().size();
        member.getCreditCards().size();
        Loans loans = new Loans(loanRepository.findByMember_Id(application.getMember().getId()));
        Account account = accountRepository.findByMemberId(member.getId());
        return new ApplicationCreditInfo.Builder()
                .application(application)
                .pboc(pboc)
                .member(member)
                .loans(loans)
                .account(account)
                .build();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
