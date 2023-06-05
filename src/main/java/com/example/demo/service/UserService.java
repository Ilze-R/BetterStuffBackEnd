package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);
}
