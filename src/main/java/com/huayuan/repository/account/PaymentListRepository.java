package com.huayuan.repository.account;

import com.huayuan.domain.accounting.PaymentList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dell on 14-8-14.
 */
public interface PaymentListRepository extends JpaRepository<PaymentList, Long> {
    public List<PaymentList> findByBankDealId(String bankDealId);
}
