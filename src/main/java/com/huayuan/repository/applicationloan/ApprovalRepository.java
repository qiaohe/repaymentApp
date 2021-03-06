package com.huayuan.repository.applicationloan;

import com.huayuan.domain.loanapplication.Approval;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Johnson on 4/7/14.
 */
public interface ApprovalRepository extends CrudRepository<Approval, Long> {
    public Approval findByApplication_ApplicationNo(String appNo);
}
