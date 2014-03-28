package com.huayuan.service;

import com.huayuan.common.exception.MemberNotFoundException;
import com.huayuan.domain.*;
import com.huayuan.domain.crawler.BillCrawler;
import com.huayuan.domain.recognizer.IdCardInfo;
import com.huayuan.repository.IdCardRepository;
import com.huayuan.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * Created by dell on 14-3-19.
 */
@Service(value = "memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;
    private MessageSource messageSource;

    @Resource
    private IdCardRepository idCardRepository;

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
    public void removeIdCard(IdCard idCard) {
        idCardRepository.delete(idCard);
    }

    @Override
    public Iterable<Member> getMembers() {
        return memberRepository.findAll();
    }

    @Override
    public void addIdCard(Member member, IdCardInfo idCardInfo) {
        IdCard ic = idCardInfo.getIdCard();
        ic.setMember(member);
        member.setIdCard(ic);
        memberRepository.save(member);
    }

    @Override
    public void addCreditCard(Member member, CreditCard creditCard) {
        memberRepository.save(member);
    }

    @Override
    public void addBillMailBox(Member member, BillMailbox billMailbox) {
        BillCrawler crawler = new BillCrawler();
        addBill(member, crawler.crawl(billMailbox));
    }

    @Override
    public void addBill(Member member, Bill bill) {
        memberRepository.save(member);
    }

    @Override
    public void addPreCredit(Member member, PreCredit credit) {
        memberRepository.save(member);
    }
}
