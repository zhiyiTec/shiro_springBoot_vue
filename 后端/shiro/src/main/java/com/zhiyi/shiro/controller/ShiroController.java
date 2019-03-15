package com.zhiyi.shiro.controller;

import com.zhiyi.shiro.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("shiro")
public class ShiroController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ShiroService shiroService;
    @ResponseBody
    @RequestMapping("login")
    public Map<String,Object> login(@RequestParam("userName")String userName,@RequestParam("passWord")String passWord, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");// 允许服务器向浏览器跨域响应时更改浏览器（客户端）的cookie
        Map<String,Object> map=new HashMap<String,Object>();


        Subject subject=SecurityUtils.getSubject();//获取当前与系统交互的对象

        return  shiroService.confirmUserService(subject,userName,passWord,request);
    }



    @RequestMapping("getRole")
    @ResponseBody
    public Map<String,Object> getRole(@RequestParam("userName")String getRoleInfo, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");// 允许服务器向浏览器跨域响应时更改浏览器（客户端）的cookie
        Map<String,Object> map=new HashMap<String,Object>();


        Subject subject=SecurityUtils.getSubject();//获取当前与系统交互的对象
        try{
           if( subject.hasRole("admin")){
               map.put("role","admin");
           }
            if( subject.hasRole("guest")){
                map.put("role","guest");
            }
            if( subject.hasRole("test")){
                map.put("role","guest");
            }



        }catch (AuthenticationException e){
            map.put("status",false);
            logger.info("验证失败");
        }
        return map;
    }


    @RequiresRoles(value={"admin","user"},logical = Logical.OR)
    @RequestMapping("admin")
    @ResponseBody
    public Map<String,Object> admin(@RequestParam("userName")String userName,@RequestParam("passWord")String passWord, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");// 允许服务器向浏览器跨域响应时更改浏览器（客户端）的cookie
        Map<String,Object> map=new HashMap<String,Object>();


        Subject subject=SecurityUtils.getSubject();//获取当前与系统交互的对象
        logger.info("这货是管理员");
        return null;
    }
}
