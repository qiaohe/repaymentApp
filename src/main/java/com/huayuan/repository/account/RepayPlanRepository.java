package com.huayuan.repository.account;

import com.huayuan.domain.accounting.RepayPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by dell on 14-4-8.
 */
public interface RepayPlanRepository extends JpaRepository<RepayPlan, Long> {
    public List<RepayPlan> findByLoanId(Long loanId);

    public List<RepayPlan> findByMemberId(Long memberId);
}
