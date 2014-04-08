package com.huayuan.repository.account;

import com.huayuan.domain.accounting.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-4-8.
 */
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
