package com.exce.model;

public class ResponsePayload<T> {

    private T data;
    private String errorCode;
    private String errorDescription;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
/*
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResponsePayload [data=");
        if (getData() != null) {
            builder.append(getData().toString());
        } else {
            builder.append(getData());
        }
        builder.append(", errorCode=");
        builder.append(getErrorCode());
        builder.append(", errorDescription=");
        builder.append(getErrorDescription());
        builder.append("]");
        return builder.toString();
    }
*/
}
