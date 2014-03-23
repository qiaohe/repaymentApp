package com.huayuan.web;

import com.huayuan.domain.BillMailbox;
import com.huayuan.domain.CreditCard;
import com.huayuan.domain.IdCard;
import com.huayuan.domain.Member;
import com.huayuan.domain.recognizer.ReadCard;
import com.huayuan.service.MemberService;
import com.huayuan.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Johnson on 3/19/14.
 */
@Controller
public class MemberController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);
    @Autowired
    MemberService memberService;

    @RequestMapping(value = "/testCreditLimit", method = RequestMethod.GET)
    public ModelMap register() {
        ModelMap model = new ModelMap();
        model.addAttribute("member", new Member());
        return model;
    }

    @RequestMapping(value = "/uploadIdCardFront", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadIdCardFront(HttpServletRequest request, @RequestParam("idCardFrontFile") MultipartFile idCardFrontFile) {
        if (!idCardFrontFile.isEmpty()) {

            String fileName = idCardFrontFile.getOriginalFilename();
            String savePath = new File("").getAbsolutePath() + "/" + fileName;
            saveFile(idCardFrontFile, savePath);
            ReadCard readCard = new ReadCard();

            //teeseract img
            String reSrcPath = new File("").getAbsolutePath() + "/re_" + fileName;
            String reCmd = readCard.resize(savePath, reSrcPath, 700, 340);
            readCard.runCmd(reCmd);
            String idCardNumber = readCard.readCard(reSrcPath, 210, 250, 500, 100);
            idCardNumber = idCardNumber.replaceAll(" ","");
            if(idCardNumber.length() > 18 ){
                return "";
            }
            FileUtils.delFile(reSrcPath);

            Member member = memberService.find(1l);
            if(member.getIdCard() != null ){
                return idCardNumber;
            }
            //save IdCard
            IdCard idCard = new IdCard();
            idCard.setIdNo(idCardNumber);
            idCard.setMember(member);

            member.setIdCard(idCard);

            memberService.addIdCard(member,idCard);

            return idCardNumber;
        }
        return "";
    }

    @RequestMapping(value = "/uploadIdCardBack", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadIdCardBack(@RequestParam("idCardBackFile") MultipartFile idCardBackFile) {
        if (!idCardBackFile.isEmpty()) {
            String fileName = idCardBackFile.getOriginalFilename();
            String savePath = new File("").getAbsolutePath() + "/" + fileName;
            saveFile(idCardBackFile, savePath);
            return "1";
        }
        return null;
    }

    @RequestMapping(value = "/uploadCreditCard", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadCreditCard(@RequestParam("creditCardFile") MultipartFile creditCardFile) {
        if (!creditCardFile.isEmpty()) {
            //读取信用卡信息并保存
            Member member = memberService.find(1L);

            CreditCard creditCard = new CreditCard();
            creditCard.setCardNo("568454334456311247");
            creditCard.setName("zhangsan");
            creditCard.setMember(member);

            member.addCreditCard(creditCard);
            memberService.addCreditCard(member,creditCard);

            return creditCard.getCardNo();
        }
        return null;
    }

    @RequestMapping(value = "/billMailBox", method = RequestMethod.POST)
    public
    @ResponseBody
    String postBillMailBox(BillMailbox billMailbox) {
        memberService.addBillMailBox(null, billMailbox);
        return null;
    }

    @RequestMapping(value = "/postMember", method = RequestMethod.POST)
    @ResponseBody
    public String postMember(@ModelAttribute Member member,String billMailbox_email,String billMailbox_password) {

        if(!billMailbox_email.isEmpty() && !billMailbox_password.isEmpty()){
            BillMailbox billMailbox = new BillMailbox();
            billMailbox.setEmail(billMailbox_email);
            billMailbox.setPassword(billMailbox_password);
            billMailbox.setMember(member);
            member.addBillMailbox(billMailbox);
        }
        memberService.register(member);

        return "5000";
    }

    @RequestMapping(value = "/testResult",method = RequestMethod.GET)
    public ModelMap testResult(String crl){

        ModelMap model = new ModelMap();
        model.addAttribute("crl",crl);

        return model;
    }

    private void saveFile(MultipartFile file, final String targetFileName) {
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(targetFileName)));
            stream.write(bytes);
            stream.close();
        } catch (Exception ignored) {
            LOGGER.error("Error Happened when saving file:" + targetFileName);
        }
    }
}
