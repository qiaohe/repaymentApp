package com.huayuan.repository.member;

import com.huayuan.domain.member.IdCard;
import com.huayuan.domain.member.PreCredit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by dell on 14-4-9.
 */
public interface PreCreditRepositoryCustom {
    public Integer execute(PreCredit preCredit);
}
