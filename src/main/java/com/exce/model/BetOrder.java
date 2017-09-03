package com.exce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Data
@Entity
public class BetOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8190106198633889279L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, columnDefinition = "BIGINT", nullable = false)
    @JsonProperty("orderId")
    private BigInteger id;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "player_id")
    private User player;

    private Integer chaseCount;

    @Column(length = 50)
    private String chaseStatus;

    @Column(name = "chase_stop")
    private Boolean chaseStop;

    @Column(name = "bet_total_amount", columnDefinition = "DECIMAL(10,1)")
    private BigDecimal betTotalAmount;

    @Column(length = 50)
    private String status;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "update_user_id")
    private User updateUser;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "betOrder")
    private List<BetOrderDetail> betOrderDetails = Lists.newArrayList();

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
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

    public Boolean getChaseStop() { return chaseStop; }

    public void setChaseStop(Boolean chaseStop) { this.chaseStop = chaseStop; }

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

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public List<BetOrderDetail> getBetOrderDetails() { return betOrderDetails; }

    public void setBetOrderDetails(List<BetOrderDetail> betOrderDetails) {
        this.betOrderDetails = betOrderDetails;
    }

    /*
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BetOrder [id=");
        builder.append(getId());
        builder.append(", game=");
        if (getGame() != null) {
            builder.append(getGame().toString());
        } else {
            builder.append(getGame());
        }
        builder.append(", player=");
        if (getPlayer() != null) {
            builder.append(getPlayer().toString());
        } else {
            builder.append(getPlayer());
        }
        builder.append(", chaseCount=");
        builder.append(getChaseCount());
        builder.append(", chaseStatus=");
        builder.append(getChaseStatus());
        builder.append(", chaseStop=");
        builder.append(getChaseStop());
        builder.append(", betTotalAmount=");
        builder.append(getBetTotalAmount());
        builder.append(", status=");
        builder.append(getStatus());
        builder.append(", updateUser=");
        if (getUpdateUser() != null) {
            builder.append(getUpdateUser().toString());
        } else {
            builder.append(getUpdateUser());
        }
        builder.append(", betDetails=");
        builder.append(getBetDetails());
        builder.append("]");
        return builder.toString();
    }
*/

}
