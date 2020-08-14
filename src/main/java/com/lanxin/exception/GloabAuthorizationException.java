package com.lanxin.exception;


import com.lanxin.config.IlanxinResult;
import com.lanxin.config.LanxinResult;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//全局异常捕获类

//用于处理AuthorizationException异常（没有访问权限的时候的异常，非本程序异常）
@ControllerAdvice//controller通知，对所有controller进行异常捕获
public class GloabAuthorizationException {

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public LanxinResult defaultErrorHandler(){
        return LanxinResult.fail(IlanxinResult.NOT_FUNCTION,IlanxinResult.NOT_FUNCTION_MSG);//没有权限
    }


}
