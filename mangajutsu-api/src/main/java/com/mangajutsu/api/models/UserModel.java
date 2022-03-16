package com.mangajutsu.api.models;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserModel implements Serializable {
    private Integer userId;
    @NotEmpty(message = "Le prénom doit être saisi")
    private String firstName;
    @NotEmpty(message = "Le nom doit être saisi")
    private String lastName;
    @NotEmpty(message = "Le pseudo doit être saisi")
    private String username;
    @NotEmpty(message = "L'email doit être saisi")
    @Email(message = "Veuillez renseigner une adresse email valide")
    private String email;
    @NotEmpty(message = "Le mot de passe doit être saisi")
    private String password;

    // GETTERS AND SETTERS //

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
