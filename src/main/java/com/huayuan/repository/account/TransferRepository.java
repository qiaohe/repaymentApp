package com.huayuan.repository.account;

import com.huayuan.domain.accounting.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-4-24.
 */
public interface TransferRepository  extends JpaRepository<Pay, Long> {
}
