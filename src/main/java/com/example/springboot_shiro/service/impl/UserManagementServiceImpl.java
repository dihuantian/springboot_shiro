package com.example.springboot_shiro.service.impl;

import com.example.springboot_shiro.dao.UsersDao;
import com.example.springboot_shiro.domain.Users;
import com.example.springboot_shiro.service.UserManagementService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Security;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    @Autowired
    public UsersDao usersDao;
    @Override
    public boolean login(Users users) {

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(users.getUsername(),users.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            usernamePasswordToken.setRememberMe(true);
            subject.login(usernamePasswordToken);
        }catch (UnknownAccountException uae){

        }catch (IncorrectCredentialsException ice){

        }catch (LockedAccountException lae){

        }catch (ExcessiveAttemptsException eae){

        }catch (AuthenticationException ae){

        }

        if (subject.isAuthenticated()){
            return true;
        }
        usernamePasswordToken.clear();
        return false;
    }

    @Override
    public boolean register(Users users) {

        //加密方法
        encryption(users);
        if (usersDao.insert(users)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean logout() {

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            subject.logout();
        }
        return true;
    }

    /*注册密码加密*/
    private String encryption(Users users){

        //简单的哈希加密
        SimpleHash simpleHash = new SimpleHash("MD5",users.getPassword(),users.getSalt(),3);
        users.setPassword(simpleHash.toHex());
        return simpleHash.toHex();
    }
}
