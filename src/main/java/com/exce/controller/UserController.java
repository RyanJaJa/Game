package com.exce.controller;

import com.exce.exception.CustomError;
import com.exce.exception.GoldLuckException;
import com.exce.model.ResponsePayload;
import com.exce.model.User;
import com.exce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
//@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponsePayload<List<User>> getUsers() {
        log.info("Controller - getUsers request ");
        ResponsePayload<List<User>> payload = new ResponsePayload<>();
        List<User> users;
        try {
            users = userService.findAll();
            payload.setData(users);
            payload.setErrorCode(CustomError.OK.getErrorCode());
            payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - getUsers error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - getUsers error : {} ", e.getMessage());
        }
        log.info("Controller - getUsers response : {} ", payload.toString());
        return payload;
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponsePayload<User> getUserInfo() {
        log.info("Controller - getUserInfo request ");
        ResponsePayload<User> payload = new ResponsePayload<>();
        User user;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            payload.setData(user);
            payload.setErrorCode(CustomError.OK.getErrorCode());
            payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - getUserInfo error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - getUserInfo error : {} ", e.getMessage());
        }
        log.info("Controller - getUserInfo response : {} ", payload.toString());
        return payload;
    }
}
