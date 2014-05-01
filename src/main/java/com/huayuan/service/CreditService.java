package com.huayuan.service;

import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.CreditResult;
import com.huayuan.domain.loanapplication.Staff;

import java.util.List;

/**
 * Created by Johnson on 4/7/14.
 */
public interface CreditService {
    public void addCreditResult(CreditResult creditResult);

    public Iterable<Staff> getStaffs();

    public void registerStaff(Staff staff);

    public void approve(Application application);

    public void telephoneVerification();

}
