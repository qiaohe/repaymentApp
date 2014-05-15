package com.huayuan.service;

        import com.huayuan.domain.credit.ApplicationCreditInfo;
        import com.huayuan.domain.credit.TvExecution;
        import com.huayuan.domain.loanapplication.Approval;
        import com.huayuan.domain.loanapplication.CreditResult;
        import com.huayuan.domain.loanapplication.Staff;
        import com.huayuan.domain.loanapplication.TelephoneTV;

        import java.util.List;

/**
 * Created by Johnson on 4/7/14.
 */
public interface CreditService {
    public void addCreditResult(CreditResult creditResult);

    public Iterable<Staff> getStaffs();

    public void registerStaff(Staff staff);

    public Approval approve(Approval approval);

    public void telephoneVerification();

    public TvExecution getTvExecution(final String appNo);

    public void replyTv(Long memberId, String replyAnswer);

    public TelephoneTV makeTelephoneTv(TelephoneTV telephoneTV);

    public List<TelephoneTV> getTelephoneTVs(String appNo);

    public ApplicationCreditInfo buildCreditInfo(String appNo);

    public Approval saveTemporarily(Approval approve);
}
