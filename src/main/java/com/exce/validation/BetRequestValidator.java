package com.exce.validation;

import com.exce.dto.Account;
import com.exce.exception.CustomError;
import com.exce.model.BetOrder;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

public class BetRequestValidator implements Validator {

    private static final String EMPTY_USER_MESSAGE = "User object can't be empty";
    private static final String EMPTY_GAME_MESSAGE = "Game object can't be empty";
    private static final String BETTOTALAMOUNT_NEGATIVE_MESSAGE = "BetTotalAmount can't be negative or empty";
    private static final String EMPTY_BETORDERDETAILS_MESSAGE = "BetOrderDetail list can't be empty";

    @Override
    public boolean supports(Class<?> clazz) { return BetOrder.class.isAssignableFrom(clazz); }

    @Override
    public void validate(Object target, Errors errors) {

        BetOrder betorder = (BetOrder) target;
        //若request沒有提供player
        if (betorder.getPlayer() == null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "player", CustomError.INVALID_REQ.getErrorCode(), EMPTY_USER_MESSAGE);
        }
        //若request沒有提供game
        if (betorder.getGame() == null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "game", CustomError.INVALID_REQ.getErrorCode(), EMPTY_GAME_MESSAGE);
        }
        //若request沒有提供betTotalAmount或是betTotalAmount小於0
        if (betorder.getBetTotalAmount() == null || betorder.getBetTotalAmount().compareTo(BigDecimal.ZERO)==-1) {
            errors.rejectValue("betTotalAmount", CustomError.INVALID_REQ.getErrorCode(), BETTOTALAMOUNT_NEGATIVE_MESSAGE);
        }
        //若request沒有提供betOrderDetails
        if (betorder.getBetOrderDetails().isEmpty() && betorder.getBetOrderDetails()==null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "betOrderDetails", CustomError.INVALID_REQ.getErrorCode(), EMPTY_BETORDERDETAILS_MESSAGE);
        }

    }

}
