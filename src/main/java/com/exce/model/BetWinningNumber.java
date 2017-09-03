package com.exce.model;

import com.exce.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Sets;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Set;

@Data
@Entity
public class BetWinningNumber extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3705223005234310153L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, columnDefinition = "BIGINT", nullable = false)
    private BigInteger id;

    @Column(name = "raffle_number", unique = true, columnDefinition = "BIGINT", nullable = false)
    private BigInteger raffleNumber;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(length = 30)
    private String result;

    @Column(name = "result_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Calendar resultTime;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "update_user_id")
    private User updateUser;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "betWinningNumbers")
    @JsonIgnore
    private Set<BetOrderDetail> betDetails = Sets.newHashSet();

    public BigInteger getId() { return id; }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getRaffleNumber() {
        return raffleNumber;
    }

    public void setRaffleNumber(BigInteger raffleNumber) {
        this.raffleNumber = raffleNumber;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Calendar getResultTime() {
        return resultTime;
    }

    public void setResultTime(Calendar resultTime) {
        this.resultTime = resultTime;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public Set<BetOrderDetail> getBetDetails() {
        return betDetails;
    }

    public void setBetDetails(Set<BetOrderDetail> betDetails) {
        this.betDetails = betDetails;
    }

    /*@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BetWinningNumber [id=");
        builder.append(getId());
        builder.append(", raffleNumber=");
        builder.append(getRaffleNumber());
        builder.append(", game=");
        if (getGame() != null) {
            builder.append(getGame().toString());
        } else {
            builder.append(getGame());
        }
        builder.append(", result=");
        builder.append(getResult());
        builder.append(", resultTime=");
        builder.append(getResultTime());
        builder.append(", updateUser=");
        builder.append(getUpdateUser());
        builder.append(", betOrders=");
        builder.append(getBetDetails());
        builder.append("]");
        return builder.toString();
    }*/
}
