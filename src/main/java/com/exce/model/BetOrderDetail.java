package com.exce.model;


import com.exce.model.parameter.BetItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

import java.util.Set;


@Data
@Entity
public class BetOrderDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2554228764008012876L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, columnDefinition = "BIGINT", nullable = false)
    private BigInteger id;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private BetOrder betOrder;

    @Column(length = 50)
    private BetItem betItem;

    @Column(length = 10)
    private String betType;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(
            name = "detail_winning_mapping",
            joinColumns = @JoinColumn(name = "bet_order_detail_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "winning_number_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<BetWinningNumber> betWinningNumbers = Sets.newHashSet();

    @Column(name = "raffle_number", columnDefinition = "BIGINT")
    private BigInteger raffleNumber;

    @Column(name = "bet_value", columnDefinition = "BIGINT")
    private BigInteger betValue;

    private Boolean winning;

    @Column(columnDefinition = "DOUBLE")
    private Double odds;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BetOrder getBetOrder() {
        return betOrder;
    }

    public void setBetOrder(BetOrder betOrder) {
        this.betOrder = betOrder;
    }

    public Set<BetWinningNumber> getBetWinningNumbers() {
        return betWinningNumbers;
    }

    public void setBetWinningNumbers(Set<BetWinningNumber> betWinningNumbers) { this.betWinningNumbers = betWinningNumbers; }

    public BetItem getBetItem() { return betItem; }

    public void setBetItem(BetItem betItem) { this.betItem = betItem; }

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

    public BigInteger getBetValue() {
        return betValue;
    }

    public void setBetValue(BigInteger betValue) {
        this.betValue = betValue;
    }

    public Boolean getWinning() {
        return winning;
    }

    public void setWinning(Boolean winning) {
        this.winning = winning;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    /*@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BetDetail [id=");
        builder.append(getId());
        builder.append(", betOrder=");
        if (getBetOrder() != null) {
            builder.append(getBetOrder().toString());
        } else {
            builder.append(getBetOrder());
        }
        builder.append(", betItem=");
        if (getBetItem() != null) {
            builder.append(getBetItem().toString());
        } else {
            builder.append(getBetItem());
        }
        builder.append(", betType=");
        builder.append(getBetType());
        builder.append(", raffleNumber=");
        builder.append(getRaffleNumber());
        builder.append(", betValue=");
        builder.append(getBetValue());
        builder.append(", winning=");
        builder.append(getWinning());
        builder.append(", odds=");
        builder.append(getOdds());
        builder.append("]");
        return builder.toString();
    }*/
}
