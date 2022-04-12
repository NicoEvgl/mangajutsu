package com.mangajutsu.webclient.controllers;

import java.util.ArrayList;
import java.util.List;

import com.mangajutsu.webclient.models.UserModel;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @GetMapping("/user-list")
    private String userList(final Model model) {
        List<UserModel> users = new ArrayList<>();
        users = mangajutsuProxy.getUserList();
        model.addAttribute("users", users);
        return "admin/user_list";
    }
}
