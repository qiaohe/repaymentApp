package com.huayuan.repository.applicationloan;

import com.huayuan.domain.credit.ApplicationSummary;
import com.huayuan.domain.loanapplication.Application;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by dell on 14-4-13.
 */
@Transactional
public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void execute(Application application) {
        Query query = em.createNativeQuery("{call PROC_BORROW_OLD(?)}")
                .setParameter(1, application.getApplicationNo());
        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ApplicationSummary> findApplicationSummaries() {
        return em.createNamedQuery("Application.findApplicationSummaries").getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ApplicationSummary> findApplicationSummaries(final String q) {
        return em.createQuery("select NEW com.huayuan.domain.credit.ApplicationSummary(appl.applicationNo, idcard.name, idcard.idNo,\n" +
                "appl.existingFlag, appl.applyTime, ma.city, appl.status, appl.createTime,apv.creditor) from Application appl , Approval apv,\n" +
                "IdCard idcard, Member mem, ValueMobileArea ma where appl.member.id = mem.id and appl.applicationNo = apv.application.applicationNo and mem.id = idcard.member.id\n " +
                "and ma.sevenPrefix = substring(mem.mobile, 0 , 8) and " + q).getResultList();
    }


    public Object findApplicationBy(final String appNo) {
        return null;
    }

}
