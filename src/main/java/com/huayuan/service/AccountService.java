package com.huayuan.service;

import com.huayuan.domain.accounting.*;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.member.Contract;

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

    public boolean review(Long loanId, Double payAmt);

    public boolean transferLoan(Long loanId, String transCode);

    public boolean handleLoan(Long loanId, Double payAmt, String msg);

    public boolean takeBackLoan(Long loanId);

    public boolean cancelLoan(Long loanId);

    public LoanSummary getLoansSummary(Long memberId);

    public Loan createLoanBy(Application application);

    public Double getAmtWithinThisPeriod(Long memberId);

    public void updateOverDue();

    public void sendRepaymentNotification();

    public Contract getContract(String appNo);

    public String getPaymentGateway(Long memberId, Double amount);

    public void addPaymentList(PaymentList paymentList);
}
