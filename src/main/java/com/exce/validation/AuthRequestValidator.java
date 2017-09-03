package com.exce.validation;

import com.exce.exception.CustomError;
import com.exce.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AuthRequestValidator implements Validator {
    private static final String EMPTY_USER_NAME_MESSAGE = "User name can't be empty";
    private static final String EMPTY_USER_PASSWORD_MESSAGE = "User password can't be empty";

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if(StringUtils.isBlank(user.getUsername())){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", CustomError.INVALID_REQ.getErrorCode(), EMPTY_USER_NAME_MESSAGE);
        }

        if(StringUtils.isBlank(user.getPassword())){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", CustomError.INVALID_REQ.getErrorCode(), EMPTY_USER_PASSWORD_MESSAGE);
        }
    }
}
