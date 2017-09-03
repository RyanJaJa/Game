package com.exce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

public class Account implements Serializable {
    @NotNull
    private BigInteger userId;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private Calendar lastPasswordResetTime;
    private Balance balance;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getLastPasswordResetTime() {
        return lastPasswordResetTime;
    }

    public void setLastPasswordResetTime(Calendar lastPasswordResetTime) {
        this.lastPasswordResetTime = lastPasswordResetTime;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Account [userId=");
        builder.append(getUserId());
        builder.append(", username=");
        builder.append(getUsername());
        builder.append(", password=");
        builder.append(getPassword());
        builder.append(", email=");
        builder.append(getEmail());
        builder.append(", lastPasswordResetTime=");
        builder.append(getLastPasswordResetTime());
        builder.append(", balance=");
        builder.append(getBalance() == null ? null : getBalance().toString());
        builder.append("]");
        return builder.toString();
    }
}
