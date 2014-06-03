package com.huayuan.repository.account;

import com.huayuan.domain.accounting.RepayOffset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dell on 14-5-21.
 */
public interface RepayOffsetRepository extends JpaRepository<RepayOffset, Long> {
    public List<RepayOffset> findByLoan_IdOrderByTermNoDesc(Long loanId);
}
