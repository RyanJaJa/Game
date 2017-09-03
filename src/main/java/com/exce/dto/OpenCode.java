package com.exce.dto;


import com.google.common.collect.Lists;

import java.util.List;


public class OpenCode {

    private Integer rows;
    private String code;
    private String remain;
    private List<OpenCodeRawData> data = Lists.newArrayList();

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public List<OpenCodeRawData> getData() {
        return data;
    }

    public void setData(List<OpenCodeRawData> data) {
        this.data = data;
    }
}
