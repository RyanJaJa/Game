package com.exce.validation;

import com.exce.dto.Account;
import com.exce.exception.CustomError;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

public class StoredValueRequestValidator implements Validator {
    private static final String USER_ID_ERROR_MESSAGE = "User id should be positive or not be empty";
    private static final String EMPTY_BALANCE_MESSAGE = "Balance object can't be empty";
    private static final String EMPTY_AMOUNT_MESSAGE = "Transfer amount can't be empty";
    private static final String AMOUNT_NEGATIVE_MESSAGE = "TransferAmount can't be negative";

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;
        //若request沒有提供userId
        if (account.getUserId() == null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", CustomError.INVALID_REQ.getErrorCode(), USER_ID_ERROR_MESSAGE);
        }
        //若request沒有提供balance
        if (account.getBalance() == null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "balance", CustomError.INVALID_REQ.getErrorCode(), EMPTY_BALANCE_MESSAGE);
        }
        //若request沒有提供transferAmount
        if (account.getBalance() != null && account.getBalance().getTransferAmount() == null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "balance.transferAmount", CustomError.INVALID_REQ.getErrorCode(), EMPTY_AMOUNT_MESSAGE);
        }
        //若deposit/withdraw時，transferAmount < 0
        if (account.getBalance() != null && account.getBalance().getTransferAmount() != null && account.getBalance().getTransferAmount().compareTo(BigDecimal.ZERO) < 0) {
            errors.rejectValue("balance.transferAmount", CustomError.INVALID_REQ.getErrorCode(), AMOUNT_NEGATIVE_MESSAGE);
        }
    }
}
