package com.huayuan.service;

import com.huayuan.common.exception.MemberNotFoundException;
import com.huayuan.domain.accounting.Account;
import com.huayuan.domain.crawler.BillCrawler;
import com.huayuan.domain.crawler.BillEmail;
import com.huayuan.domain.loanapplication.CreditResult;
import com.huayuan.domain.member.*;
import com.huayuan.repository.ValueBinRepository;
import com.huayuan.repository.account.AccountRepository;
import com.huayuan.repository.credit.CreditResultRepository;
import com.huayuan.repository.member.*;
import com.huayuan.web.dto.MemberDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private CreditResultRepository creditResultRepository;


    @Override
    public Integer testCreditLimit(MemberDto memberDto) {
        Member member = find(memberDto.getMemberId());
        member.setEducation(memberDto.getEducation());
        member.setIndustry(memberDto.getIndustry());
        member.setEmail(member.getEmail());
        member = update(member);
        CreditCard creditCard = addCreditCard(member, memberDto.getCreditCarNo());
        PreCredit pc = new PreCredit();
        pc.setMember(creditCard.getMember());
        pc.setIdCard(pc.getMember().getIdCard());
        pc.setCreditCard(creditCard);
        pc = preCreditRepository.save(pc);
        return preCreditRepository.execute(pc);
    }

    @Override
    public Member update(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member createMemberFromWeChat(Member member) {
        Member mb = memberRepository.findByWcNo(member.getWcNo());
        if (mb == null) return update(member);
        return mb;
    }

    @Override
    public Member findMemberBy(String weChatNo) {
        return memberRepository.findByWcNo(weChatNo);
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
    public IdCard addIdCard(Member member, IdCard idCard) {
        if (member.getIdCard() != null) {
            return member.getIdCard();
        }
        idCard.setMember(member);
        IdCard card = idCardRepository.save(idCard);
        idCardRepository.execute(card);
        return idCardRepository.findOne(card.getId());
    }

    @Override
    public CreditCard addCreditCard(Member member, String creditCardNo) {
        List<CreditCard> cards = creditCardRepository.findByCardNo(creditCardNo);
        if (!CollectionUtils.isEmpty(cards))
            return cards.get(0);
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNo(creditCardNo);
        creditCard.setBank(valueBinRepository.findByBinNo(creditCard.getBinCode()).getBankNo());
        creditCard.setMember(member);
        return creditCardRepository.save(creditCard);
    }

    @Override
    public void removeCreditCard(Member member, String creditNo) {
        List<CreditCard> cards = creditCardRepository.findByCardNo(creditNo);
        if (!CollectionUtils.isEmpty(cards)) {
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
    public List<CreditCard> getCreditCards(Long memberId) {
        return creditCardRepository.findByMemberId(memberId);
    }

    @Override
    public Integer getCrl(Long id) {
        Account account = accountRepository.findByMemberId(id);
        if (account != null) return account.getCrl();
        Member member = find(id);
        return member.getPreCrl();
    }

    @Override
    public Integer getStatus(Long id) {
        Member member = memberRepository.findOne(id);
        if (member.getStatus().equals(MemberStatusEnum.REJECTED)) return 3;
        CreditResult creditResult = creditResultRepository.findByMemberId(id);
        if ((creditResult != null) && creditResult.getLastDecision().equals("D")) return 1;
        if ((creditResult != null) && creditResult.getLastDecision().equals("A")) return 2;
        return 0;
    }
}
