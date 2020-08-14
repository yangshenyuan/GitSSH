package com.lanxin.controller;

import com.lanxin.config.IlanxinResult;
import com.lanxin.config.LanxinResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    //登录
    @RequestMapping(value = "/login")//http://localhost:8080/login?username=ysy1&password=123
    public LanxinResult login(String username,String password){

        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);

        try {
            subject.login(token);
            System.out.println("登录成功");
            return LanxinResult.success(IlanxinResult.SUCCESS_LOGIN,IlanxinResult.SUCCESS_LOGIN_MSG);

        } catch (IncorrectCredentialsException e1) {
            return LanxinResult.fail(5000,"密码错误");

        }catch (AuthenticationException e) {
            return LanxinResult.fail(5001,"用户不存在");
        }
    }

    //退出
    @RequestMapping(value = "/logout")
    public LanxinResult logout(){
        Subject subject= SecurityUtils.getSubject();
        subject.logout();//退出
        return LanxinResult.success(IlanxinResult.SUCCESS_LOGOUT,IlanxinResult.SUCCESS_LOGOUT_MSG);
    }


    @RequestMapping(value = "/unauth")
    public LanxinResult unauth(){
        Subject subject= SecurityUtils.getSubject();
        subject.logout();//退出
        return LanxinResult.fail(4545,"请先登录");
    }

    @RequiresRoles("system")//    验证当前用户的 角色 有system角色才可以访问此方法
    @RequiresPermissions("insert")//验证当前用户的 权限 有insert权限才可以访问此方法
    @RequestMapping(value = "/insert")
    public LanxinResult insert(){
        return LanxinResult.success("执行成功");
    }


    @RequiresPermissions("update")//验证当前用户的 权限
    @RequestMapping(value = "/update")
    public LanxinResult update(){
        return LanxinResult.success("执行成功");
    }


    @RequiresPermissions("delete")//验证当前用户的 权限
    @RequestMapping(value = "/delete")
    public LanxinResult delete(){
        return LanxinResult.success("执行成功");
    }


    @RequiresPermissions("select")//验证当前用户的 权限
    @RequestMapping(value = "/select")
    public LanxinResult select(){
        return LanxinResult.success("执行成功");
    }

}
