package com.model.guesthousebooking.service;

import java.util.List;

import com.model.guesthousebooking.model.Role;
import com.model.guesthousebooking.model.User;



public interface IRoleService {
    List<Role> getRoles();
    Role createRole(Role theRole);

    void deleteRole(Long id);
    Role findByName(String name);

    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId);
    Role removeAllUsersFromRole(Long roleId);
}