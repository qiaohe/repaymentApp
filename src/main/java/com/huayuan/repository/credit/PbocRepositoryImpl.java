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
    public List<PbocSummary> findByStatus() {
//        return em.createNativeQuery("SELECT pb.id, pb.certNo, pb.name,pb.createTime,\n" +
//                "            pb.keyiner, pb.status FROM Pboc pb WHERE pb.status in(1,2,3) and pb.flag = 1 and\n" +
//                "            pb.risk = 0", PbocSummary.class).getResultList();

        return null;
    }
}
