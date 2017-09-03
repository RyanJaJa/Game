package com.exce.model;

import com.exce.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Calendar createTime;

    @Column(name = "modify_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Calendar modifyTime;

    @PrePersist
    public void prePersist() {
        this.createTime = Calendar.getInstance();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifyTime = Calendar.getInstance();
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public Calendar getModifyTime() {
        return modifyTime;
    }
}
