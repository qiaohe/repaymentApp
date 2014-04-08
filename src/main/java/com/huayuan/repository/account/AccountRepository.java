package com.huayuan.repository.account;

import com.huayuan.domain.accounting.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-4-8.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
