package com.mangajutsu.webclient.proxies;

import com.mangajutsu.webclient.models.UserModel;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mangajutsu-api", url = "localhost:9090")
public interface MangajutsuProxy {

    @GetMapping("find-user/{username}")
    UserModel findUserByUsername(@PathVariable("username") String username);

    @PostMapping("/register")
    void userRegistration(UserModel user);
}
