package com.huayuan.repository.account;

import com.huayuan.domain.member.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-7-23.
 */
public interface ContractRepository extends JpaRepository<Contract, Long> {
    public Contract findByAppNo(String appNo);
}
