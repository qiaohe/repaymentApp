package com.huayuan.repository.account;

import com.huayuan.web.dto.LoanCommonDto;

import java.util.List;

/**
 * Created by Administrator on 14-5-21.
 */
public interface AccountRepositoryCustom {
    public List<LoanCommonDto> findLoanTransDetails(String query);
}
