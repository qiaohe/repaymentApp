package com.huayuan.repository.applicationloan;

import com.huayuan.domain.credit.ApplicationSummary;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.member.MemberProfile;

import java.util.List;

/**
 * Created by dell on 14-4-13.
 */
public interface ApplicationRepositoryCustom {
    public void execute(Application application);

    public List<ApplicationSummary> findApplicationSummaries();

    public List<ApplicationSummary> findApplicationSummaries(String q);

    public List<MemberProfile.Application> findApplicationsProfile(Long memberId);
}
