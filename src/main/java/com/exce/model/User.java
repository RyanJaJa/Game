package com.exce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

@Data
@Entity
public class User extends BaseEntity implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BIGINT")
    private BigInteger id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private Calendar lastPasswordResetTime;

    @OneToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE}, mappedBy = "player")
    @JsonIgnore
    private Set<BetOrder> betOrders = new HashSet<>();
    @OneToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE}, mappedBy = "updateUser")
    @JsonIgnore
    private Set<BetWinningNumber> betWinningNumbers = Sets.newHashSet();
//    private List<String> roles;

    public BigInteger getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendar getLastPasswordResetTime() {
        return lastPasswordResetTime;
    }

    public void setLastPasswordResetTime(Calendar lastPasswordResetTime) {
        this.lastPasswordResetTime = lastPasswordResetTime;
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
        builder.append("User [id=");
        builder.append(getId());
        builder.append(", username=");
        builder.append(getUsername());
        builder.append(", email=");
        builder.append(getEmail());
        builder.append(", betOrders=");
        builder.append(getBetOrders());
        builder.append(", betWinningNumbers=");
        builder.append(getBetWinningNumbers());
        builder.append("]");
        return builder.toString();
    }
}
