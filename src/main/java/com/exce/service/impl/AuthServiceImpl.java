package com.exce.service.impl;

import com.exce.exception.CustomError;
import com.exce.exception.GoldLuckException;
import com.exce.model.User;
import com.exce.repository.UserRepository;
import com.exce.service.AuthService;
import com.exce.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class AuthServiceImpl implements AuthService {
    private static Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    public User register(User newUser) throws Exception {
        User user;
        try{
            final String username = newUser.getUsername();
            if (userRepository.findByUsername(username).isPresent()) {
                throw new GoldLuckException(CustomError.PLAYER_REGISTER_ERROR.getErrorCode(),CustomError.PLAYER_REGISTER_ERROR.getErrorDesc());
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            final String rawPassword = newUser.getPassword();
            newUser.setPassword(encoder.encode(rawPassword));
            newUser.setLastPasswordResetTime(Calendar.getInstance());
            //newUser.setRoles(asList("ROLE_USER"));
            user = userRepository.save(newUser);
        } catch (Exception e) {
            log.error("Service - register error message : ", e);
            throw e;
        }
        return user;
    }

    @Override
    public String login(String username, String password, Device device) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails, device);
        return token;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = (User) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetTime().getTime())) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
