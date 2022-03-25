package com.mangajutsu.api.services;

import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.exceptions.UserAlreadyExistException;
import com.mangajutsu.api.models.UserModel;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void register(UserModel userModel) throws UserAlreadyExistException;

    boolean checkIfUserExist(String username);

    UserEntity findByUsername(String username);

    void sendAccountVerifEmail(UserEntity user);
}