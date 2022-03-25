package com.mangajutsu.api.emails;

import com.mangajutsu.api.dao.entities.UserEntity;

import org.springframework.web.util.UriComponentsBuilder;

public class AccountVerifEmailContext extends AbstractEmailContext {

    private String token;

    @Override
    public <T> void init(T context) {
        UserEntity user = (UserEntity) context;
        put("firstName", user.getFirstName());
        setTemplateLocation("/email-verification");
        setSubject("Activez votre compte");
        setFrom("contact.mangajutsu@gmail.com");
        setTo(user.getEmail());
    }

    public void buildVerificationUrl(final String baseURL, final String token) {
        final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/register/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }

    // Getters & Setters //

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }
}