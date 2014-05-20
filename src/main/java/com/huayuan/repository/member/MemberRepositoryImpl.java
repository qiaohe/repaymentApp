package com.huayuan.repository.member;

import com.huayuan.common.util.JpaSqlResultMapper;
import com.huayuan.web.dto.MemberLoansDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Richard Xue on 14-5-20.
 */
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<MemberLoansDto> getLoansBy(String query) {
        String q = em.createNamedQuery("Member.findMemberLoans")
                .unwrap(org.hibernate.Query.class)
                .getQueryString();
        return JpaSqlResultMapper.list(em.createNativeQuery(q.replace(":q", "WHERE " + query)), MemberLoansDto.class);
    }
}
