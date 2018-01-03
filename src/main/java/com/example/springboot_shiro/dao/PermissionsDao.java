package com.example.springboot_shiro.dao;

import com.example.springboot_shiro.domain.Permissions;
import org.springframework.stereotype.Component;

@Component
public interface PermissionsDao {
    int deleteByPrimaryKey(String permissionsId);

    int insert(Permissions record);

    int insertSelective(Permissions record);

    Permissions selectByPrimaryKey(String permissionsId);

    int updateByPrimaryKeySelective(Permissions record);

    int updateByPrimaryKey(Permissions record);
}