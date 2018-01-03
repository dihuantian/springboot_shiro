package com.example.springboot_shiro.domain;

import java.io.Serializable;
import java.util.UUID;

public class RolePermissions implements Serializable {
    private String rolePermissionsId = UUID.randomUUID().toString();

    private String role_id;

    private String permissions_id;

    private static final long serialVersionUID = 1L;

    public String getRolePermissionsId() {
        return rolePermissionsId;
    }

    public void setRolePermissionsId(String rolePermissionsId) {
        this.rolePermissionsId = rolePermissionsId == null ? null : rolePermissionsId.trim();
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id == null ? null : role_id.trim();
    }

    public String getPermissions_id() {
        return permissions_id;
    }

    public void setPermissions_id(String permissions_id) {
        this.permissions_id = permissions_id == null ? null : permissions_id.trim();
    }
}