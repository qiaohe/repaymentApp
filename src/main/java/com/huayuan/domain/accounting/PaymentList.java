package com.huayuan.domain.accounting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huayuan.common.util.Constants;
import com.huayuan.domain.payment.PkiPairUtil;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.Date;

/**
 * Created by dell on 14-8-14.
 */
@Entity
@Table(name = "PAYMENT_LIST")
public class PaymentList {
    private static final String PAYMENT_SIGN_MESSAGE_PATTERN = "merchantAcctId={0}&version={1}&language={2}&signType={3}" +
            "&payType={4}&bankId={5}&orderId={6}&orderTime={7}&orderAmount={8}&dealId={9}&bankDealId={10}&dealTime={11}" +
            "&payAmount={12}&payResult={13}";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;
    @Column(name = "MERCHANT_ACCT_ID")
    private String merchantAcctId;
    @Column(name = "VERSION")
    private String version;
    @Column(name = "LANGUAGE")
    private String language;
    @Column(name = "SIGN_TYPE")
    private String signType;
    @Column(name = "PAY_TYPE")
    private String payType;
    @Column(name = "BANK_ID")
    private String bankId;
    @Column(name = "ORDER_ID")
    private String orderId;
    @Column(name = "ORDER_TIME")
    private Date orderTime;
    @Column(name = "ORDER_AMOUNT")
    private Double orderAmount;
    @Column(name = "DEAL_ID")
    private String dealId;
    @Column(name = "BANK_DEAL_ID")
    private String bankDealId;
    @Column(name = "DEAL_TIME")
    private Date dealTime;
    @Column(name = "PAY_AMOUNT")
    private Double payAmount;
    @Column(name = "FEE")
    private Double fee;
    @Column(name = "PAY_RESULT")
    private String payResult;
    @Column(name = "ERR_CODE")
    private String errCode;
    @Column(name = "BIND_CARD")
    private String bindCard;
    @Column(name = "EXT1")
    private String ext1;
    @Column(name = "EXT2")
    private String ext2;
    @Column(name = "SIGN_MSG")
    private String signMsg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantAcctId() {
        return merchantAcctId;
    }

    public void setMerchantAcctId(String merchantAcctId) {
        this.merchantAcctId = merchantAcctId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getBankDealId() {
        return bankDealId;
    }

    public void setBankDealId(String bankDealId) {
        this.bankDealId = bankDealId;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getPayResult() {
        return payResult;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getBindCard() {
        return bindCard;
    }

    public void setBindCard(String bindCard) {
        this.bindCard = bindCard;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    public String paymentSignMessageVariable() {
        String result = MessageFormat.format(PAYMENT_SIGN_MESSAGE_PATTERN, merchantAcctId, version, language, signType, payType, bankId,
                orderId, new DateTime(orderTime).toString(Constants.LONG_DATE_PATTERN), orderAmount, dealId, bankDealId,
                new DateTime(dealTime).toString(Constants.LONG_DATE_PATTERN), payAmount, payResult);
        return StringUtils.isEmpty(errCode) ? result : result + "&errCode=" + errCode;
    }

    public boolean isPaymentSuccess() {
        boolean signMessageMatched = new PkiPairUtil().enCodeByCer(paymentSignMessageVariable(), signMsg);
        return signMessageMatched && Integer.valueOf(payResult) == 10;
    }
}
