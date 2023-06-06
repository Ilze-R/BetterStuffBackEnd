package com.example.demo.repository;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;

import java.util.Collection;

public interface UserRepository <T extends User>{
//    Basic CRUD Operations
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get (Long id);
    T update(T data);
    Boolean delete (Long id);

    User getUserByEmail(String email);
    void sendVerificationCode(UserDTO user);
}
