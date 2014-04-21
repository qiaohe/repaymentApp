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


}
