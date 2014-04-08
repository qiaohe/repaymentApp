package com.huayuan.service;

import com.huayuan.common.exception.MemberNotFoundException;
import com.huayuan.domain.crawler.BillCrawler;
import com.huayuan.domain.crawler.BillEmail;
import com.huayuan.domain.member.*;
import com.huayuan.repository.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 14-3-19.
 */
@Service(value = "memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Inject
    private MemberRepository memberRepository;
    @Inject
    private ValueBinRepository valueBinRepository;
    @Inject
    private IdCardRepository idCardRepository;
    @Inject
    private CreditCardRepository creditCardRepository;
    @Inject
    private CreditCardBillRepository creditCardBillRepository;
    @Inject
    private PreCreditRepository preCreditRepository;

    @Override
    public void register(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void update(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void updateIdCard(Member member, IdCard idCard) {
        IdCard ic = idCardRepository.findOne(member.getIdCard().getId());
        ic.setIssuer(idCard.getIssuer());
        ic.setValidFrom(idCard.getValidFrom());
        ic.setValidThru(idCard.getValidThru());
        idCardRepository.save(ic);
    }

    @Override
    public Member find(Long id) {
        Member result = memberRepository.findOne(id);
        if (result == null) throw new MemberNotFoundException(id);
        return result;
    }

    @Override
    public void remove(Member member) {
        memberRepository.delete(member);
    }

    @Override
    public Iterable<Member> getMembers() {
        return memberRepository.findAll();
    }

    @Override
    public void addIdCard(Member member, IdCard idCard) {
        if (member.getIdCard() != null) {
            idCardRepository.delete(member.getIdCard());
        }
        idCard.setMember(member);
        idCardRepository.save(idCard);
    }

    @Override
    public void addCreditCard(Member member, String creditCardNo) {
        removeCreditCard(member, creditCardNo);
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNo(creditCardNo);
        creditCard.setBank(valueBinRepository.findByBinNo(creditCard.getBinCode()).getBankNo());
        creditCard.setMember(member);
        creditCardRepository.save(creditCard);
    }

    @Override
    public void removeCreditCard(Member member, String creditNo) {
        List<CreditCard> cards = creditCardRepository.findByCardNo(creditNo);
        if (cards != null) {
            creditCardRepository.delete(cards.get(0));
        }
    }

    @Override
    public void updateBillEmail(Member member, String billEmail, String password) {
        BillCrawler crawler = new BillCrawler();
        addBill(member, crawler.crawl(new BillEmail(billEmail, password, "")));
    }

    @Override
    public void addBill(Member member, CreditCardBill creditCardBill) {
        creditCardBill.setMember(member);
        creditCardBillRepository.save(creditCardBill);
    }

    @Override
    public void addPreCredit(Member member, PreCredit credit) {
        credit.setMember(member);
        preCreditRepository.save(credit);
    }
}
