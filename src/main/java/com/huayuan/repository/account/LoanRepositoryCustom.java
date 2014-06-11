package com.huayuan.repository.account;

import com.huayuan.domain.accounting.Loan;

import java.util.List;

/**
 * Created by Richard Xue on 14-6-11.
 */
public interface LoanRepositoryCustom {
    public List<Loan> findEffectiveLoansBy(Long memberId);
}
