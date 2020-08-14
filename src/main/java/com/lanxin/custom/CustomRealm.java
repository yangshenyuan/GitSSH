package com.lanxin.custom;

import com.lanxin.dao.RoleDao;
import com.lanxin.util.SqlSessionUtil;
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

import java.util.Set;

/**
 * Created by Administrator on 2020/8/11 0011.
 */

//自定义Realm
public class CustomRealm extends AuthorizingRealm {


    @Autowired
    private RoleDao roleDao;


    //封装授权信息（权限）
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //得到用户界面上填写的用户名
        String username= (String) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();

        //根据用户名去数据库查询 他的权限，set集合
        Set<String> set=roleDao.selectPermsByUsername(username);
        simpleAuthorizationInfo.setStringPermissions(set);//权限

        //根据用户名去数据库查询 他的角色，set集合
        Set<String> set2=roleDao.selectRoleByUsername(username);
        simpleAuthorizationInfo.setRoles(set2);//角色

        return simpleAuthorizationInfo;
    }


    //封装认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //用户界面上填写的用户名
        String username= (String) authenticationToken.getPrincipal();

        //根据用户名查询数据库中的密码
        String password=roleDao.selectPassByUsername(username);

        if (password!=null){
            SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(username,password,"customRealm");

            //对应加盐时的盐值
            String salt="*/+&^%$";
            simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(salt+username));

            return simpleAuthenticationInfo;
        }

        return null;//unkownaccountexception
    }
}
