package com.mangajutsu.api.services;

import java.util.List;

import com.mangajutsu.api.dao.entities.RoleEntity;
import com.mangajutsu.api.dao.entities.UserEntity;

import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    void addCustomerRole(UserEntity user);

    List<RoleEntity> getRoles();

    RoleEntity getRoleDetails(String nameRole);
}
