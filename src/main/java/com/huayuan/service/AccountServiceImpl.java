package com.huayuan.service;

import com.huayuan.domain.accounting.Account;
import com.huayuan.domain.accounting.Loan;
import com.huayuan.domain.accounting.Repay;
import com.huayuan.domain.accounting.RepayPlan;
import com.huayuan.repository.account.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
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
        return loanRepository.findByMemberId(memberId);
    }

    @Override
    public void rePay(Long memberId, Double amount) {
        Repay repay = new Repay();
        repay.setAmt(amount);
        repay.setSource(1);
        repay.setRepayDate(new Date());
        repay.setRefNo("");
        repay.setMemberId(memberId);
        rePayRepository.save(repay);
        Account account = accountRepository.findByMemberId(memberId);
        account.setDebit_amt(account.getDebit_amt() + amount);
        List<RepayPlan> plans = repayPlanRepository.findByMemberId(memberId);
        for (RepayPlan plan : plans) {
            if (plan.getDueAmt() + plan.getOverDueAmt() > account.getDebit_amt()) return;
            plan.setPaidPrincipal(plan.getDuePrincipal());
            plan.setPaidInterest(plan.getDueInterest());
            account.setDebit_amt(account.getDebit_amt() - plan.getPaidInterest() - plan.getPaidPrincipal() - plan.getOverDue_Interest());
        }
    }

    @Override
    public boolean review(Long loadId) {
        Loan loan = loanRepository.findOne(loadId);
        loan.getPay().setConfirmDate(new Date());
        loan.getPay().setTransferTime(new Date());
        loan.getPay().setConfirm(1);
        loan.getPay().setCode("T1000001");
        loan.setStartDate(loan.getPay().getConfirmDate());
        loan.setAmt(loan.getPay().getPayAmt());
        loan.setPrincipal(loan.getPay().getPayAmt());
        List<RepayPlan> plans = loan.createRepayPlan();
        Double sumInterest = 0d;
        for (RepayPlan plan : plans) {
            sumInterest += plan.getDueInterest();
        }
        loan.setInterest(sumInterest);
        loanRepository.save(loan);
        repayPlanRepository.save(plans);
        return true;
    }


    public static void main(String[] args) {

        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:\\development\\working\\repaymentApp\\repaymentApp\\src\\main\\resources\\applicationContext.xml");
        AccountService accountService = applicationContext.getBean("accountService", AccountService.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        ApplicationService applicationService = applicationContext.getBean("applicationService", ApplicationService.class);
//        Loan loan = new Loan();
//        loan.setMember(memberService.find(1l));
//        loan.setApplication(applicationService.getApplication("2014042110000000"));
//        loan.setTerm(3);
//        loan.setApr(0.15d);
//        loan.setAmt(102d);
//        loan.setStartDate(new Date());
        System.out.println(accountService.review(1l));
    }
}
