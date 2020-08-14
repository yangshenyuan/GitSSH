package com.lanxin.util;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

/**
 * Created by Administrator on 2020/8/11 0011.
 */
public class SqlSessionUtil {

    public static SqlSession getSqlSession(){


        MybatisSqlSessionFactoryBuilder mybatisSqlSessionFactoryBuilder=new MybatisSqlSessionFactoryBuilder();

        try {
         return  mybatisSqlSessionFactoryBuilder.
                 build(Resources.getResourceAsReader("mybatisconfig.xml")).openSession();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
//
//    public static void main(String[] args) {
//
//
//        RoleDao roleDao=SqlSessionUtil.getSqlSession().getMapper(RoleDao.class);
//
//        System.out.println( roleDao.selectPassByUsername("ysy1"));
//    }
}
