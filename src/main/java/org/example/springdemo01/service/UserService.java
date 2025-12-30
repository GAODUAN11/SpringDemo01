package org.example.springdemo01.service;

import org.example.springdemo01.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User findUserByUsername(String username);
    boolean validateUser(String username, String password);
    boolean registerUser(String username, String password);
    User createUser(String username, String password);
}