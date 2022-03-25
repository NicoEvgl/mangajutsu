package com.mangajutsu.api.services;

import com.mangajutsu.api.dao.entities.UserEntity;

import org.springframework.stereotype.Service;

@Service
public interface RoleService {

    void addCustomerRole(UserEntity userEntity);
}