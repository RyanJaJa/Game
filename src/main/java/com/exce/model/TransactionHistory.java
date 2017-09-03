package com.exce.model;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Entity
public class TransactionHistory extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, columnDefinition = "BIGINT", nullable = false)
    private BigInteger id;

    @Column(name = "user_id", columnDefinition = "BIGINT", nullable = false)
    private BigInteger userId;
    @Column(name = "currency_code", length = 10)
    @ColumnDefault("'CNY'")
    private String currencyCode;
    @Column(name = "transaction_type", columnDefinition = "TINYINT")
    private byte transactionType;
    @Column(name = "withdraw_amount", columnDefinition = "DECIMAL(10,1)")
    private BigDecimal withdrawAmount;
    @Column(name = "deposit_amount", columnDefinition = "DECIMAL(10,1)")
    private BigDecimal depositAmount;
    @Column(name = "balance_before", columnDefinition = "DECIMAL(10,1)")
    private BigDecimal balanceBefore;
    @Column(name = "balance_after", columnDefinition = "DECIMAL(10,1)")
    private BigDecimal balanceAfter;

    public TransactionHistory() {
        super();
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
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

    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TransactionDetail [userId=");
        builder.append(getUserId());
        builder.append(", currencyCode=");
        builder.append(getCurrencyCode());
        builder.append(", transactionType=");
        builder.append(getTransactionType());
        builder.append(", withdrawAmount=");
        builder.append(getWithdrawAmount());
        builder.append(", depositAmount=");
        builder.append(getDepositAmount());
        builder.append(", balanceBefore=");
        builder.append(getBalanceBefore());
        builder.append(", balanceAfter=");
        builder.append(getBalanceAfter());
        builder.append("]");
        return builder.toString();
    }
}
