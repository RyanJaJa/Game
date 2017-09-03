package com.exce.service;

import com.exce.model.User;
import org.springframework.mobile.device.Device;

public interface AuthService {

    User register(User newUser) throws Exception;

    String login(String username, String password, Device device);

    String refresh(String oldToken);
}
