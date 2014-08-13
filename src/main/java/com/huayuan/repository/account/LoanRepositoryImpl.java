package com.huayuan.repository.account;

import com.huayuan.domain.accounting.Loan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Administrator on 14-6-11.
 */
public class LoanRepositoryImpl implements LoanRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Loan> findEffectiveLoansBy(Long memberId) {
        return em.createNamedQuery("Loan.findEffectiveLoansBy").setParameter(1,memberId).getResultList();
    }

}
