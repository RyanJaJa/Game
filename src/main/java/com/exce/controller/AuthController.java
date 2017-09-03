package com.exce.controller;

import com.exce.dto.JwtAuthenticationResponse;
import com.exce.exception.CustomError;
import com.exce.exception.GoldLuckException;
import com.exce.model.ResponsePayload;
import com.exce.model.User;
import com.exce.service.AuthService;
import com.exce.service.UserService;
import com.exce.util.JwtTokenUtil;
import com.exce.validation.AuthRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class AuthController {
    private static Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new AuthRequestValidator());
    }

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponsePayload<String> createAuthenticationToken(@Valid @RequestBody User authenticationRequest, Device device) throws AuthenticationException {
        log.info("Controller - createAuthenticationToken request {} , {} ", authenticationRequest, device);
        ResponsePayload<String> payload = new ResponsePayload<>();
        try {
            // Perform the security
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload password post-security so we can generate token
            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final String token = jwtTokenUtil.generateToken(userDetails, device);

            payload.setData(token);
            payload.setErrorCode(CustomError.OK.getErrorCode());
            payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - createAuthenticationToken error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - createAuthenticationToken error : {} ", e.getMessage());
        }
        log.info("Controller - createAuthenticationToken response : {} ", payload.toString());
        // Return the token
        return payload;
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponsePayload<JwtAuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        log.info("Controller - refreshAndGetAuthenticationToken request {} ", request.getHeader(tokenHeader));
        ResponsePayload<JwtAuthenticationResponse> payload = new ResponsePayload<>();
        try {
            String token = request.getHeader(tokenHeader);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            User user = (User) userService.loadUserByUsername(username);

            if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetTime().getTime())) {
                String refreshedToken = jwtTokenUtil.refreshToken(token);

                payload.setData(new JwtAuthenticationResponse(refreshedToken));
                payload.setErrorCode(CustomError.OK.getErrorCode());
                payload.setErrorDescription(CustomError.OK.getErrorDesc());
            } else {
                payload.setErrorCode(CustomError.INVALID_REQ.getErrorCode());
                payload.setErrorDescription(CustomError.INVALID_REQ.getErrorDesc());
            }

        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - refreshAndGetAuthenticationToken error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - refreshAndGetAuthenticationToken error : {} ",  e.getMessage());
        }
        log.info("Controller - refreshAndGetAuthenticationToken response : {} ", payload.toString());
        // Return the token
        return payload;
    }

    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
    public @ResponseBody
    ResponsePayload<User> register(@Valid @RequestBody User addedUser) {
        log.info("Controller - register request {}", addedUser);
        ResponsePayload<User> payload = new ResponsePayload<>();
        try {
            payload.setData(authService.register(addedUser));
            payload.setErrorCode(CustomError.OK.getErrorCode());
            payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - register error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - register error : {} ", e.getMessage());
        }
        log.info("Controller - register response : {} ", payload.toString());
        return payload;
    }

}
