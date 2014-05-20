package com.huayuan.repository.member;

import com.huayuan.web.dto.MemberLoansDto;

import java.util.List;

/**
 * Created by Richard Xue on 14-5-20.
 */
public interface MemberRepositoryCustom {
    public List<MemberLoansDto> getLoansBy(String query);
}
