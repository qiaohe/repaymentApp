package com.huayuan.repository.member;


import com.huayuan.domain.member.IdCard;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Johnson He on 14-3-26.
 */
public interface IdCardRepository extends CrudRepository<IdCard, Long>, IdCardRepositoryCustom {
}
