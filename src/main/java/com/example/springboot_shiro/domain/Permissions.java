package com.example.springboot_shiro.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Permissions implements Serializable {
    private String permissionsId = UUID.randomUUID().toString();

    private String permissionsName;

    private String permissions;

    private String permissionsDescription;

    private String createPeople;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(String permissionsId) {
        this.permissionsId = permissionsId == null ? null : permissionsId.trim();
    }

    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName == null ? null : permissionsName.trim();
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions == null ? null : permissions.trim();
    }

    public String getPermissionsDescription() {
        return permissionsDescription;
    }

    public void setPermissionsDescription(String permissionsDescription) {
        this.permissionsDescription = permissionsDescription == null ? null : permissionsDescription.trim();
    }

    public String getCreatePeople() {
        return createPeople;
    }

    public void setCreatePeople(String createPeople) {
        this.createPeople = createPeople == null ? null : createPeople.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}