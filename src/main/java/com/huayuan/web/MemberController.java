package com.huayuan.web;

import com.huayuan.domain.BillMailbox;
import com.huayuan.domain.Member;
import com.huayuan.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
    String uploadIdCardFront(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            return null;
        }
        return null;
    }

    @RequestMapping(value = "/uploadIdCardBack", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadIdCardBack(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            return null;
        }
        return null;
    }

    @RequestMapping(value = "/uploadCreditCard", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadCreditCard(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            return null;
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
