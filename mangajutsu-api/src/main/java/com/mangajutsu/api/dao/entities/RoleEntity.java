package com.mangajutsu.api.dao.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Integer roleId;
    @Column(name = "code", unique = true)
    private String code;
<<<<<<< HEAD
    @Column(name = "name_role", unique = true)
    private String nameRole;
=======
    @Column(name = "role_name", unique = true)
    private String roleName;
>>>>>>> 3368934ee88b2f8978dcd2b3c3a06044701e6350

    @JsonIgnore
    @ManyToMany(mappedBy = "userRoles")
    private Set<UserEntity> users;

    // Getters & Setters //
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

<<<<<<< HEAD
    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
=======
    public String getRoleName() {
        return roleName;
    }

    public void setRole_name(String roleName) {
        this.roleName = roleName;
>>>>>>> 3368934ee88b2f8978dcd2b3c3a06044701e6350
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
