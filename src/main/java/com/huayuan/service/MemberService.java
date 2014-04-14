package com.huayuan.service;

import com.huayuan.common.exception.MemberNotFoundException;
import com.huayuan.domain.member.CreditCard;
import com.huayuan.domain.member.CreditCardBill;
import com.huayuan.domain.member.IdCard;
import com.huayuan.domain.member.Member;
import com.huayuan.web.dto.MemberDto;

import java.util.List;

/**
 * Created by Johnson on 3/19/14.
 */
public interface MemberService {

    public Integer testCreditLimit(MemberDto memberDto);

    public Member update(Member member);

    public void updateIdCard(Member member, IdCard idCard);

    public Member find(Long id) throws MemberNotFoundException;

    public void remove(Member member);

    public Iterable<Member> getMembers();

    public IdCard addIdCard(Member member, IdCard idCard);

    public CreditCard addCreditCard(Member member, String creditNo);

    public void removeCreditCard(Member member, String creditNo);

    public void updateBillEmail(Member member, String billEmail, String password);

    public void addBill(Member member, CreditCardBill creditCardBill);

    public List<CreditCard> getCreditCards(Long memberId);
}
