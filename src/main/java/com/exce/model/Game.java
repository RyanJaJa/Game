package com.exce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Game extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 816765560786909407L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, columnDefinition = "BIGINT", nullable = false)
    private BigInteger id;

    @Column(length = 20)
    private String name;
    private Boolean active;
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "game")
    @JsonIgnore
    private Set<BetOrder> betOrders = new HashSet<>();
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "game")
    @JsonIgnore
    private Set<BetWinningNumber> betWinningNumbers = Sets.newHashSet();

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<BetOrder> getBetOrders() {
        return betOrders;
    }

    public void setBetOrders(Set<BetOrder> betOrders) {
        this.betOrders = betOrders;
    }

    public Set<BetWinningNumber> getBetWinningNumbers() {
        return betWinningNumbers;
    }

    public void setBetWinningNumbers(Set<BetWinningNumber> betWinningNumbers) {
        this.betWinningNumbers = betWinningNumbers;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Game [id=");
        builder.append(getId());
        builder.append(", name=");
        builder.append(getName());
        builder.append(", active=");
        builder.append(getActive());
        builder.append(", betOrders=");
        builder.append(getBetOrders());
        builder.append(", betWinningNumbers=");
        builder.append(getBetWinningNumbers());
        builder.append("]");
        return builder.toString();
    }
}
