package com.model.guesthousebooking.Service.Impl;

import com.model.guesthousebooking.Dao.RoleDao;
import com.model.guesthousebooking.Model.Role;
import com.model.guesthousebooking.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role findByName(String name) {
        // Find role by name using the roleDao
        Role role = roleDao.findRoleByName(name);
        return role;
    }
}
