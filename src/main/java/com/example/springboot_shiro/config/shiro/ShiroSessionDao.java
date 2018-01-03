package com.example.springboot_shiro.config.shiro;

import com.example.springboot_shiro.enums.ShiroTimeoutEnum;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.sql.Time;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/*
 * 第一个坑，redisTemplate为null，首先怀疑RedisConfig中的Bean(name="redisTemplate")没有比ShiroConfig的Bean(name="securityManager")先初始化(错误)
 * 第二个坑，将redisTemplate转移到Controller和Service层测试发现redisTemplate已经被注入实例了，验证了第一个坑的正确，redisTemplate的确被初始化了
 * 第三个坑，第一个和第二个坑怀疑的重点在于@Configuration配置类的加载顺序上(错误)
 * 第四个坑，(修复)关键在"类的实例化和初始化顺序上出现了问题"，ShiroConfig中Bean(name="securityManager")中设置setSessionDao时，参数只是实例化，但是并没有
 * 进行初始化并且进行依赖注入属性，导致redisTemplate为空。应该警惕。
 * 第五个坑，频繁和Redis通信并更新Session最后访问时间(导致SessionId更改?)
 * 第六个坑，细节问题，Shiro 的Session过期时间和Redis的过期时间搞混了，Shiro是毫秒，Redis是秒。(总之被时间给坑了)
 * 第七个坑，在使用记住我的时候，没有加上关于setRememberMe的配置，导致Session带回setRememberMe的Cookie无法识别，从而丢失setRememberMe的Cookie和Redis数据
 * */
@Service
public class ShiroSessionDao extends EnterpriseCacheSessionDAO {

    private long redisExpireTime = ShiroTimeoutEnum.REDIS_TIMEOUT.getTime();

    private long shiroExpireTime = ShiroTimeoutEnum.SHRIO_TIMEOUT.getTime();

    private static final String KEY_PREFIX = "shiro_redis_session:";

    @Autowired(required = true)
    public RedisTemplate<String,Object> redisTemplate;

    public ShiroSessionDao() {
        super();
    }

    @Override
    protected Serializable doCreate(Session session) {

        Serializable sessionId = super.doCreate(session);
        ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
        operations.set((KEY_PREFIX + session.getId()).toString(), session, redisExpireTime, TimeUnit.SECONDS);
        this.assignSessionId(session, sessionId);
        System.out.println("创建：" + session.getId() + ",剩余时间：" + session.getTimeout());
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {

        Session session = super.doReadSession(sessionId);
        if (session == null) {
            String key = KEY_PREFIX + sessionId;
            session = (Session) redisTemplate.opsForValue().get(KEY_PREFIX + sessionId);
            System.out.println("Redis读取：" + sessionId);
        } else {
            System.out.println("Cache读取：" + sessionId + ",剩余时间：" + session.getTimeout());
        }
        return session;
    }

    @Override
    protected void doUpdate(Session session) throws UnknownSessionException {

        if (session == null || session.getId() == null) {
            return;
        }
        System.out.println("更新：" + session.getId() + ",剩余时间：" + session.getTimeout());
        session.setTimeout(shiroExpireTime);
        redisTemplate.opsForValue().set(KEY_PREFIX + session.getId(), session, redisExpireTime, TimeUnit.SECONDS);
    }

    @Override
    protected void doDelete(Session session) {

        if (session == null || session.getId() == null) {
            return;
        }
        redisTemplate.opsForValue().getOperations().delete(KEY_PREFIX + session.getId());
        System.out.println("删除：" + session.getId() + ",剩余时间：" + session.getTimeout());
    }

}
