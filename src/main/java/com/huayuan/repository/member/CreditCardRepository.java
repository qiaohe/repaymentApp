package com.huayuan.repository.member;

import com.huayuan.domain.member.CreditCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Johnson on 4/6/14.
 */
public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {
    public List<CreditCard> findByCardNo(String cardNo);

    public List<CreditCard> findByCardNoAndIsValid(String cardNo, boolean isValid);

    public List<CreditCard> findByMemberId(Long memberId);
}
