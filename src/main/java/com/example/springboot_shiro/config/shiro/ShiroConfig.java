package com.example.springboot_shiro.config.shiro;

import com.example.springboot_shiro.enums.ShiroTimeoutEnum;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.HashMap;
import java.util.Map;

/*
 * 第一个坑，Shiro频繁和Redis进行Session通信，导致SessionId频繁更改，原因暂时是SessionManager设置检查Session状态(改:将SessionManager检查时间设置长一点)
 * 第二个坑，第一个坑的补充重写url并加上JSESSIONID。
 * 第三个坑，没有设置rememberMeManager，导致shiro记住我功能报异常，秘钥长度不匹配。
 * */
@Configuration
public class ShiroConfig {

    @Autowired
    private ShiroSessionDao shiroSessionDao;

    /*Shiro过滤器设置*/
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //成功登录后跳转的连接
        shiroFilterFactoryBean.setSuccessUrl("/Home/Index");
        //登录页面
        shiroFilterFactoryBean.setLoginUrl("/Login");
        //设置未经授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("Error/403");

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        // 配置不会被拦截的链接
        filterChainDefinitionMap.put("/Login/LoginVerification", "anon");
        filterChainDefinitionMap.put("/Register", "anon");
        filterChainDefinitionMap.put("/Register/RegisterVerification", "anon");
        filterChainDefinitionMap.put("/css/*", "anon");
        filterChainDefinitionMap.put("/js/*", "anon");
        filterChainDefinitionMap.put("/image/*", "anon");
        filterChainDefinitionMap.put("/Home/Index", "authc");
        // 配置退出过滤器

        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /*安全管理器设置*/
    @Bean
    public SecurityManager securityManager() {

        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(shiroRealm());
        defaultSecurityManager.setSessionManager(sessionManager());
        defaultSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultSecurityManager;
    }

    /*Session管理*/
    @Bean
    public SessionManager sessionManager() {

        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(shiroSessionDao);
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        defaultWebSessionManager.setGlobalSessionTimeout(ShiroTimeoutEnum.SHRIO_TIMEOUT.getTime());
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionValidationInterval(ShiroTimeoutEnum.SHIRO_EXAMINE_TIME.getTime());
        defaultWebSessionManager.setSessionIdUrlRewritingEnabled(true);
        return defaultWebSessionManager;
    }

    /*Cookie模板*/
    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(Cookie.ONE_YEAR);
        return simpleCookie;
    }

    /*RememberMe管理*/
    @Bean
    public CookieRememberMeManager  rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /*Shiro的AOP支持*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {

        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /*Shiro数据访问层设置，认证和授权*/
    @Bean
    public CustomShiroRealm shiroRealm() {

        CustomShiroRealm realm = new CustomShiroRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    /*密码匹配器*/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {

        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(3);
        return hashedCredentialsMatcher;
    }
}
