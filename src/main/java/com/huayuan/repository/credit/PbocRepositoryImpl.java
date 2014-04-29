package com.huayuan.repository.credit;

import com.huayuan.domain.credit.Pboc;
import com.huayuan.domain.credit.PbocSummary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by dell on 14-4-28.
 */
public class PbocRepositoryImpl implements PbocRepositoryCustom {
    private static final String SQL_RESULT_MAPPING = "pbocSummary";
    private static final String SEARCH_SQL = "SELECT pb.id as id, pb.certNo as certNo, pb.name as name, pb.create_Time as createTime,pb.keyiner as keyiner, pb.status as status FROM Pboc pb where ";
    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<PbocSummary> findByStatus() {
        return em.createNamedQuery("Pboc.findByStatusWithSummary").getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Pboc> search(String query) {
        return em.createNativeQuery(SEARCH_SQL + query, SQL_RESULT_MAPPING).setParameter(1, query).getResultList();
    }
}