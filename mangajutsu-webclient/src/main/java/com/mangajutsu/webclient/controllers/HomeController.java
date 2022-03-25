package com.mangajutsu.webclient.controllers;

import com.mangajutsu.webclient.proxies.MangajutsuProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    MangajutsuProxy mangajutsuProxy;

    @GetMapping({ "/index", "/" })
    public String Home() {
        mangajutsuProxy.deleteUnverifiedAccount();
        return "index";
    }
}
