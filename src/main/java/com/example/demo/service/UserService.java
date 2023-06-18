package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);
    UserDTO getUserByEmail(String email);
    void sendVerificationCode(UserDTO user);

    UserDTO verifyCode(String email, String code);

    void resetPassword(String email);

    UserDTO verifyPasswordKey(String key);

    void renewPasswordKey(String key, String password, String confirmPassword);

    UserDTO verifyAccountKey(String key);
}
