package com.exce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Balance implements Serializable {
    private static final long serialVersionUID = -8338041935308563839L;

    private BigInteger userId;
    private BigDecimal total;
    private BigDecimal transferAmount;
    @JsonProperty(value = "currency", defaultValue = "CNY")
    private String currencyCode;
    //1: 微信上分 2:網銀上分 3:銀行卡上分
    private byte transactionType;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTransferAmount() {
        return this.transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public byte getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(byte transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Balance [userId=");
        builder.append(getUserId());
        builder.append(", total=");
        builder.append(getTotal());
        builder.append(", transferAmount=");
        builder.append(getTransferAmount());
        builder.append(", currencyCode=");
        builder.append(getCurrencyCode());
        builder.append(", transactionType=");
        builder.append(getTransactionType());
        builder.append("]");
        return builder.toString();
    }
}
