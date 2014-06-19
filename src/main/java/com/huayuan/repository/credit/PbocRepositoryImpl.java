package com.huayuan.repository.credit;

import com.huayuan.common.CommonDef;
import com.huayuan.common.util.JpaSqlResultMapper;
import com.huayuan.domain.credit.PbocSummary;
import com.huayuan.web.dto.PbocOutDto;
import org.apache.commons.lang.StringUtils;

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

    @Override
    public List<PbocOutDto> searchPbocOutList(String query) {
        String q = em.createNamedQuery("PBOC.searchPbocOutList")
                .unwrap(org.hibernate.Query.class)
                .getQueryString();
        q = StringUtils.isNotEmpty(query) ? q.replace(":q","where "+query) : q.replace(":q","");
        return JpaSqlResultMapper.list(em.createNativeQuery(q), PbocOutDto.class);
    }
}