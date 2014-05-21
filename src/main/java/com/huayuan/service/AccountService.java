package com.huayuan.service;

import com.huayuan.domain.accounting.Account;
import com.huayuan.domain.accounting.Loan;
import com.huayuan.domain.accounting.LoanSummary;
import com.huayuan.domain.accounting.RepayPlan;
import com.huayuan.domain.loanapplication.Application;

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

    public List<RepayPlan> getRepayPlan(Long loanId);

    public List<Loan> getLoans();

    public List<Loan> getLoansBy(Long memberId);

    public void offset(Long memberId);

    public void repay(Long memberId, Double amount);

    public boolean review(Long loanId);

    public boolean transferLoan(Long loanId,String transCode);

    public LoanSummary getLoansSummary(Long memberId);

    public Loan createLoanBy(Application application);

    public Double getAmtWithinThisPeriod(Long memberId);

    public void updateOverDue();
}
