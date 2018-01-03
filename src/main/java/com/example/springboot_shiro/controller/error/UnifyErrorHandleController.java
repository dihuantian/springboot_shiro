package com.example.springboot_shiro.controller.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class UnifyErrorHandleController {

    @ResponseBody
    @ExceptionHandler(value = AuthorizationException.class)
    public String authorizationException(AuthorizationException e){

        return "权限不足"+e.getMessage();
    }
}
