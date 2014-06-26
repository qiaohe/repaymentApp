package com.huayuan.repository.member;

import com.huayuan.common.util.JpaSqlResultMapper;
import com.huayuan.domain.member.IdCard;
import com.huayuan.web.dto.MemberLoanSummaryDto;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by dell on 14-4-9.
 */
public class IdCardRepositoryImpl implements IdCardRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void execute(IdCard idCard) {
        Query query = em.createNativeQuery("{call ADDRESS(?)}")
                .setParameter(1, idCard.getId());
        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<IdCard> findFromPbocOut() {
        return em.createNamedQuery("IdCard.findFromPbocOut", IdCard.class).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<IdCard> findByIdNos(String query) {
        return em.createNativeQuery("select * from ID_CARD where ID_NO in ("+query+")",IdCard.class).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MemberLoanSummaryDto> findMembersWithLoanSummary() {
        Query q = em.createNamedQuery("Member.findMembersWithLoanSummary");
        return JpaSqlResultMapper.list(q, MemberLoanSummaryDto.class);
    }

    @Override
    public List<MemberLoanSummaryDto> findMembersWithLoanSummary(String query) {
        String q = em.createNamedQuery("Member.searchMembersWithLoanSummary")
                .unwrap(org.hibernate.Query.class)
                .getQueryString();
        return JpaSqlResultMapper.list(em.createNativeQuery(q.replace(":q", "WHERE " + query).replaceAll("\\[","%").replaceAll("]","%")), MemberLoanSummaryDto.class);
    }
}
