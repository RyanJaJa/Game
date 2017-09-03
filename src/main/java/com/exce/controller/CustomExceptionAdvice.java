package com.exce.controller;

import com.exce.exception.CustomError;
import com.exce.model.ResponsePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class CustomExceptionAdvice {
    private static Logger log = LoggerFactory.getLogger(CustomExceptionAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponsePayload handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ResponsePayload payload = new ResponsePayload();
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();

        payload.setErrorCode(CustomError.INVALID_REQ.getErrorCode());
        payload.setErrorDescription(fieldError.getDefaultMessage());
        log.error("handleMethodArgumentNotValidException : {} ",e.getMessage());
        return payload;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponsePayload handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ResponsePayload payload = new ResponsePayload();
        payload.setErrorCode(CustomError.INVALID_REQ.getErrorCode());
        payload.setErrorDescription(CustomError.INVALID_REQ.getErrorDesc());
        log.error("handleHttpMessageNotReadableException : {} ",e.getMessage());
        return payload;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponsePayload handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        ResponsePayload payload = new ResponsePayload();
        payload.setErrorCode(CustomError.INVALID_REQ.getErrorCode());
        payload.setErrorDescription(CustomError.INVALID_REQ.getErrorDesc());
        log.error("handleMissingServletRequestParameterException : {} ",e.getMessage());
        return payload;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponsePayload handleIllegalArgumentException(IllegalArgumentException e) {
        ResponsePayload payload = new ResponsePayload();
        payload.setErrorCode(CustomError.INVALID_REQ.getErrorCode());
        payload.setErrorDescription(CustomError.INVALID_REQ.getErrorDesc());
        log.error("handleIllegalArgumentException : {} ",e.getMessage());
        return payload;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponsePayload handleUsernameNotFoundException(UsernameNotFoundException e) {
        ResponsePayload payload = new ResponsePayload();
        payload.setErrorCode(CustomError.NOT_FOUND_USER.getErrorCode());
        payload.setErrorDescription(CustomError.NOT_FOUND_USER.getErrorDesc());
        log.error("handleUsernameNotFoundException : {} ",e.getMessage());
        return payload;
    }

}
