package com.mangajutsu.api.controllers;

import java.util.List;

import com.mangajutsu.api.dao.entities.RoleEntity;
import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.services.RoleService;
import com.mangajutsu.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/user-list")
    private List<UserEntity> getUserList() {
        List<UserEntity> users = userService.getUserList();
        return users;
    }

    @GetMapping("/roles")
    private List<RoleEntity> getRoles() {
        List<RoleEntity> roles = roleService.getRoles();
        return roles;
    }

    @GetMapping("/role-details/{nameRole}")
    private RoleEntity getRoleDetails(@PathVariable String nameRole) {
        RoleEntity role = roleService.getRoleDetails(nameRole);
        return role;
    }

    @PostMapping("/{id}/add-role")
    private void addRoleToUser(@RequestBody RoleEntity role, @PathVariable Integer id) {
        userService.addRole(role, id);
    }
}
