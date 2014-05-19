package com.huayuan.repository.applicationloan;

import com.huayuan.domain.credit.ApplicationSummary;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.member.MemberProfile;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        String sql = "select distinct appl.APPL_NO,idcard.NAME,idcard.ID_NO,appl.EXISTING_FLAG,appl.APPLY_TIME,ma.CITY,appl.STATUS,apv.CREDITOR,appl.CREATE_TIME\n" +
                " from APPL appl\n" +
                " inner join APPROVAL apv on apv.APPL_NO = appl.APPL_NO\n" +
                " inner join MEMBER mem on mem.ID = appl.MEMBER_ID\n" +
                " inner join ID_CARD idcard on idcard.MEMBER_ID = mem.ID\n" +
                " inner join VALUE_MOBILE_AREA ma on ma.N1_7 = SUBSTRING(mem.MOBILE,0,8)\n" +
                " left join APPL_TV appltv on appltv.APPL_NO = appl.APPL_NO\n" +
                " left join (select appl.APPL_NO,count(teletv.ID) as NUM from APPL appl,tele_tv teletv where appl.APPL_NO = teletv.APPL_NO group by appl.APPL_NO) as t1 on t1.APPL_NO = appl.APPL_NO\n" +
                " where " + q;
        List<Object> resultList = em.createNativeQuery(sql).getResultList();

        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        List<ApplicationSummary> applicationSummaryList = new ArrayList<ApplicationSummary>();
        ApplicationSummary applicationSummary = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Object aResultList : resultList) {
            Object[] objs = (Object[]) aResultList;
            applicationSummary = new ApplicationSummary();
            applicationSummary.setAppNo(objs[0].toString());
            applicationSummary.setName(objs[1].toString());
            applicationSummary.setIdCardNo(objs[2].toString());
            applicationSummary.setExistingFlag(Integer.valueOf(objs[3].toString()));
            applicationSummary.setMobileCity(objs[5].toString());
            applicationSummary.setStatus(Integer.valueOf(objs[6].toString()));
            applicationSummary.setCreditor(objs[7].toString());
            try {
                Date appDate = simpleDateFormat.parse(objs[4].toString());
                applicationSummary.setApplyDate(appDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Date createDate = simpleDateFormat.parse(objs[8].toString());
                applicationSummary.setCreateDate(createDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            applicationSummaryList.add(applicationSummary);
        }
        return applicationSummaryList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MemberProfile.Application> findApplicationsProfile(Long memberId) {
        return em.createNamedQuery("Application.findByMemberIdWithProfile").setParameter(1, memberId).getResultList();
    }
}
