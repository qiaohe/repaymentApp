package com.huayuan.web;

import com.huayuan.domain.dictionary.CreditLimitRanges;
import com.huayuan.domain.member.CreditCard;
import com.huayuan.domain.member.IdCard;
import com.huayuan.domain.member.Member;
import com.huayuan.domain.recognizer.IdCardRecognizer;
import com.huayuan.service.MemberService;
import com.huayuan.web.dto.CreditLimitDto;
import com.huayuan.web.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Johnson on 3/19/14.
 */
@Controller
@RequestMapping("/members")
public class MemberController {
    @Inject
    MemberService memberService;
    @Inject
    CreditLimitRanges creditLimitRanges;

    @RequestMapping(value = "/{id}/idCardFront", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadIdCardFront(@PathVariable Long id, @RequestParam("idCardFrontFile") MultipartFile idCardFrontFile) throws IOException {
        if (idCardFrontFile.isEmpty())
            throw new IllegalArgumentException("error.member.idCard.front.bad.argument.empty");
        IdCardRecognizer recognizer = new IdCardRecognizer(idCardFrontFile.getBytes());
        IdCard idCard = recognizer.recognize(true);
        idCard = memberService.addIdCard(memberService.find(id), idCard);
        return idCard.getIdNo();
    }

    @RequestMapping(value = "/{id}/idCardBack", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadIdCardBack(@PathVariable Long id, @RequestParam("idCardBackFile") MultipartFile idCardBackFile) throws IOException {
        if (idCardBackFile.isEmpty())
            throw new IllegalArgumentException("error.member.idCard.back.bad.argument.empty");
        IdCardRecognizer recognizer = new IdCardRecognizer(idCardBackFile.getBytes());
        IdCard idCard = recognizer.recognize(false);
        memberService.updateIdCard(memberService.find(id), idCard);
        return idCard.getValidThru().toString();
    }

    @RequestMapping(value = "/{id}/creditCard", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    String addCreditCard(@PathVariable Long id, @RequestParam("creditCardNo") String creditCardNo) {
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
        Integer creditLimit = memberService.testCreditLimit(memberDto);
        String rankOfLimit = creditLimitRanges.getScaleBy(Long.valueOf(creditLimit.toString())).toString();
        return new CreditLimitDto(creditLimit, rankOfLimit);
    }



//    @RequestMapping(value = "/{id}/billEmail", method = RequestMethod.POST)
//    @ResponseBody
//    @ResponseStatus(value = HttpStatus.OK)
//    public void updateBillEmail(@PathVariable Long id, String billEmail, String password) {
//        Member member = memberService.find(id);
//        memberService.updateBillEmail(member, billEmail, password);
//    }


    @RequestMapping(value = "/{id}/crl", method = RequestMethod.GET)
    public Integer testResult(@PathVariable Long id) {
        return memberService.getCrl(id);
    }
}
