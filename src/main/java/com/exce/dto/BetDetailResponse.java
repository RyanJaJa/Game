package com.exce.dto;

import com.exce.model.Game;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BetDetailResponse implements Serializable {

    private static final long serialVersionUID = 7671297705693727543L;

    private BigInteger orderId;

    private String betItem;

    private String betType;

    private BigInteger raffleNumber;

    private Game game;

    private BigDecimal betTotalAmount;

    private String status;

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public String getBetItem() {
        return betItem;
    }

    public void setBetItem(String betItem) {
        this.betItem = betItem;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public BigInteger getRaffleNumber() {
        return raffleNumber;
    }

    public void setRaffleNumber(BigInteger raffleNumber) {
        this.raffleNumber = raffleNumber;
    }

    public Game getGame() { return game; }

    public void setGame(Game game) { this.game = game; }

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BetDetailResponse [orderId=");
        builder.append(getOrderId());
        builder.append(", betItem=");
        builder.append(getBetItem());
        builder.append(", betType=");
        builder.append(getBetType());
        builder.append(", raffleNumber=");
        builder.append(getRaffleNumber());
        builder.append(", gameId=");
        builder.append(getGame());
        builder.append(", betTotalAmount=");
        builder.append(getBetTotalAmount());
        builder.append(", status=");
        builder.append(getStatus());
        builder.append("]");
        return builder.toString();
    }
}
