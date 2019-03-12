package com.zhiyi.shiro.service;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShiroService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public  Map<String,Object> confirmUserService(Subject subject,String userName,String password){
        Map<String,Object> map=new HashMap<String,Object>();
        UsernamePasswordToken token=new UsernamePasswordToken(userName,password);//由用户名和密码组成
        try{
            subject.login(token);
            logger.info("登录成功");
            map.put("status",0);
            if(subject.hasRole("admin")){
                logger.info("该用户的角色为admin");
                map.put("role","admin");
            }

        }catch (UnknownAccountException uae){
            System.out.println("没有用户名为"+token.getPrincipal()+"的用户");
            map.put("status",1);
        } catch (IncorrectCredentialsException ice){
            System.out.println("用户名为："+token.getPrincipal()+"的用户密码不正确");
            map.put("status",2);
        } catch (LockedAccountException lae){
            System.out.println("用户名为："+token.getPrincipal()+"的用户已被冻结");
            map.put("status",3);
        } catch (AuthenticationException e){
            System.out.println("未知错误！");
            map.put("status",4);
        }
        return  map;
    }
}
