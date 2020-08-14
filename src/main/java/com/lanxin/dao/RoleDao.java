package com.lanxin.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Set;


@Mapper
public interface RoleDao {


    //通过用户名，查询密码
    public String selectPassByUsername(String username);

    //通过用户名，查询权限
    public Set<String> selectPermsByUsername(String username);

    //通过用户名，查询角色
    public Set<String> selectRoleByUsername(String username);
}
