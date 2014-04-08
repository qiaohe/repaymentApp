package com.huayuan.service;

        import com.huayuan.domain.accounting.Account;
        import com.huayuan.domain.accounting.Loan;
        import com.huayuan.domain.accounting.RepayPlan;
        import com.huayuan.domain.member.Member;

        import java.util.List;

/**
 * Created by dell on 14-4-8.
 */
public interface AccountService {
    public Account getAccount(Long accountId);

    public Account createAccount(Account account);

    public Loan createLoan(Loan loan);

    public List<Account> getAccounts();

    public List<RepayPlan> getRepayPlan(Long accountId);

    public List<Loan> getLoans();

    public List<Loan> getLoansBy(Member member);
}
