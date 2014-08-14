package com.huayuan.repository.account;

import com.huayuan.domain.accounting.PaymentList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-8-14.
 */
public interface PaymentListRepository extends JpaRepository<PaymentList, Long> {
}
