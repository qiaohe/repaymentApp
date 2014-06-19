package com.huayuan.repository.credit;

import com.huayuan.common.CommonDef;
import com.huayuan.domain.credit.PbocOut;
import com.huayuan.domain.credit.PbocSummary;
import com.huayuan.domain.member.IdCard;

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
    public List<PbocSummary> search(Integer curPage,String query) {
        return em.createNativeQuery("SELECT pb.id as id, pb.certNo as certNo, pb.name as name, pb.create_Time as createTime,pb.keyiner as keyiner, pb.risk as risk, pb.status as status FROM Pboc pb where " + query+" order by pb.create_Time ASC").setMaxResults(CommonDef.PER_PAGE).setFirstResult((curPage-1)*CommonDef.PER_PAGE).getResultList();
    }

    public String getIdCardImage(Long id) {
        return em.createNativeQuery("select IMAGE_FRONT from ID_CARD a , Pboc pb where a.ID_NO = pb.certNo and pb.id=?").setParameter(1, id).getSingleResult().toString();
    }
}