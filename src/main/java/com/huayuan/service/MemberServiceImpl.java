package com.huayuan.service;

import com.huayuan.common.exception.MemberNotFoundException;
import com.huayuan.domain.accounting.Account;
import com.huayuan.domain.crawler.BillCrawler;
import com.huayuan.domain.crawler.BillEmail;
import com.huayuan.domain.dictionary.Dictionary;
import com.huayuan.domain.dictionary.ValueBin;
import com.huayuan.domain.loanapplication.CreditResult;
import com.huayuan.domain.member.*;
import com.huayuan.repository.DictionaryRepository;
import com.huayuan.repository.ValueBinRepository;
import com.huayuan.repository.account.AccountRepository;
import com.huayuan.repository.credit.CreditResultRepository;
import com.huayuan.repository.member.*;
import com.huayuan.web.dto.MemberDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 14-3-19.
 */
@Service(value = "memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    private static final ConcurrentHashMap<Integer, String> BANK_MAP = new ConcurrentHashMap<>();
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
    @Inject
    private DictionaryRepository dictionaryRepository;


    @PostConstruct
    private void init() {
        List<Dictionary> banks = dictionaryRepository.findByType("BANK");
        for (Dictionary dictionary : banks) {
            BANK_MAP.put(Integer.valueOf(dictionary.getValue()), dictionary.getName());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer testCreditLimit(MemberDto memberDto) {
        Member member = find(memberDto.getMemberId());
        member.setEducation(memberDto.getEducation());
        member.setIndustry(memberDto.getIndustry());
        member.setEmail(member.getEmail());
        member = update(member);
        CreditCard creditCard = addCreditCard(member, memberDto.getCreditCarNo());
        PreCredit pc = new PreCredit();
        pc.setMember(creditCard.getMember());
        pc.setIdCard(member.getIdCard());
        pc.setCreditCard(creditCard);
        if (memberDto.crawlBillIfNeeded()) {
            BillEmail billEmail = new BillEmail(memberDto.getBillEmail(), memberDto.getBillPassword(), BANK_MAP.get(creditCard.getBank()));
            CreditCardBill bill = addBill(member, billEmail);
            pc.setCreditCardBill(bill);
        }
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
        ValueBin valueBin = valueBinRepository.findByBinNo(creditCard.getBinCode());
        creditCard.setBank(valueBin.getBankNo());
        creditCard.setType(valueBin.getCardLevel());
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
    public CreditCardBill addBill(Member member, BillEmail billEmail) {
        BillCrawler crawler = new BillCrawler();
        CreditCardBill bill = crawler.crawl(billEmail);
        bill.setMember(member);
        return creditCardBillRepository.save(bill);
    }


    @Override
    public List<CreditCard> getCreditCards(Long memberId) {
        return creditCardRepository.findByMemberId(memberId);
    }

    @Override
    public Double getCrl(Long id) {
        Member member = find(id);
        if (member.getStatus().equals(MemberStatusEnum.REJECTED)) return 0d;
        Account account = accountRepository.findByMemberId(id);
        if (account != null) return account.getCrl();
        return (double) member.getPreCrl();
    }

    @Override
    public Double getAvlCrl(Long id) {
        Member member = find(id);
        if (member.getStatus().equals(MemberStatusEnum.REJECTED)) return 0d;
        Account account = accountRepository.findByMemberId(id);
        if (account != null) return account.getCrlAvl();
        return (double) member.getPreCrl();
    }

    private boolean repayImmediatelyIfNeeded(Member member, CreditResult creditResult) {
        return (StringUtils.containsAny(member.getBlockCode(), new char[]{'D', 'E', 'F', 'G', 'I'}) ||
                creditResult.getLastReason1().equalsIgnoreCase("D101") ||
                creditResult.getLastReason2().equalsIgnoreCase("D101") ||
                creditResult.getLastReason2().equalsIgnoreCase("D101"));
    }

    @Override
    public Integer getStatus(Long id) {
        CreditResult creditResult = creditResultRepository.findByMemberId(id);
        Member member = memberRepository.findOne(id);
        if (member.getStatus().equals(MemberStatusEnum.REJECTED)) {
            if (repayImmediatelyIfNeeded(member, creditResult)) return 31;
            return 32;
        }
        if ((creditResult != null) && creditResult.getLastDecision().equals("D")) return 1;
        if ((creditResult != null) && creditResult.getLastDecision().equals("A")) return 2;
        return 0;
    }

    @Override
    public Integer getApplicationStatus(Long memberId) {
        CreditResult creditResult = creditResultRepository.findByMemberId(memberId);
        if ((creditResult != null) && creditResult.getLastDecision().equals("D")) return 1;
        if ((creditResult != null) && creditResult.getLastDecision().equals("A")) return 2;
        return 0;
    }

    @Override
    public String getRating(Long memberId) {
        CreditResult creditResult = creditResultRepository.findByMemberId(memberId);
        if (creditResult != null) return creditResult.getLastRating();
        return find(memberId).getPreRating();
    }
}
