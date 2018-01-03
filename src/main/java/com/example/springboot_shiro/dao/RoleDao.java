package com.example.springboot_shiro.dao;

import com.example.springboot_shiro.domain.Role;
import org.springframework.stereotype.Component;

@Component
public interface RoleDao {
    int deleteByPrimaryKey(String roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}