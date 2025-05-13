package com.model.guesthousebooking.Service;

import com.model.guesthousebooking.Model.User;
import com.model.guesthousebooking.Model.UserDto;

import java.util.List;

public interface UserService {

    // Saves a user
    User save(UserDto user);

    // Retrieves all users
    List<User> findAll();

    // Retrieves a user by username
    User findOne(String username);

    User createEmployee(UserDto user);

}