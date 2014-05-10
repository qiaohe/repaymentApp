package com.huayuan.service;

import com.huayuan.domain.credit.ApplicationSummary;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.Approval;

import java.util.List;

/**
 * Created by dell on 14-3-25.
 */
public interface ApplicationService {
    public Application getApplication(String appNo);

    public Application getApplicationBy(Long memberId);

    public Application applyLoan(Application application);

    public void approve(Approval approval);

    public Application bindCreditCard(Long memberId, String creditCArdNo);

    public List<ApplicationSummary> getApplicationSummaries();

    public List<ApplicationSummary> getApplicationSummaries(String query);
}
