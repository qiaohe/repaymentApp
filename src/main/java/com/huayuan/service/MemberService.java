package com.huayuan.service;

import com.huayuan.common.exception.MemberNotFoundException;
import com.huayuan.domain.crawler.BillEmail;
import com.huayuan.domain.member.*;
import com.huayuan.web.dto.MemberDto;

import java.util.List;

/**
 * Created by Johnson on 3/19/14.
 */
public interface MemberService {

    public PreCredit testCreditLimit(MemberDto memberDto);

    public Member update(Member member);

    public Member createMemberFromWeChat(Member member);

    public Member findMemberBy(String weChatNo);

    public void updateIdCard(Member member, IdCard idCard);

    public Member find(Long id) throws MemberNotFoundException;

    public void remove(Member member);

    public Iterable<Member> getMembers();

    public IdCard addIdCard(Member member, IdCard idCard);

    public CreditCard addCreditCard(Member member, String creditNo);

    public void removeCreditCard(Member member, String creditNo);

    public CreditCardBill addBill(Member member, BillEmail billEmail);

    public List<CreditCard> getCreditCards(Long memberId);

    public Double getCrl(Long id);

    public Double getAvlCrl(Long id);

    public Integer getStatus(Long memberId);

    public Integer getApplicationStatus(Long memberId);

    public String getRating(Long memberId);
}
