package com.exce.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;

public class BetDetailRequest implements Serializable {

    private static final long serialVersionUID = -1389163607745384617L;

    @NotNull
    private BigInteger gameId;

    @NotNull
    private BigInteger playerId;

    public BigInteger getGameId() {
        return gameId;
    }

    public void setGameId(BigInteger gameId) {
        this.gameId = gameId;
    }

    public BigInteger getPlayerId() {
        return playerId;
    }

    public void setPlayerId(BigInteger playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BetOrderRequest [gameId=");
        builder.append(getGameId());
        builder.append(", playerId=");
        builder.append(getPlayerId());
        builder.append("]");
        return builder.toString();
    }
}
