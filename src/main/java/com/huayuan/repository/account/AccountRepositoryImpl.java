package com.huayuan.repository.account;

import com.huayuan.common.util.JpaSqlResultMapper;
import com.huayuan.web.dto.LoanCommonDto;
import com.huayuan.web.dto.MemberLoansDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Richard Xue on 14-5-21.
 */
public class AccountRepositoryImpl implements AccountRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<LoanCommonDto> findLoanTransDetails(String query) {
        String q = em.createNamedQuery("Loan.findLoanTransDetails")
                .unwrap(org.hibernate.Query.class)
                .getQueryString();
        return JpaSqlResultMapper.list(em.createNativeQuery(q.replace(":q", "WHERE " + query)), LoanCommonDto.class);
    }
}
