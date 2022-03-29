package com.mangajutsu.api.emails;

import com.mangajutsu.api.dao.entities.UserEntity;

import org.springframework.web.util.UriComponentsBuilder;

public class ForgotPasswordEmailContext extends AbstractEmailContext {

    private String token;

    @Override
    public <T> void init(T context) {
        UserEntity user = (UserEntity) context; // we pass the customer informati
        put("firstName", user.getFirstName());
        setTemplateLocation("/forgot-password");
        setSubject("Modifier mot de passe");
        setFrom("${spring.mail.username}");
        setTo(user.getEmail());
    }

    public void buildVerificationUrl(final String baseURL, final String token) {
        final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/password/change").queryParam("token", token).toUriString();
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
