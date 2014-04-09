package com.huayuan.repository.member;

import com.huayuan.domain.member.PreCredit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by dell on 14-4-9.
 */
public class PreCreditRepositoryImpl implements PreCreditRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void execute(PreCredit preCredit) {
        Query query = em.createNativeQuery("{call PRECREDIT_CRL_BILL(?)}")
                .setParameter(1, preCredit.getId());
        query.executeUpdate();
    }
}

