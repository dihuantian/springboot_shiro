package com.example.springboot_shiro.dao;

import com.example.springboot_shiro.domain.Permissions;
import com.example.springboot_shiro.domain.Users;
import org.springframework.stereotype.Component;

@Component
public interface UsersDao {
    int deleteByPrimaryKey(String userId);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    Users selectByUserName(String username);

}