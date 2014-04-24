package com.huayuan.service;

import com.huayuan.domain.accounting.Account;
import com.huayuan.domain.accounting.Loan;
import com.huayuan.domain.accounting.RepayPlan;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.member.Member;
import com.huayuan.repository.account.AccountRepository;
import com.huayuan.repository.account.LoanRepository;
import com.huayuan.repository.account.RepayPlanRepository;
import com.huayuan.repository.account.TransferRepository;
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
    private TransferRepository transferRepository;

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
        transferRepository.save(newLoan.createTransfer());
        repayPlanRepository.save(newLoan.createRepayPlan());
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
    public List<RepayPlan> getRepayPlan(Long accountId) {
        return null;
    }

    @Override
    public List<Loan> getLoans() {
        return null;
    }

    @Override
    public List<Loan> getLoansBy(Member member) {
        return null;
    }

    @Override
    public void setOff(Account account, Double amount) {

    }


    public static void main(String[] args) {

        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:\\development\\working\\repaymentApp\\repaymentApp\\src\\main\\resources\\applicationContext.xml");
        AccountService accountService = applicationContext.getBean("accountService", AccountService.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        ApplicationService applicationService = applicationContext.getBean("applicationService", ApplicationService.class);
        Loan loan = new Loan();
        loan.setMember(memberService.find(1l));
        loan.setApplication(applicationService.getApplication("2014042110000000"));
        loan.setTerm(3);
        loan.setApr(0.15d);
        loan.setAmt(102d);
        loan.setStartDate(new Date());
        System.out.println(accountService.createLoan(loan));
    }
}
