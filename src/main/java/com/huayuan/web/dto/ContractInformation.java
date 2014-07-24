package com.huayuan.web.dto;

import java.io.Serializable;

import static com.huayuan.common.App.getInstance;

/**
 * Created by dell on 14-7-23.
 */
public class ContractInformation implements Serializable {
    private static final long serialVersionUID = -6132704549790997685L;
    private Double apr;
    private String lender;
    private String lenderMobile;
    private String lenderPhone;

    public ContractInformation() {
    }

    public ContractInformation(Double apr, String lender, String lenderMobile, String lenderPhone) {
        this();
        this.apr = apr;
        this.lender = lender;
        this.lenderMobile = lenderMobile;
        this.lenderPhone = lenderPhone;
    }

    public Double getApr() {
        return apr;
    }

    public void setApr(Double apr) {
        this.apr = apr;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public String getLenderMobile() {
        return lenderMobile;
    }

    public void setLenderMobile(String lenderMobile) {
        this.lenderMobile = lenderMobile;
    }

    public String getLenderPhone() {
        return lenderPhone;
    }

    public void setLenderPhone(String lenderPhone) {
        this.lenderPhone = lenderPhone;
    }

    public static ContractInformation valueOf(Double apr) {
        return new ContractInformation(apr, getInstance().getLender(), getInstance().getLenderMobile(), getInstance().getLenderEmail());
    }
}
