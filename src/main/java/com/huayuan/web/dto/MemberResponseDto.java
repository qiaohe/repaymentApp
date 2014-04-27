package com.huayuan.web.dto;

import com.huayuan.common.Day;
import com.huayuan.domain.member.Member;

import java.io.Serializable;

/**
 * Created by dell on 14-4-27.
 */
public class MemberResponseDto implements Serializable {
    private static final long serialVersionUID = -8031658907040656280L;
    private Long memberId;
    private String idCardNo;
    private String validFrom;
    private String validThru;
    private int industry;
    private int education;
    private String email;

    public MemberResponseDto() {
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidThru() {
        return validThru;
    }

    public void setValidThru(String validThru) {
        this.validThru = validThru;
    }

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static MemberResponseDto valueOf(Member member) {
        MemberResponseDto result = new MemberResponseDto();
        result.setMemberId(member.getId());
        result.setIdCardNo(member.getIdCard().getIdNo());
        result.setValidFrom(new Day(member.getIdCard().getValidFrom()).toString());
        result.setValidThru(new Day(member.getIdCard().getValidThru()).toString());
        result.setEmail(member.getEmail());
        result.setEducation(member.getEducation());
        result.setIndustry(member.getIndustry());
        return result;
    }
}
