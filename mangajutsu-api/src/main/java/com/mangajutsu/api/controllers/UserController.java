package com.mangajutsu.api.controllers;

import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("find-user/{username}")
    public UserEntity findUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }
}