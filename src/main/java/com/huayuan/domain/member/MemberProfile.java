package com.huayuan.domain.member;

import com.huayuan.domain.accounting.Account;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Johnson on 5/17/14.
 */
public class MemberProfile implements Serializable {
    private static final long serialVersionUID = -1169605034032493164L;
    private Member member;
    private Account account;
    private List<Application> applications;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public static class Builder {
        private MemberProfile profile;

        public Builder() {
            this.profile = new MemberProfile();
        }

        public Builder member(Member member) {
            profile.setMember(member);
            return this;
        }

        public Builder applications(List<Application> applications) {
            profile.setApplications(applications);
            return this;
        }

        public Builder account(Account account) {
            profile.setAccount(account);
            return this;
        }

        public MemberProfile build() {
            return profile;
        }
    }

    public static class Application {
        private String appNo;
        private Date applyDate;
        private String decision;

        public Application() {
        }

        public Application(String appNo, Date applyDate, String decision) {
            this.appNo = appNo;
            this.applyDate = applyDate;
            this.decision = decision;
        }

        public String getAppNo() {
            return appNo;
        }

        public void setAppNo(String appNo) {
            this.appNo = appNo;
        }

        public Date getApplyDate() {
            return applyDate;
        }

        public void setApplyDate(Date applyDate) {
            this.applyDate = applyDate;
        }

        public String getDecision() {
            return decision;
        }

        public void setDecision(String decision) {
            this.decision = decision;
        }
    }
}
