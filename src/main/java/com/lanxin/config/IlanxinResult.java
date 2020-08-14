package com.lanxin.config;

//错误信息接口
public interface IlanxinResult {

    //执行成功
    public static final Integer SUCCESS_CODE=200;
    public static final String SUCCESS_MSG="执行成功";


    public static final Integer SUCCESS_LOGIN=2001;
    public static final String SUCCESS_LOGIN_MSG="登录成功";

    public static final Integer SUCCESS_LOGOUT=2002;
    public static final String SUCCESS_LOGOUT_MSG="退出成功";

    //程序错误
    public static final Integer FAIL_CODE=500;
    public static final String FAIL_MSG="程序错误";

    //没有找到数据
    public static final Integer NOT_FOUND_CODE=1001;
    public static final String NOT_FOUND_MSG="数据操作失败或没有找到数据";


    //没有登录
    public static final Integer NOT_LOGIN=4004;
    public static final String NOT_LOGIN_MSG="请先登录";


    //没有操作权限
    public static final Integer NOT_FUNCTION=4006;
    public static final String NOT_FUNCTION_MSG="没有操作权限";


}
