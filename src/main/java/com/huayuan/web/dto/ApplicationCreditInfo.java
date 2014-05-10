package com.huayuan.web.dto;

import com.huayuan.domain.credit.Pboc;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.member.Member;

import java.io.Serializable;

/**
 * Created by dell on 14-5-10.
 */
public class ApplicationCreditInfo implements Serializable {
    private static final long serialVersionUID = 4668039823659995340L;
    private Application application;
    private Member member;
    private Pboc pboc;

    public ApplicationCreditInfo() {
    }

    public Application getApplication() {
        return application;
    }

    public Member getMember() {
        return member;
    }

    public Pboc getPboc() {
        return pboc;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setPboc(Pboc pboc) {
        this.pboc = pboc;
    }

    public ApplicationCreditInfo(Application application, Member member, Pboc pboc) {
        this.application = application;
        this.member = member;
        this.pboc = pboc;
    }

    public static class Builder {
        private ApplicationCreditInfo dto;

        public Builder() {
            this.dto = new ApplicationCreditInfo();
        }

        public Builder application(Application application) {
            this.dto.setApplication(application);
            return this;
        }

        public Builder member(Member member) {
            this.dto.setMember(member);
            return this;
        }

        public Builder pboc(Pboc pboc) {
            this.dto.setPboc(pboc);
            return this;
        }
        public ApplicationCreditInfo build() {
            return dto;
        }
    }
}
