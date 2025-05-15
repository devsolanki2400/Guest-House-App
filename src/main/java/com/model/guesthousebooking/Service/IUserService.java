package com.model.guesthousebooking.service;

import java.util.List;

import com.model.guesthousebooking.model.User;



public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}