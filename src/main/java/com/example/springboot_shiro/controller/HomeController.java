package com.example.springboot_shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class HomeController {

    @RequestMapping(value = "/Home/Index")
    @RequiresPermissions("/Home/View")
    public String index(HttpSession httpSession){

        return "进来了";
    }

}
