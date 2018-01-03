package com.example.springboot_shiro.service;

import com.example.springboot_shiro.domain.Users;

public interface UserManagementService {

    boolean login(Users users);

    boolean register(Users users);

    boolean logout();
}
