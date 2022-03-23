package com.mangajutsu.webclient.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.mangajutsu.webclient.validators.FieldMatchValidator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@FieldMatchValidator.List({
        @FieldMatchValidator(first = "password", second = "confirmPassword", message = "{registration.validation.confirm-password}"),
        // @FieldMatchValidator(first = "email", second = "confirmEmail", message = "The
        // email fields must match")
})

public class UserModel implements Serializable {
    private Integer id;
    @NotEmpty(message = "{registration.validation.firstName}")
    private String firstName;
    @NotEmpty(message = "{registration.validation.lastName}")
    private String lastName;
    @NotEmpty(message = "{registration.validation.username}")
    private String username;
    @NotEmpty(message = "{registration.validation.email}")
    @Email(message = "{registration.validation.email}")
    private String email;
    @NotEmpty(message = "{registration.validation.password}")
    private String password;
    @NotEmpty(message = "{registration.validation.password}")
    private String confirmPassword;
    private boolean enabled;

    private Set<RoleModel> userRoles;

    public Collection<? extends GrantedAuthority> getAuthorities(Set<RoleModel> userRoles) {
        Collection<GrantedAuthority> authorities = new ArrayList<>(userRoles.size());
        for (RoleModel role : userRoles) {
            authorities.add(new SimpleGrantedAuthority(role.getNameRole().toUpperCase()));
        }
        return authorities;
    }

    // Getters and Setters //

    public Integer getId() {
        return id;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<RoleModel> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<RoleModel> userRoles) {
        this.userRoles = userRoles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // to String() //

    @Override
    public String toString() {
        return "User [email=" + email + ", enabled=" + enabled + ", firstName=" + firstName + ", id=" + id
                + ", lastName=" + lastName + ", password=" + password + ", userRoles=" + userRoles + ", username="
                + username + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserModel other = (UserModel) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
}