package com.mangajutsu.webclient.models;

import java.io.Serializable;

public class RoleModel implements Serializable {
    private Integer roleId;
    private String code;
    private String roleName;

    public RoleModel(Integer roleId, String code, String roleName) {
        this.roleId = roleId;
        this.code = code;
        this.roleName = roleName;
    }

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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // toString() //
    @Override
    public String toString() {
        return "Role [ id=" + roleId + ", code=" + code + ", roleName=" + roleName + "]";
    }
}
