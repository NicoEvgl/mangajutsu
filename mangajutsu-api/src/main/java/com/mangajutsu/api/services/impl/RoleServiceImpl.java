package com.mangajutsu.api.services.impl;

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
    public void addCustomerRole(UserEntity userEntity) {
        RoleEntity role = roleRepository.findByNameRole("customer_role");
        userEntity.getUserRoles().add(role);
        role.getUsers().add(userEntity);
    }
}
