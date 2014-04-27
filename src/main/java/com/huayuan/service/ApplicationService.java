package com.huayuan.service;

import com.huayuan.domain.loanapplication.AScore;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.Approval;
import com.huayuan.domain.loanapplication.TelephoneVerification;
import com.huayuan.domain.member.CreditCard;

/**
 * Created by dell on 14-3-25.
 */
public interface ApplicationService {
    public Application getApplication(String appNo);

    public Application getApplicationBy(Long memberId);

    public Application applyLoan(Application application);

    public void approve(Approval approval);

    public void telephoneVerification(TelephoneVerification tv);

    public void aStore(AScore aScore);

    public Application bindCreditCard(Application application, String creditCArdNo);

}
