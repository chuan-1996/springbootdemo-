package com.example.bootdemo.common;

import com.example.bootdemo.vo.ResultVo;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qq491
 */
@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVo handle(Exception e) {
     if( e instanceof  UnknownAccountException) {
         return new ResultVo(0,"未知账户",null);
     }
     else if( e instanceof IncorrectCredentialsException ) {
        return new ResultVo(0,"密码不正确，剩余次数"+e.getMessage(),null);
     }else if( e instanceof LockedAccountException ) {
        return new ResultVo(0,"账户已锁定",null);
     }else if( e instanceof ExcessiveAttemptsException ) {
        return new ResultVo(0,"用户名或密码错误次数过多,账户被锁定，请联系管理员",null);
     }else if( e instanceof AuthenticationException ) {
        return new ResultVo(0,"用户名或密码不正确！",null);
     }else if( e instanceof AuthorizationException ) {
        return new ResultVo(0,"没有权限！",null);
    }
     else if( e instanceof ShiroException) {
         System.out.println(e.toString());
         return new ResultVo(0,"没有登录！",null);
     }
     else {
         System.out.println(e.toString());
         return new ResultVo(0,"操作异常，联系管理员",null);
     }
    }
}

