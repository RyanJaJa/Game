package com.exce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Calendar;

public class OpenCodeRawData {

    private String expect;
    private String opencode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar opentime;
    private Integer opentimestamp;

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getOpencode() {
        return opencode;
    }

    public void setOpencode(String opencode) {
        this.opencode = opencode;
    }

    public Calendar getOpentime() {
        return opentime;
    }

    public void setOpentime(Calendar opentime) {
        this.opentime = opentime;
    }

    public Integer getOpentimestamp() {
        return opentimestamp;
    }

    public void setOpentimestamp(Integer opentimestamp) {
        this.opentimestamp = opentimestamp;
    }
}