package com.huayuan.repository.credit;

import com.huayuan.domain.loanapplication.Staff;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Johnson on 4/7/14.
 */
public class StaffRepositoryImpl implements StaffRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Staff findByPassword(String staffId, String password) {
        List staffList = em.createNamedQuery("Staff.findByPassword").setParameter(1,staffId).setParameter(2,password).getResultList();
        if(staffList == null || staffList.isEmpty()) {
            return null;
        }
        return (Staff) staffList.get(0);
    }
}
