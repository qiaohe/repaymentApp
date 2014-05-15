package com.huayuan.repository.member;

import com.huayuan.domain.member.IdCard;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by dell on 14-4-9.
 */
public class IdCardRepositoryImpl implements IdCardRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void execute(IdCard idCard) {
        Query query = em.createNativeQuery("{call ADDRESS(?)}")
                .setParameter(1, idCard.getId());
        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<IdCard> findFromPbocOut() {
        return em.createNamedQuery("IdCard.findFromPbocOut", IdCard.class).getResultList();
    }
}
