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

    public Object findApplicationBy(final String appNo) {
        final String query = "select appl.*, tv.DECISION, tv.TV_MEM_ANS_1, tv.TV_MEM_ANS_2, tv.TV_QUES_1, tv.TV_QUES_2, tv.TYPE, aval.CLASS, aval.DECISION as approvedDecision, aval.OPINION,aval.PROFILE, aval.REASON_1, aval.REASON_2, aval.REASON_3, aval.REPAY_TYPE as approvedRepayType, aval.SUG_CRL,\n" +
                "score.PBOC_BACK_TIME,score.PBOC_TIME,score.RATING, score.RISK_REMIND,score.SCORE,member.BLOCK_CODE, member.EDUCATION,member.INDUSTRY,member.MOBILE,member.NAME, member.ORG_FLAG, member.POINTS,member.PRE_CRL, member.STATUS as memberStatus,member.PRE_RATING as memberPreRating,member.PRE_SCORE as memberPreScore\n" +
                "from APPL appl, APPL_TV tv, APPROVAL aval, A_SCORE score, MEMBER member, ID_CARD idcard\n" +
                " where appl.APPL_NO = tv.APPL_NO and appl.APPL_NO = aval.APPL_NO and appl.APPL_NO = score.APPL_NO and appl.MEMBER_ID = member.ID and member.ID = idcard.MEMBER_ID and appl.APPL_NO = ?";
        return em.createNativeQuery(query).setParameter(1, appNo).getSingleResult();
    }

}
