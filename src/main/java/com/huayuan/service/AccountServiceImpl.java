package com.huayuan.service;

import com.huayuan.common.event.MemberStatusChangeEvent;
import com.huayuan.common.util.Constants;
import com.huayuan.common.util.Day;
import com.huayuan.domain.accounting.*;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.member.Contract;
import com.huayuan.domain.member.CreditCard;
import com.huayuan.domain.member.Member;
import com.huayuan.domain.member.MemberStatusEnum;
import com.huayuan.domain.payment.PkiPairUtil;
import com.huayuan.repository.account.*;
import com.huayuan.repository.member.CreditCardRepository;
import com.huayuan.repository.member.MemberRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;

import static com.huayuan.common.App.getInstance;

/**
 * Created by dell on 14-4-8.
 */
@Service(value = "accountService")
@Transactional
public class AccountServiceImpl implements AccountService, ApplicationEventPublisherAware {
    private static final double PRECISION_THRESHOLD = 0.01d;
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private RepayPlanRepository repayPlanRepository;
    @Inject
    private LoanRepository loanRepository;
    @Inject
    private LoanRepositoryCustom loanRepositoryCustom;
    @Inject
    private PayRepository payRepository;
    @Inject
    private RePayRepository rePayRepository;
    @Inject
    private LoanSummaryBuilder loanSummaryBuilder;
    @Value("${weChat.bindCreditCardSuccess}")
    private String bindCreditCardSuccess;
    @Value("${weChat.bindCreditCardFail}")
    private String bindCreditCardFail;
    @Value("${account.graceDay}")
    private String graceDay;
    @Value("${account.overdueRating}")
    private String overDueRating;
    @Value("${99bill.payment.gateway}")
    private String paymentGatewayUrlPattern;
    @Inject
    private RepaymentNotification repaymentNotification;
    @Inject
    private RepayOffsetRepository repayOffsetRepository;
    @Inject
    private MemberRepository memberRepository;
    @Inject
    private CreditCardRepository creditCardRepository;
    @Inject
    private ContractRepository contractRepository;
    @Inject
    private PaymentListRepository paymentListRepository;

    private ApplicationEventPublisher publisher;

    @Override
    public Account getAccount(Long accountId) {
        return accountRepository.findOne(accountId);
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Loan createLoan(Loan loan) {
        Loan newLoan = loanRepository.save(loan);
        payRepository.save(newLoan.createTransfer());
        return newLoan;
    }

    @Override
    public Loan getLoan(Long id) {
        return loanRepository.findOne(id);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<RepayPlan> getRepayPlan(Long loanId) {
        return repayPlanRepository.findByLoanId(loanId);
    }

    @Override
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> getLoansBy(Long memberId) {
        return loanRepositoryCustom.findEffectiveLoansBy(memberId);
    }

    @Override
    public void offset(Long memberId, Long loanId) {
        Account account = accountRepository.findByMemberId(memberId);
        List<RepayPlan> plans = repayPlanRepository.findByLoanIdAndMemberIdAndDueDateLessThan(loanId, memberId);
        for (RepayPlan plan : plans) {
            if (plan.getDueTotalAmt() > account.getDebit_amt()) return;
            plan.setPaidPrincipal(plan.getDuePrincipal());
            plan.setPaidInterest(plan.getDueInterest());
            plan.setPaidOverDueInterest(plan.getOverDue_Interest());
            plan.getLoan().setCurDelq(0);
            plan.getLoan().setStatus(0);
            plan.getLoan().setPaidInterest(plan.getPaidInterest() + plan.getLoan().getPaidInterest());
            plan.getLoan().setPaidPrincipal(plan.getPaidPrincipal() + plan.getLoan().getPaidPrincipal());
            plan.getLoan().setPaidOverDueInt(plan.getLoan().getPaidOverDueInt() + plan.getOverDue_Interest());
            if (plan.isLastTerm()) plan.getLoan().setStatus(9);
            loanRepository.save(plan.getLoan());
            if (plan.getMember().blockCodeChangeIfNeeded()) {
                plan.getMember().setBlockCode(plan.getMember().getBlockCodeAfterRepayment());
                plan.getMember().setStatus(MemberStatusEnum.NORMAL);
                memberRepository.save(plan.getMember());
            }
            updateAccountCrl(account, plan);
            createOffsetByRepayPlan(plan);
        }
    }

    private void createOffsetByRepayPlan(RepayPlan plan) {
        RepayOffset repayOffset = new RepayOffset();
        repayOffset.setAmt(plan.getPaidAmt());
        repayOffset.setTermNo(plan.getTermNo());
        repayOffset.setLoan(plan.getLoan());
        repayOffsetRepository.save(repayOffset);
    }

    private void updateAccountCrl(Account account, RepayPlan plan) {
        Double r = account.getDebit_amt() - plan.getPaidAmt();
        account.setDebit_amt(Math.abs(r) < PRECISION_THRESHOLD ? NumberUtils.DOUBLE_ZERO : r);
        account.setCrlUsed(account.getCrlUsed() - plan.getPaidPrincipal());
        account.setCrlAvl(account.getCrl() - account.getCrlUsed());
        accountRepository.save(account);
    }

    @Override
    public void repay(Long memberId, Double amount) {
        Repay repay = new Repay();
        repay.setAmt(amount);
        repay.setSource(1);
//        repay.isSuccess(2);
        repay.setRepayDate(new Date());
        repay.setMemberId(memberId);
        Account account = accountRepository.findByMemberId(memberId);
        account.setDebit_amt(account.getDebit_amt() + amount);
        rePayRepository.save(repay);
    }

    @Override
    public boolean review(Long loanId, Double payAmt) {
        Loan loan = loanRepository.findOne(loanId);
        loan.getPay().setConfirmDate(new Date());
        loan.getPay().setConfirmID("admin");
        loan.getPay().setConfirm(2);
        loan.getPay().setPayAmt(loan.getAmt());
        loan.setStartDate(loan.getPay().getConfirmDate());
        loan.setAmt(loan.getPay().getPayAmt());
        loan.setPrincipal(loan.getPay().getPayAmt());
        loan.createRepayPlans();
        loan.setStatus(0);
        updateContractBy(loan);
        loanRepository.save(loan);
        sendMessage(loan, loan.getStatus().equals(10) ? bindCreditCardFail : bindCreditCardSuccess);
        return true;
    }

    private void sendMessage(Loan loan, String message) {
        final String wcNo = memberRepository.findOne(loan.getMember().getId()).getWcNo();
        MemberStatusChangeEvent event = new MemberStatusChangeEvent(this, wcNo, message);
        publisher.publishEvent(event);
    }

    @Override
    public boolean transferLoan(Long loanId, String transCode) {
        Loan loan = loanRepository.findOne(loanId);
        loan.getPay().setTransferTime(new Date());
        loan.getPay().setCode(transCode);
        loan.getPay().setErrorMessage("");
        loan.getPay().setConfirm(1);
        loanRepository.save(loan);
        return true;
    }

    @Override
    public boolean handleLoan(Long loanId, Double payAmt, String msg) {
        Loan loan = loanRepository.findOne(loanId);
        loan.getPay().setErrorMessage(msg);
        loan.getPay().setPayAmt(payAmt);
        loan.getPay().setConfirm(9);
        loanRepository.save(loan);
        return true;
    }

    @Override
    public boolean takeBackLoan(Long loanId) {
        Loan loan = loanRepository.findOne(loanId);
        loan.getPay().setErrorMessage("");
        loan.getPay().setConfirm(1);
        loanRepository.save(loan);
        return true;
    }

    @Override
    public boolean cancelLoan(Long loanId) {
        Loan loan = loanRepository.findOne(loanId);
        Long memberId = loan.getApplication().getMember().getId();
        Account account = accountRepository.findByMemberId(memberId);
        account.setCrlUsed(account.getCrlUsed() - loan.getAmt());
        account.setCrlAvl(account.getCrl() - account.getCrlUsed());
        accountRepository.save(account);
        loan.getPay().setConfirm(10);
        loan.setStatus(10);
        CreditCard card = loan.getApplication().getCreditCard();
        card.setIsValid(false);
        creditCardRepository.save(card);
        sendMessage(loan, loan.getStatus().equals(10) ? bindCreditCardFail : bindCreditCardSuccess);
        loanRepository.save(loan);
        return true;
    }

    @Override
    public LoanSummary getLoansSummary(Long memberId) {
        return loanSummaryBuilder.build(getLoansBy(memberId));
    }

    @Override
    public Loan createLoanBy(Application application) {
        Loan loan = new Loan();
        loan.setMember(application.getMember());
        loan.setApplication(application);
        loan.setTerm(application.getApproval().getTerm());
        loan.setApr(application.getApproval().getApr());
        loan.setAmt(application.getApproval().getAmt());
        loan.setPrincipal(loan.getAmt());
        loan.setStartDate(application.getApproval().getCreateTime());
        Loan newLoan = createLoan(loan);
        createContractBy(newLoan);
        return newLoan;
    }

    @Override
    public Double getAmtWithinThisPeriod(Long memberId) {
        List<RepayPlan> plans = repayPlanRepository.findByMemberIdAndDueDateLessThan(memberId);
        if (CollectionUtils.isNotEmpty(plans)) return plans.get(0).getDueAmt() + plans.get(0).getOverDue_Interest();
        return null;
    }

    private void updateBlockCode(Member member, Integer overDueDays) {
        final String blockCode = member.getBlockCodeBy(overDueDays);
        if (!blockCode.equals(member.getBlockCode())) {
            member.setBlockCode(blockCode);
            member.setStatus(MemberStatusEnum.REJECTED);
            memberRepository.save(member);
        }
    }

    @Override
    @Scheduled(cron = "0 0 * * * ?")
    public void updateOverDue() {
        List<RepayPlan> plans = repayPlanRepository.findByDueDate(Day.TODAY.plusDays((-1) * Integer.valueOf(graceDay)));
        for (RepayPlan plan : plans) {
            plan.setOverDueAmt(plan.getDuePrincipal());
            int overDueDays = Day.TODAY.escapeDays(plan.getDueDate());
            plan.setOverDueDay(overDueDays);
            plan.setOverDue_Interest(overDueDays * plan.getOverDueAmt() * Double.valueOf(overDueRating));
            plan.getLoan().setStatus(1);
            plan.getLoan().setMaxDelq(Math.max(plan.getLoan().getMaxDelq(), overDueDays));
            plan.getLoan().setCurDelq(Math.max(plan.getLoan().getCurDelq(), overDueDays));
            updateBlockCode(plan.getLoan().getMember(), plan.getLoan().getCurDelq());
        }
        repayPlanRepository.save(plans);
    }

    private Map<Loan, Integer> getLoanNotificationCountMap(List<Loan> loans) {
        Map<Loan, Integer> result = new HashMap<>();
        for (Loan loan : loans) {
            boolean hasTheSameMemberAndStartDate = false;
            for (Map.Entry<Loan, Integer> entry : result.entrySet()) {
                if (entry.getKey().withTheSameMemberAndStartDate(loan)) {
                    hasTheSameMemberAndStartDate = true;
                    result.put(entry.getKey(), result.get(entry.getKey()) + 1);
                }
            }
            if (!hasTheSameMemberAndStartDate) result.put(loan, 1);
        }
        return result;
    }

    @Override
    @Scheduled(cron = "0 0 12 * * ?")
    public void sendRepaymentNotification() {
        List<Loan> loans = loanRepository.findByStatusIn(Arrays.asList(0, 1, 2));
        for (Map.Entry<Loan, Integer> entry : getLoanNotificationCountMap(loans).entrySet()) {
            final String ns = repaymentNotification.getNotificationMessage(entry.getKey(), entry.getValue());
            if (StringUtils.isEmpty(ns)) continue;
            sendMessage(entry.getKey(), ns);
        }
    }

    @Override
    public Contract getContract(String appNo) {
        return contractRepository.findByAppNo(appNo);
    }

    @Override
    public String getPaymentGateway(Long memberId, Long loanId, Double amount) {
        Member member = memberRepository.findOne(memberId);
        final String orderId = DateTime.now().toString(Constants.LONG_DATE_PATTERN);
        final String payAmount = String.valueOf(new Double(amount * 100).longValue());
        String gatewayParamPattern = StringUtils.substringBetween(paymentGatewayUrlPattern, "?", "&signMsg");
        String signMessage = new PkiPairUtil().signMsg(MessageFormat.format(gatewayParamPattern, member.getWcNo(),
                member.getEmail(), memberId, orderId, payAmount, loanId));
        try {
            return MessageFormat.format(paymentGatewayUrlPattern, member.getWcNo(), member.getEmail(), memberId,
                    orderId, payAmount, loanId, URLEncoder.encode(signMessage, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("can not talk with 99bill gateway.");
        }
    }

    @Override
    public void addPaymentList(PaymentList paymentList) {
        paymentListRepository.save(paymentList);
    }

    private Contract createContractBy(Loan loan) {
        if (contractRepository.findByAppNo(loan.getApplicationNo()) != null)
            throw new IllegalStateException("The Contract has been created by " + loan.getApplicationNo());
        Contract contract = new Contract(loan, getInstance().getLender(),
                getInstance().getLenderEmail(), getInstance().getLenderMobile());
        return contractRepository.save(contract);
    }

    private Contract updateContractBy(Loan loan) {
        Contract contract = contractRepository.findByAppNo(loan.getApplicationNo());
        if (contract == null) throw new IllegalStateException("The Contract is null with " + loan.getApplicationNo());
        contract.setAmount(loan.getPrincipal());
        contract.setStartDate(loan.getStartDate());
        contract.setEndDate(new Day(loan.getStartDate()).plusMonths(loan.getTerm()));
        contract.setRepaymentDay(new DateTime(loan.getStartDate()).getDayOfMonth());
        contract.setSigningDate(loan.getStartDate());
        return contractRepository.save(contract);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public static void main(String[] args) {
        Double amount = 340.02d;
        System.out.println(String.valueOf(new Double(amount * 100).longValue()));
    }
}