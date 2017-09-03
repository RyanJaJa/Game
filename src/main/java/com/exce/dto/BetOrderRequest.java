package com.exce.dto;



import com.exce.model.BetOrderDetail;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


public class BetOrderRequest implements Serializable {

    private static final long serialVersionUID = -1389163607745384617L;

    private BigInteger gameId;

    private BigInteger playerId;

    private BigDecimal betTotalAmount;

    private String status;

    private String betItem;

    private List<String> betType = Lists.newArrayList();

    private BigInteger raffleNumber;

    private BigInteger betValue;

    private Double odds;

    private Integer chaseCount;

    private String chaseStatus;

    private List<BetOrderDetail> betDetails = Lists.newLinkedList();

    public BigInteger getGameId() {
        return gameId;
    }

    public void setGame(BigInteger gameId) {
        this.gameId = gameId;
    }

    public BigInteger getPlayerId() {
        return playerId;
    }

    public void setPlayerId(BigInteger playerId) {
        this.playerId = playerId;
    }

    public BigDecimal getBetTotalAmount() {
        return betTotalAmount;
    }

    public void setBetTotalAmount(BigDecimal betTotalAmount) {
        this.betTotalAmount = betTotalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBetItem() {
        return betItem;
    }

    public void setBetItem(String betItem) {
        this.betItem = betItem;
    }

    public List<String> getBetType() { return betType; }

    public void setBetType(List<String> betType) { this.betType = betType; }

    public BigInteger getRaffleNumber() {
        return raffleNumber;
    }

    public void setRaffleNumber(BigInteger raffleNumber) {
        this.raffleNumber = raffleNumber;
    }

    public BigInteger getBetValue() { return betValue; }

    public void setBetValue(BigInteger betValue) {
        this.betValue = betValue;
    }

    public double getOdds() { return odds; }

    public void setOdds(double odds) { this.odds = odds; }

    public Integer getChaseCount() { return chaseCount; }

    public void setChaseCount(Integer chaseCount) { this.chaseCount = chaseCount; }

    public String getChaseStatus() { return chaseStatus; }

    public void setChaseStatus(String chaseStatus) { this.chaseStatus = chaseStatus; }

    public List<BetOrderDetail> getBetDetails() {
        return betDetails;
    }

    public void setBetDetails(List<BetOrderDetail> betDetails) {
        this.betDetails = betDetails;
    }

    /*@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BetOrderRequest [gameId=");
        builder.append(getGameId());
        builder.append(", playerId=");
        builder.append(getPlayerId());
        builder.append(", betTotalAmount=");
        builder.append(getBetTotalAmount());
        builder.append(", status=");
        builder.append(getStatus());
        builder.append(", createTime=");

        builder.append(", betItem=");
        builder.append(getBetItem());
        builder.append(", betType=");
        builder.append(getBetType());
        builder.append(", raffleNumber=");
        builder.append(getRaffleNumber());
        builder.append(", betValue=");
        builder.append(getBetValue());
        builder.append(", odds=");
        builder.append(getOdds());
        builder.append(", chaseCount=");
        builder.append(getChaseCount());
        builder.append(", chaseStatus=");
        builder.append(getChaseStatus());
        builder.append(", betDetaildto=");
        builder.append(getBetDetaildto());

        builder.append("]");
        return builder.toString();
    }*/
}
