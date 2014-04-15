package com.huayuan.repository.applicationloan;

import com.huayuan.domain.loanapplication.Application;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
}
