package com.huayuan.repository.applicationloan;

import com.huayuan.domain.credit.ApplicationSummary;
import com.huayuan.domain.loanapplication.Application;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 14-4-13.
 */
@Transactional
public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    public static <T> T map(Class<T> type, Object[] tuple) {
        List<Class<?>> tupleTypes = new ArrayList<>();
        for (Object field : tuple) {
            tupleTypes.add(field.getClass());
        }
        try {
            Constructor<T> ctor = type.getConstructor(tupleTypes.toArray(new Class<?>[tuple.length]));
            return ctor.newInstance(tuple);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(Application application) {
        Query query = em.createNativeQuery("{call PROC_BORROW_OLD(?)}")
                .setParameter(1, application.getApplicationNo());
        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ApplicationSummary> findApplicationSummaries() {
        List<ApplicationSummary> result = new ArrayList<>();
        for (Object item : em.createNamedQuery("Application.findApplicationSummaries").getResultList()) {
            result.add(map(ApplicationSummary.class, (Object[]) item));
        }
        return result;
    }

    public Object findApplicationBy(final String appNo) {
        return null;
    }

}
