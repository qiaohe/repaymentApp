package com.huayuan.web;

import com.huayuan.common.Day;
import com.huayuan.domain.dictionary.CreditLimitRanges;
import com.huayuan.domain.member.CreditCard;
import com.huayuan.domain.member.IdCard;
import com.huayuan.domain.member.Member;
import com.huayuan.domain.recognizer.IdCardRecognizer;
import com.huayuan.repository.member.PreCreditRepository;
import com.huayuan.service.MemberService;
import com.huayuan.service.SmsVerificationCodeService;
import com.huayuan.web.dto.CreditLimitDto;
import com.huayuan.web.dto.MemberDto;
import com.huayuan.web.dto.MemberResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Johnson on 3/19/14.
 */
@Controller
@RequestMapping("/members")
public class MemberController {
    @Inject
    private MemberService memberService;
    @Inject
    private CreditLimitRanges creditLimitRanges;
    @Inject
    private PreCreditRepository preCreditRepository;
    @Inject
    private SmsVerificationCodeService smsVerificationCodeService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public MemberResponseDto getMember(@PathVariable Long id) {
        return MemberResponseDto.valueOf(memberService.find(id));
    }

    @RequestMapping(value = "/{id}/idCardFront", method = RequestMethod.POST)
    public
    @ResponseBody
    Callable<IdCard> uploadIdCardFront(@PathVariable final Long id, @RequestParam("idCardFrontFile") final MultipartFile idCardFrontFile) throws IOException {
        if (idCardFrontFile.isEmpty())
            throw new IllegalArgumentException("error.member.idCard.front.bad.argument.empty");
        return new Callable<IdCard>() {
            public IdCard call() throws Exception {
                IdCardRecognizer recognizer = new IdCardRecognizer(idCardFrontFile.getBytes());
                IdCard idCard = recognizer.recognize(true);
                idCard = memberService.addIdCard(memberService.find(id), idCard);
                return idCard;
            }
        };
    }

    @RequestMapping(value = "/{id}/idCardBack", method = RequestMethod.POST)
    @ResponseBody
    public Callable<String> uploadIdCardBack(@PathVariable final Long id, @RequestParam("idCardBackFile") final MultipartFile idCardBackFile) throws IOException {
        if (idCardBackFile.isEmpty())
            throw new IllegalArgumentException("error.member.idCard.back.bad.argument.empty");
        return new Callable<String>() {
            public String call() throws Exception {
                IdCardRecognizer recognizer = new IdCardRecognizer(idCardBackFile.getBytes());
                IdCard idCard = recognizer.recognize(false);
                memberService.updateIdCard(memberService.find(id), idCard);
                return new Day(idCard.getValidThru()).toString();
            }
        };
    }

    @RequestMapping(value = "/{id}/creditCards/{creditCardNo}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    String addCreditCard(@PathVariable Long id, @PathVariable String creditCardNo) {
        Member member = memberService.find(id);
        memberService.addCreditCard(member, creditCardNo);
        return creditCardNo;
    }

    @RequestMapping(value = "/{id}/creditCard", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    List<CreditCard> getCreditCards(@PathVariable Long id) {
        return memberService.getCreditCards(id);
    }

    @RequestMapping(value = "/{id}/idCard", method = RequestMethod.GET)
    public
    @ResponseBody
    IdCard getIdCard(@PathVariable Long id) {
        Member member = memberService.find(id);
        return member.getIdCard();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public CreditLimitDto testCreditLimit(@PathVariable Long id, @RequestBody MemberDto memberDto) {
        memberDto.setMemberId(id);
        Double creditLimit = (double) preCreditRepository.execute(memberService.testCreditLimit(memberDto));
        String rankOfLimit = creditLimitRanges.getScaleBy(creditLimit.longValue()).toString();
        return new CreditLimitDto(creditLimit, rankOfLimit);
    }

    @RequestMapping(value = "/{id}/crl", method = RequestMethod.GET)
    @ResponseBody
    public CreditLimitDto getCrl(@PathVariable Long id) {
        Double creditLimit = memberService.getCrl(id);
        String rankOfLimit = creditLimitRanges.getScaleBy(creditLimit.longValue()).toString();
        return new CreditLimitDto(creditLimit, rankOfLimit);
    }

    @RequestMapping(value = "/{id}/avlCrl", method = RequestMethod.GET)
    @ResponseBody
    public Double getAvailableCreditLimit(@PathVariable Long id) {
        return memberService.getAvlCrl(id);
    }

    @RequestMapping(value = "/{id}/{mobilePhone}/{code}", method = RequestMethod.POST)
    @ResponseBody
    public boolean verifyCode(@PathVariable Long memberId, @PathVariable String mobilePhone, @PathVariable String code) {
        boolean verified = smsVerificationCodeService.verifyCode(mobilePhone, code);
        if (verified) {
            Member member = memberService.find(memberId);
            member.setMobile(mobilePhone);
        }
        return verified;
    }
}
