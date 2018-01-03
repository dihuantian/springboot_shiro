package com.example.springboot_shiro.controller;

import com.example.springboot_shiro.domain.Users;
import com.example.springboot_shiro.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/*
* 使用第一个错误，应该将所有的packageApplication类的里面
* 使用第二个错误，spring.thymeleaf.prefix的路径后面不要忘了别要有空格
* */
@Controller
public class TestController {

    @Autowired
    public UserManagementService userManagementService;

    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    public ModelAndView loginView(Model map){

        Users users= new Users();
        users.setUsername("administrator");
        users.setPassword("administrator");
        map.addAttribute("users",users);
        return new ModelAndView("UserManagement/Login");
    }

    @RequestMapping(value = "/Login/LoginVerification")
    @ResponseBody
    public String login(Users users){

        if (userManagementService.login(users)){
            return "true";
        }
        return "false";
    }

    @RequestMapping(value = "/Register")
    public ModelAndView registerView(){

        return new ModelAndView("UserManagement/Register");
    }

    @RequestMapping(value = "/Register/RegisterVerification")
    @ResponseBody
    public String register(Users users){

        if (userManagementService.register(users)){
            return "true";
        }
        return "false";
    }


    @RequestMapping(value = "/logout")
    @ResponseBody
    public boolean logout(){

        return userManagementService.logout();
    }
}
