package com.mangajutsu.webclient.models;

import java.io.Serializable;

public class RoleModel implements Serializable {
    private Integer id;
    private String code;
    private String nameRole;

    public RoleModel(Integer id, String code, String nameRole) {
        this.id = id;
        this.code = code;
        this.nameRole = nameRole;
    }

    // Getters & Setters //

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    // toString() //
    @Override
    public String toString() {
        return "Role [ id=" + id + ", code=" + code + ", name_role=" + nameRole + "]";
    }
}
