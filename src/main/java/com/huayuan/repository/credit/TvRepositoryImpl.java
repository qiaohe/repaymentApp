package com.huayuan.repository.credit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by dell on 14-5-1.
 */
public class TvRepositoryImpl implements TvRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public List<String> findRandomAddress(String city) {
        return em.createNamedQuery("TvQuestionList.Random", String.class).setParameter(1, city) .getResultList();
    }
}
