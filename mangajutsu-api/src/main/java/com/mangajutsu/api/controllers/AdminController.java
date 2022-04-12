package com.mangajutsu.api.controllers;

import java.util.List;

import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/user-list")
    private List<UserEntity> getUserList() {
        List<UserEntity> users = userService.getUserList();
        return users;
    }

}
