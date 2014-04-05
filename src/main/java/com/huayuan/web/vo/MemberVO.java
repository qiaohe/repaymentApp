package com.huayuan.web.vo;

import java.io.Serializable;

/**
 * Created by dell on 14-4-5.
 */
public class MemberVO implements Serializable {
    private static final long serialVersionUID = 6133417800869029252L;
    private int education;
    private String email;
    private int industry;

    public MemberVO() {
    }

    public MemberVO(int education, String email, int industry) {
        this();
        this.education = education;
        this.email = email;
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

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }
}
