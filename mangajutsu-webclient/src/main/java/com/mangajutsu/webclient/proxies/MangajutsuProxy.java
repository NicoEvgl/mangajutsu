package com.mangajutsu.webclient.proxies;

import java.util.List;

import com.mangajutsu.webclient.models.AnimeModel;
import com.mangajutsu.webclient.models.UserModel;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mangajutsu-api", url = "localhost:9090")
public interface MangajutsuProxy {

    @GetMapping("find-user/{username}")
    UserModel findUserByUsername(@PathVariable("username") String username);

    @PostMapping("/register")
    void userRegistration(@RequestBody UserModel user);

    @GetMapping("/register/verify")
    boolean verifyAccount(@RequestParam("token") String token);

    @DeleteMapping("/delete-unverified-account")
    void deleteUnverifiedAccount();

    @PostMapping("/register-login-failure")
    void registerLoginFailure(@RequestBody String username);

    @PostMapping("/reset-bruteforce-counter")
    void resetBruteForceCounter(@RequestBody String username);

    @GetMapping("/login-disabled/{username}")
    boolean loginDisabled(@PathVariable String username);

    @PostMapping("/password/reset")
    void resetPassword(@RequestParam("email") String email);

    @PostMapping("password/change")
    void changePassword(@RequestParam("password") String password, @RequestParam("token") String token);

    @GetMapping("/anime/anime-list")
    List<AnimeModel> getAnimeList();

    @GetMapping("/anime/anime-details/{title}")
    AnimeModel getAnimeDetails(@PathVariable String title);

    @PostMapping("/anime/add-anime")
    void addAnime(@RequestBody AnimeModel anime, @RequestParam("username") String username);

    @PostMapping("/anime/update-anime/{title}")
    void updateAnime(@RequestBody AnimeModel anime, @PathVariable String title);

    @GetMapping("/enum/types")
    List<String> getTypes();

    @GetMapping("/enum/status")
    List<String> getStatus();

    @GetMapping("/enum/genres")
    List<String> getGenres();
}
