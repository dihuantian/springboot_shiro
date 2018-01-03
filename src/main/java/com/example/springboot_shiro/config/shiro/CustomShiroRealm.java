package com.example.springboot_shiro.config.shiro;

import com.example.springboot_shiro.dao.UsersDao;
import com.example.springboot_shiro.domain.Permissions;
import com.example.springboot_shiro.domain.Role;
import com.example.springboot_shiro.domain.Users;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/*
* 第一个困难，Mybatis使用不熟练，硬是去恶补了一下，多表查询，单表查询，嵌套查询一番，终于解决了(Hibernate，唉)
* */
public class CustomShiroRealm extends AuthorizingRealm {

    @Autowired
    public UsersDao usersDao;

    /*授权*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        Users users = (Users) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : users.getRoles()){
            simpleAuthorizationInfo.addRole(role.getRoleId());
            for (Permissions permissions : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissions());
            }
        }
        return simpleAuthorizationInfo;
    }

    /*认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String) authenticationToken.getPrincipal();
        Users user = usersDao.selectByUserName(username);
        if (user != null) {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
