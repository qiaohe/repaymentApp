package com.huayuan.repository.applicationloan;

import com.huayuan.domain.loanapplication.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Johnson on 4/7/14.
 */
public interface ApplicationRepository extends JpaRepository<Application, String>, ApplicationRepositoryCustom {
    public List<Application> findByMemberIdOrderByApplicationNoDesc(Long memberId);

    public List<Application> findByMemberId(Long memberId);

    public Application findByMemberIdAndStatusAndApproval_Decision(Long memberId, Integer status, String decision);

    public long countByApplyTimeGreaterThan(Date applyDate);

}
