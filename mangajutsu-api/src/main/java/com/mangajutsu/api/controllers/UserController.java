package com.mangajutsu.api.controllers;

import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.exceptions.UserAlreadyExistException;
import com.mangajutsu.api.models.UserModel;
import com.mangajutsu.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public void userRegistration(@RequestBody UserModel user) throws UserAlreadyExistException {
        userService.register(user);
    }

    @GetMapping("find-user/{username}")
    public UserEntity findUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }
}
