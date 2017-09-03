package com.exce.dto;

import com.exce.model.BetWinningNumber;
import com.exce.model.Game;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BetOrderResponse implements Serializable {

    private static final long serialVersionUID = 7671297705693727543L;

    private Game game;

    private BetWinningNumber betWinningNumber;

    private Integer chaseCount;

    private String chaseStatus;

    private BigDecimal chaseTotalAmount;

    private String status;

    private BigInteger[] raffleNumber;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BetWinningNumber getBetWinningNumber() {
        return betWinningNumber;
    }

    public void setBetWinningNumber(BetWinningNumber betWinningNumber) {
        this.betWinningNumber = betWinningNumber;
    }

    public Integer getChaseCount() {
        return chaseCount;
    }

    public void setChaseCount(Integer chaseCount) {
        this.chaseCount = chaseCount;
    }

    public String getChaseStatus() {
        return chaseStatus;
    }

    public void setChaseStatus(String chaseStatus) {
        this.chaseStatus = chaseStatus;
    }

    public BigDecimal getChaseTotalAmount() {
        return chaseTotalAmount;
    }

    public void setChaseTotalAmount(BigDecimal chaseTotalAmount) {
        this.chaseTotalAmount = chaseTotalAmount;
    }

    public BigInteger[] getRaffleNumber() { return raffleNumber; }

    public void setRaffleNumber(BigInteger[] raffleNumber) { this.raffleNumber = raffleNumber; }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BetOrderResponse [Game=");
        if (getGame() != null) {
            builder.append(getGame().toString());
        } else {
            builder.append(getGame());
        }
        builder.append(", BetItem=");
        builder.append(", betWinningNumber=");
        if (getBetWinningNumber() != null) {
            builder.append(getBetWinningNumber().toString());
        } else {
            builder.append(getBetWinningNumber());
        }
        builder.append(", chaseCount=");
        builder.append(getChaseCount());
        builder.append(", chaseStatus=");
        builder.append(getChaseStatus());
        builder.append(", chaseTotalAmount=");
        builder.append(getChaseTotalAmount());
        builder.append(", status=");
        builder.append(getStatus());
        builder.append(", raffleNumber=");
        builder.append(getRaffleNumber());
        builder.append("]");
        return builder.toString();
    }
}
