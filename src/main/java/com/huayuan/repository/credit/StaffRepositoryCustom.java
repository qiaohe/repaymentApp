package com.huayuan.repository.credit;

import com.huayuan.domain.loanapplication.Staff;

/**
 * Created by Johnson on 4/7/14.
 */
public interface StaffRepositoryCustom{
    public Staff findByPassword(String staffId,String password);
}
