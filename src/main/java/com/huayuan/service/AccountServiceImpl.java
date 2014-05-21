package com.huayuan.service;

import com.huayuan.common.util.Day;
import com.huayuan.domain.accounting.*;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.member.Member;
import com.huayuan.repository.account.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.LongRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 14-4-8.
 */
@Service(value = "accountService")
@Transactional
public class AccountServiceImpl implements AccountService {
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private RepayPlanRepository repayPlanRepository;
    @Inject
    private LoanRepository loanRepository;
    @Inject
    private PayRepository payRepository;
    @Inject
    private RePayRepository rePayRepository;
    @Inject
    private LoanSummaryBuilder loanSummaryBuilder;
    @Value("${weChat.bindCreditCardSuccess}")
    private String bindCreditCardSuccess;
    @Value("${account.graceDay}")
    private String graceDay;
    @Value("${account.overdueRating}")
    private String overDueRating;
    @Inject
    private RepayOffsetRepository repayOffsetRepository;

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
        return loanRepository.findByMember_Id(memberId);
    }

    public void offset(Long memberId) {
        Account account = accountRepository.findByMemberId(memberId);
        List<RepayPlan> plans = repayPlanRepository.findByMemberId(memberId);
        for (RepayPlan plan : plans) {
            if (plan.getDueAmt() + plan.getOverDue_Interest() > account.getDebit_amt()) return;
            plan.setPaidPrincipal(plan.getDueAmt());
            plan.setPaidInterest(plan.getDueInterest());
            plan.setPaidOverDueInterest(plan.getOverDue_Interest());
            plan.getLoan().setCurDelq(0);
            plan.getLoan().setStatus(0);
            plan.getLoan().setPaidInterest(plan.getPaidInterest() + plan.getLoan().getPaidInterest());
            plan.getLoan().setPaidPrincipal(plan.getPaidPrincipal() + plan.getLoan().getPaidPrincipal());
            plan.getLoan().setPaidOverDueInt(plan.getLoan().getPaidOverDueInt() + plan.getOverDue_Interest());
            account.setDebit_amt(account.getDebit_amt() - plan.getPaidInterest() - plan.getPaidPrincipal() - plan.getOverDue_Interest());
            accountRepository.save(account);
            RepayOffset repayOffset = new RepayOffset();
            repayOffset.setAmt(plan.getPaidInterest() + plan.getPaidPrincipal() + plan.getOverDue_Interest());
            repayOffset.setLoan(plan.getLoan());
            repayOffset.setTermNo(plan.getTermNo());
            repayOffset.setLoan(plan.getLoan());
            repayOffsetRepository.save(repayOffset);
            loanRepository.save(plan.getLoan());
        }
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
    public boolean review(Long loanId) {
        Loan loan = loanRepository.findOne(loanId);
        loan.getPay().setConfirmDate(new Date());
        loan.getPay().setTransferTime(new Date());
        loan.getPay().setConfirm(1);
        loan.getPay().setPayAmt(loan.getAmt());
        loan.setStartDate(loan.getPay().getConfirmDate());
        loan.setAmt(loan.getPay().getPayAmt());
        loan.setPrincipal(loan.getPay().getPayAmt());
        loan.createRepayPlans();
        loan.setStatus(0);
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
        return createLoan(loan);
    }

    @Override
    public Double getAmtWithinThisPeriod(Long memberId) {
        List<RepayPlan> plans = repayPlanRepository.findByMemberId(memberId);
        Double result = 0d;
        for (RepayPlan plan : plans) {
            result += plan.getDueAmt() + plan.getOverDue_Interest();
        }
        return result;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateOverDue() {
        List<RepayPlan> plans = repayPlanRepository.findByDueDate(Day.TODAY.plusDays((-1) * Integer.valueOf(graceDay)));
        for (RepayPlan plan : plans) {
            plan.setOverDueAmt(plan.getDuePrincipal());
            int overDueDays = Day.TODAY.escapeDays(plan.getDueDate());
            plan.setOverDueDay(overDueDays);
            plan.setOverDue_Interest(overDueDays * plan.getOverDueAmt() * Double.valueOf(overDueRating));
            plan.getLoan().setStatus(2);
            plan.getLoan().setMaxDelq(Math.max(plan.getLoan().getMaxDelq(), overDueDays));
            plan.getLoan().setCurDelq(overDueDays);
        }
        repayPlanRepository.save(plans);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
        AccountService accountService = applicationContext.getBean("accountService", AccountService.class);
        accountService.updateOverDue();
    }
}