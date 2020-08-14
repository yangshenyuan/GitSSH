package com.lanxin;

import com.lanxin.custom.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

//3.3验证 （自定义Realm）
public class CustomRealmApp {
    private String ddd;

    public static void main(String[] args) {

        CustomRealm customRealm=new CustomRealm();

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        //Shiro加密
        HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(10);//加密次数
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        //2.主体提交认证请求
        //将实例绑定给SecurityUtils
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject=SecurityUtils.getSubject();


        //验证登录登
        UsernamePasswordToken token=new UsernamePasswordToken("ysy1","123");
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
        }catch(UnknownAccountException un){
            System.out.println("账户不正确");
        }
        System.out.println("是否登录成功:"+subject.isAuthenticated());

        //验证当前用户的 权限
        try {
            subject.checkPermission("update");//没有该权限会抛出错误：UnauthorizedException
        } catch (AuthorizationException e) {
            System.out.println("没有该权限");
        }

        //验证当前用户的 角色
        try {
            subject.checkRole("system");//没有该角色会抛出错误：UnauthorizedException
        } catch (AuthorizationException e) {
            System.out.println("没有该角色");
        }
        System.out.println("角色是否是system："+subject.hasRole("system"));//返回类型为boolean


    }
}
