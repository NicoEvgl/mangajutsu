package com.mangajutsu.webclient.models;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.mangajutsu.webclient.validators.FieldMatchValidator;

@FieldMatchValidator.List({
        @FieldMatchValidator(first = "password", second = "confirmPassword", message = "{registration.validation.confirm-password}"),
})

public class ForgotPasswordModel implements Serializable {

    private String email;
    private String token;
    @NotEmpty(message = "{registration.validation.password}")
    private String password;
    @NotEmpty(message = "{registration.validation.password}")
    private String confirmPassword;

    // Getters & Setters //

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
