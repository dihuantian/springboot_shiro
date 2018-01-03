package com.example.springboot_shiro.dao;

import com.example.springboot_shiro.domain.UsersRole;
import org.springframework.stereotype.Component;

@Component
public interface UsersRoleDao {
    int deleteByPrimaryKey(String userRoleId);

    int insert(UsersRole record);

    int insertSelective(UsersRole record);

    UsersRole selectByPrimaryKey(String userRoleId);

    int updateByPrimaryKeySelective(UsersRole record);

    int updateByPrimaryKey(UsersRole record);
}