package com.example.springboot_shiro.domain;

import java.io.Serializable;
import java.util.UUID;

public class UsersRole implements Serializable {
    private String userRoleId = UUID.randomUUID().toString();

    private String user_id;

    private String role_id;

    private static final long serialVersionUID = 1L;

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId == null ? null : userRoleId.trim();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id == null ? null : role_id.trim();
    }

}