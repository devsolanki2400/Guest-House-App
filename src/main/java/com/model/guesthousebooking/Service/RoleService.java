package com.model.guesthousebooking.Service;

// Importing the Role model
import com.model.guesthousebooking.Model.Role;

// Declaring the RoleService interface
public interface RoleService {
    // Method to find a Role by its name
    Role findByName(String name);
}
