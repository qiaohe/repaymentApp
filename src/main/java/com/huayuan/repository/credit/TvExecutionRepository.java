package com.huayuan.repository.credit;

import com.huayuan.domain.credit.TvExecution;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-5-1.
 */
public interface TvExecutionRepository extends JpaRepository<TvExecution, Long> {
    public TvExecution findByApplication_ApplicationNo(String appNo);
}
