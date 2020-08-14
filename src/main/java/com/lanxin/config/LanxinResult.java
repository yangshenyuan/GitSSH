package com.lanxin.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//自定义异常 实体

@Data//可以省略get、set
@AllArgsConstructor//可以省略全参数的构造方法
@NoArgsConstructor//可以省略无参的构造方法
public class LanxinResult {

    private Integer code;//响应码 200.404,500等
    private String msg;//响应消息
    private Object data;//返回数据

    //定义无参构造方法
    //定义全参构造方法
    //定义两参构造方法
    public LanxinResult(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }

    //执行成功
    public static LanxinResult success(Object date){
        return new LanxinResult(IlanxinResult.SUCCESS_CODE,IlanxinResult.SUCCESS_MSG,date);
    }

    //执行成功
    public static LanxinResult success(Integer code, String msg){
        return new LanxinResult(code,msg);
    }

    //执行失败
    public static LanxinResult fail(){
        return new LanxinResult(IlanxinResult.FAIL_CODE,IlanxinResult.FAIL_MSG,null);
    }

    //执行失败
    public static LanxinResult fail(Integer code, String msg){
        return new LanxinResult(code,msg);
    }





}
