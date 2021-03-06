package com.mangajutsu.webclient.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.mangajutsu.webclient.validators.FieldMatchValidator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@FieldMatchValidator.List({
        @FieldMatchValidator(first = "password", second = "confirmPassword", message = "{registration.valid.confirm-password}"),
        // @FieldMatchValidator(first = "email", second = "confirmEmail", message = "The
        // email fields must match")
})

public class UserModel implements Serializable {
    private Integer id;
    @NotEmpty(message = "{registration.valid.firstName}")
    private String firstName;
    @NotEmpty(message = "{registration.valid.lastName}")
    private String lastName;
    @NotEmpty(message = "{registration.valid.username}")
    private String username;
    @NotEmpty(message = "{registration.valid.email}")
    @Email(message = "{registration.valid.email}")
    private String email;
    @NotEmpty(message = "{registration.valid.password}")
    private String password;
    @NotEmpty(message = "{registration.valid.password}")
    private String confirmPassword;
    private boolean enabled;
    private int failedLoginAttempts;
    private boolean loginDisabled;

    private Set<RoleModel> userRoles;
    private Set<AnimeModel> animes;
    private Set<MovieModel> movies;
    private List<ReviewModel> reviews;

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

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public boolean isLoginDisabled() {
        return loginDisabled;
    }

    public void setLoginDisabled(boolean loginDisabled) {
        this.loginDisabled = loginDisabled;
    }

    public Set<AnimeModel> getAnimes() {
        return animes;
    }

    public void setAnimes(Set<AnimeModel> animes) {
        this.animes = animes;
    }

    public Set<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieModel> movies) {
        this.movies = movies;
    }

    public List<ReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewModel> reviews) {
        this.reviews = reviews;
    }

    // to String() //

    @Override
    public String toString() {
        return "UserModel [confirmPassword=" + confirmPassword + ", email=" + email + ", enabled=" + enabled
                + ", failedLoginAttempts=" + failedLoginAttempts + ", firstName=" + firstName + ", id=" + id
                + ", lastName=" + lastName + ", loginDisabled=" + loginDisabled + ", password=" + password
                + ", userRoles=" + userRoles + ", username=" + username + "]";
    }

    // hashCode(), equals() //

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
