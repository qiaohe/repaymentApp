package com.huayuan.service;

import com.huayuan.domain.accounting.Account;
import com.huayuan.domain.accounting.Loan;
import com.huayuan.domain.accounting.LoanSummary;
import com.huayuan.domain.accounting.RepayPlan;

import java.util.List;

/**
 * Created by dell on 14-4-8.
 */
public interface AccountService {
    public Account getAccount(Long accountId);

    public Account createAccount(Account account);

    public Loan createLoan(Loan loan);

    public Loan getLoan(Long loanId);

    public List<Account> getAccounts();

    public List<RepayPlan> getRepayPlan(Long accountId);

    public List<Loan> getLoans();

    public List<Loan> getLoansBy(Long memberId);

    public void rePay(Long memberId, Double amount);

    public boolean review(Long loanId);

    public LoanSummary getLoansSummary(Long memberId);

}
