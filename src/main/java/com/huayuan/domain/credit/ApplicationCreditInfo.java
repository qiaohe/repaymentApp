package com.huayuan.domain.credit;

import com.huayuan.domain.accounting.Account;
import com.huayuan.domain.accounting.Loans;
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
    private Loans loans;
    private Account account;

    private ApplicationCreditInfo() {
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

    public Loans getLoans() {
        return loans;
    }

    public void setLoans(Loans loans) {
        this.loans = loans;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    private ApplicationCreditInfo(Application application, Member member, Pboc pboc, Loans loans, Account account) {
        this.application = application;
        this.member = member;
        this.pboc = pboc;
        this.loans = loans;
        this.account = account;
    }

    public static class Builder {
        private ApplicationCreditInfo creditInfo;

        public Builder() {
            this.creditInfo = new ApplicationCreditInfo();
        }

        public Builder application(Application application) {
            this.creditInfo.setApplication(application);
            return this;
        }

        public Builder member(Member member) {
            this.creditInfo.setMember(member);
            return this;
        }

        public Builder pboc(Pboc pboc) {
            this.creditInfo.setPboc(pboc);
            return this;
        }

        public Builder loans(Loans loans) {
            this.creditInfo.setLoans(loans);
            return this;
        }

        public Builder account(Account account) {
            this.creditInfo.setAccount(account);
            return this;
        }

        public ApplicationCreditInfo build() {
            return creditInfo;
        }

    }
}
