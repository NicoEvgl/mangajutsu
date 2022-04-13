package com.mangajutsu.api.services;

import java.util.List;

import com.mangajutsu.api.dao.entities.RoleEntity;
import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.exceptions.UnknownIdentifierException;
import com.mangajutsu.api.exceptions.UserAlreadyExistException;
import com.mangajutsu.api.models.UserModel;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void register(UserModel userModel) throws UserAlreadyExistException;

    boolean checkIfUserExist(String username);

    UserEntity findByUsername(String username);

    UserEntity getUserDetails(Integer id);

    List<UserEntity> getUserList();

    void sendAccountVerifEmail(UserEntity user);

    void sendResetPasswordEmail(UserEntity user);

    boolean loginDisabled(String username);

    void updateUser(UserEntity user, Integer id) throws UnknownIdentifierException;

    void addRole(RoleEntity role, Integer id);
}
