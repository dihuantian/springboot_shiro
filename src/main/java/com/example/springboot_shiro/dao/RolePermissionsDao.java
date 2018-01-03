package com.example.springboot_shiro.dao;

import com.example.springboot_shiro.domain.RolePermissions;
import org.springframework.stereotype.Component;

@Component
public interface RolePermissionsDao {
    int deleteByPrimaryKey(String rolePermissionsId);

    int insert(RolePermissions record);

    int insertSelective(RolePermissions record);

    RolePermissions selectByPrimaryKey(String rolePermissionsId);

    int updateByPrimaryKeySelective(RolePermissions record);

    int updateByPrimaryKey(RolePermissions record);
}