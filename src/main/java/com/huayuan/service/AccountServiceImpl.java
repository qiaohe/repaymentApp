package com.huayuan.service;

import com.huayuan.domain.accounting.Account;
import com.huayuan.domain.accounting.Loan;
import com.huayuan.domain.accounting.RepayPlan;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.member.Member;
import com.huayuan.repository.account.AccountRepository;
import com.huayuan.repository.account.LoanRepository;
import com.huayuan.repository.account.RepayPlanRepository;
import org.omg.CosNaming._NamingContextExtStub;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by dell on 14-4-8.
 */
@Service(value = "accountService")
public class AccountServiceImpl implements AccountService {
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private RepayPlanRepository repayPlanRepository;
    @Inject
    private LoanRepository loanRepository;

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
        return loanRepository.save(loan);
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

    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("E:\\development\\working\\repaymentApp\\repaymentApp\\src\\main\\resources\\applicationContext.xml");
        AccountService accountService = ctx.getBean("accountService", AccountService.class);
        MemberService memberServcie = ctx.getBean("memberService", MemberService.class);
        ApplicationService applicationService = ctx.getBean("applicationService", ApplicationService.class);
        Application application = applicationService.getApplication("2014040601100000001");

        Member member = memberServcie.find(1l);
//
//        Account account = new Account();
//        account.setCrl(1000);
//        account.setCrlAvl(1000);
//        account.setCrlUsed(900);
//        account.setDebit_amt(90000.12);
//        account.setMember(member);
//        accountService.createAccount(account);
        Account account = accountService.getAccount(2l);
        Loan loan = new Loan();
        loan.setMember(member);
        loan.setApplication(application);
        loan.setMaxDelq(100);
        loan.setApr(12.90);
        loan.setPaidInterest(100d);
        accountService.createLoan(loan);
    }

}
