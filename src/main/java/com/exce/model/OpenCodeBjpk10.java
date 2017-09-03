package com.exce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Calendar;

@Data
@Entity
public class OpenCodeBjpk10 extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, columnDefinition = "BIGINT", nullable = false)
    private BigInteger id;
    private String expect;
    private String openCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar openTime;
    private Integer openTimestamp;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }

    public Calendar getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Calendar openTime) {
        this.openTime = openTime;
    }

    public Integer getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(Integer openTimestamp) {
        this.openTimestamp = openTimestamp;
    }
}
