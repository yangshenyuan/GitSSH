package com.lanxin.config;

import com.lanxin.custom.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//shiro配置类
//把配置交给IOC容器管理
@Component
public class ShiroCinfig {

    //自定义Realm
    @Bean
    public CustomRealm customRealm(HashedCredentialsMatcher hashedCredentialsMatcher){
        CustomRealm customRealm=new CustomRealm();
        //设置Shiro加密
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return customRealm;
    }


    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    //配置Shiro RedisManager
    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager=new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(1800);//配置缓存过期时间
        return redisManager;
    }

    //Shiro+redis缓存管理 ，使用的是配置Shiro—redis开源插件
    //作用：缓存管理，解决shiro和数据库频繁交互
    //cacheManager 缓存 redis实现
    @Bean
    public RedisCacheManager redisCacheManager(RedisManager redisManager){
        RedisCacheManager redisCacheManager=new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    //session管理
    //使用的是配置Shiro—redis开源插件
    //作用：解决集群或分布式架构（需重复登录的问题，完成session持久化到redis中）
    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager){
        RedisSessionDAO redisSessionDAO=new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }
    @Bean//session Manager
    public DefaultWebSessionManager defaultWebSessionManager(RedisSessionDAO redisSessionDAO){
        DefaultWebSessionManager defaultWebSessionManager=new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(redisSessionDAO);
        return defaultWebSessionManager;
    }



    //过滤器工厂 （设置对应的过滤条件和跳转条件）
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String,String> map=new HashMap<String,String>();
        //顺序不能颠倒
        //对controller路径进行拦截
        map.put("/login","anon");//不会被拦截的路径 anon表示匿名登录（无需登录）
        map.put("/logout","anon");
        map.put("/**","authc");//被拦截的路径为所有（除了/login和/logout）

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        //设置被拦截后默认跳转的路径
        shiroFilterFactoryBean.setLoginUrl("/unauth");

        return shiroFilterFactoryBean;
    }

    //安全管理器
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm, RedisCacheManager redisCacheManager,DefaultWebSessionManager defaultWebSessionManager){
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        //设置自定义realm
        defaultWebSecurityManager.setRealm(customRealm);
        //设置自定义缓存实现 使用redis
        defaultWebSecurityManager.setCacheManager(redisCacheManager);
        //设置自定义session管理 使用redis
        defaultWebSecurityManager.setSessionManager(defaultWebSessionManager);
        return defaultWebSecurityManager;
    }

    //Shiro加密
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法  md5算法
        hashedCredentialsMatcher.setHashIterations(10);//加密次数
        return hashedCredentialsMatcher;
    }


    //开启后 支持Controller层注解实现权限控制 注解@RequiresPermissions
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    //开启shiro aop注解支持
    //开启自动代理器（可以代理没有实现接口的类）
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor=new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }



}
