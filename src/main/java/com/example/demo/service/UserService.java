package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);
    UserDTO getUserByEmail(String email);
    void sendVerificationCode(UserDTO user);

    UserDTO verifyCode(String email, String code);
}