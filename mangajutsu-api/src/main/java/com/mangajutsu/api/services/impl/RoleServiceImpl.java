package com.mangajutsu.api.services.impl;

import java.util.List;

import com.mangajutsu.api.dao.entities.RoleEntity;
import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.repositories.RoleRepository;
import com.mangajutsu.api.services.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void addCustomerRole(UserEntity user) {
        RoleEntity role = roleRepository.findByNameRole("customer_role");
        user.getUserRoles().add(role);
        role.getUsers().add(user);
    }

    @Override
    public List<RoleEntity> getRoles() {
        List<RoleEntity> roles = (List<RoleEntity>) roleRepository.findAll();
        return roles;
    }

    @Override
    public RoleEntity getRoleDetails(String nameRole) {
        RoleEntity role = roleRepository.findByNameRole(nameRole);
        return role;
    }
}
