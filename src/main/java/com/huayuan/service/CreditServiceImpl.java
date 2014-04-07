package com.huayuan.service;

import com.huayuan.domain.idgenerator.IdSequenceGenerator;
import com.huayuan.domain.loanapplication.CreditResult;
import com.huayuan.domain.loanapplication.Staff;
import com.huayuan.domain.member.Member;
import com.huayuan.repository.credit.CreditResultRepository;
import com.huayuan.repository.credit.StaffRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by Johnson on 4/7/14.
 */
@Service(value = "creditService")
@Transactional
public class CreditServiceImpl implements CreditService {
    @Inject
    private CreditResultRepository creditResultRepository;
    @Inject
    private StaffRepository staffRepository;
    @Inject
    private IdSequenceGenerator idSequenceGenerator;

    @Override
    public void addCreditResult(CreditResult creditResult) {
        creditResultRepository.save(creditResult);
    }

    @Override
    public Iterable<Staff> getStaffs() {
        return staffRepository.findAll();
    }

    @Override
    public void registerStaff(Staff staff) {
        staff.setStaffId(idSequenceGenerator.getStaffNo());
        staffRepository.save(staff);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
        ApplicationService ms = applicationContext.getBean("applicationService", ApplicationService.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        Member member = memberService.find(1l);

        CreditService creditService = applicationContext.getBean("creditService", CreditService.class);

//
//        CreditResult creditResult = new CreditResult();
//        creditResult.setLastPbocBackTime(new Date());
//        creditResult.setLastReason1("ddd");
//        creditResult.setLastScore(90);
//        creditResult.setMember(member);
//        creditResult.setLastApplicationNo(ms.getApplication("2014040601100000001"));
//        creditResult.setLastDecision(int.valueOf("2"));
//        creditService.addCreditResult(creditResult);

        Staff staff = new Staff();
        staff.setCreator("Johnson");
        staff.setEnrollTime(new Date());
        staff.setStatus("OK");
        staff.setRole(1);
        creditService.registerStaff(staff);
    }
}
