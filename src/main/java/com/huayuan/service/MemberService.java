package com.huayuan.service;

import com.huayuan.common.exception.MemberNotFoundException;
import com.huayuan.domain.*;
import com.huayuan.domain.member.*;

/**
 * Created by Johnson on 3/19/14.
 */
public interface MemberService {

    public void register(Member member);

    public void update(Member member);

    public void updateIdCard(Member member, IdCard idCard);

    public Member find(Long id) throws MemberNotFoundException;

    public void remove(Member member);

    public Iterable<Member> getMembers();

    public void addIdCard(Member member, IdCard idCard);

    public void addCreditCard(Member member, String creditNo);

    public void  removeCreditCard(Member member, String creditNo);

    public void updateBillEmail(Member member, String billEmail, String password);

    public void addBill(Member member, CreditCardBill creditCardBill);

    public void addPreCredit(Member member, PreCredit credit);
}
