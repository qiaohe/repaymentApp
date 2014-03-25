package com.huayuan.service;

import com.huayuan.domain.*;
import com.huayuan.domain.crawler.BillCrawler;
import com.huayuan.domain.recognizer.IdCardInfo;
import com.huayuan.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dell on 14-3-19.
 */
@Service(value = "memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void register(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void update(Member member) {

    }

    @Override
    public Member find(Long id) {
        return memberRepository.findOne(id);
    }

    @Override
    public void remove(Member member) {

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
