package com.zhiyi.shiro.controller;

import com.zhiyi.shiro.service.GetService;
import com.zhiyi.shiro.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("get")
public class GetController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    GetService getService;

    /**
     * 获取所有用户
     * @param response
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("getUser")
    public Map<String,Object> getUser(HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");// 允许服务器向浏览器跨域响应时更改浏览器（客户端）的cookie
        return getService.getUserService();
    }

    /**
     * 每一次请求数据都要在此处验证一次token
     * @param response
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("confirmUser")
    public Map<String,Object> confirmUser(@RequestParam("userName")String userName, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");// 允许服务器向浏览器跨域响应时更改浏览器（客户端）的cookie
        HttpSession session = request.getSession(false);
        String sid=session.getId();
        return getService.confirmUserService(userName,sid);
    }

}
