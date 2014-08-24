package com.huayuan.repository.credit;

import com.huayuan.domain.loanapplication.Staff;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Johnson on 4/7/14.
 */
public class StaffRepositoryImpl implements StaffRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Staff findByPassword(String staffId, String password) {
//        return em.createNamedQuery("Staff.findByPassword").setParameter(1,staffId).setParameter(2,password).getSingleResult();
        return null;
    }
}
