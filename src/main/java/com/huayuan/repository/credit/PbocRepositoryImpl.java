package com.huayuan.repository.credit;

import com.huayuan.domain.credit.PbocSummary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by dell on 14-4-28.
 */
public class PbocRepositoryImpl implements PbocRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<PbocSummary> findByStatus() {
        return em.createNamedQuery("Pboc.findByStatusWithSummary").getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PbocSummary> search(String query) {
        return em.createNamedQuery("Pboc.searchWithSummary").setParameter("q", query).getResultList();
    }
}