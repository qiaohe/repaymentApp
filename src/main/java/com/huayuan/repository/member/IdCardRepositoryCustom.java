package com.huayuan.repository.member;

import com.huayuan.domain.member.IdCard;
import com.huayuan.web.dto.MemberLoanSummaryDto;

import java.util.List;

/**
 * Created by dell on 14-4-9.
 */
public interface IdCardRepositoryCustom {
    public void execute(IdCard idCard);

    public List<IdCard> findFromPbocOut();

    public List<MemberLoanSummaryDto> findMembersWithLoanSummary();

    public List<MemberLoanSummaryDto> findMembersWithLoanSummary(String query);
}
