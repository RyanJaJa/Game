package com.exce.exception;

import java.io.Serializable;

public class GoldLuckException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1308166459508211789L;
    private String errorCode;

    public GoldLuckException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
