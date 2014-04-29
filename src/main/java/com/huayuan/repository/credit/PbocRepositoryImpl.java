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
        return em.createNativeQuery("SELECT pb.id as id, pb.certNo as certNo, pb.name as name, pb.create_Time as createTime,pb.keyiner as keyiner, pb.status as status FROM Pboc pb where " + query, "pbocSummary").getResultList();
    }
}