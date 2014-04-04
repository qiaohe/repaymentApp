package com.huayuan.service;

import com.huayuan.common.exception.MemberNotFoundException;
import com.huayuan.domain.*;

/**
 * Created by Johnson on 3/19/14.
 */
public interface MemberService {

    public void register(Member member);

    public void update(Member member);

    public void updateIdCard(Member member, IdCard idCard);

    public Member find(Long id) throws MemberNotFoundException;

    public void remove(Member member);

    public void removeIdCard(IdCard idCard);

    public Iterable<Member> getMembers();

    public void addIdCard(Member member, IdCard idCard);

    public void addCreditCard(Member member, CreditCard creditCard);

    public void addBillMailBox(Member member, BillMailbox billMailbox);

    public void addBill(Member member, CreditCardBill creditCardBill);

    public void addPreCredit(Member member, PreCredit credit);
}
