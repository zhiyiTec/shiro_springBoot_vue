package com.zhiyi.shiro.service;

import com.zhiyi.shiro.mapper.GetMapper;
import com.zhiyi.shiro.mapper.UpdateMapper;
import com.zhiyi.shiro.model.Shiro_User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ShiroService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    GetMapper getMapper;
    @Autowired
    UpdateMapper updateMapper;

    public  Map<String,Object> confirmUserService(Subject subject, String userName, String password, HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        UsernamePasswordToken token=new UsernamePasswordToken(userName,password);//由用户名和密码组成
        try{
            subject.login(token);
            logger.info("登录成功");
            map.put("status",0);

            HttpSession session = request.getSession(true);
            String tokenPass=session.getId();
            Shiro_User user=new Shiro_User();
            user.setUserName(userName);
            user.setToken(tokenPass);
            updateMapper.SaveTokenToUserByUN(user);
            List<String>roles=getMapper.getAllROles();
            List<String>listRoles=new LinkedList<String>();
            for(String role:roles){
                if(subject.hasRole(role)){
                    listRoles.add(role);
                }
            }
            List<String> lpermissions=getMapper.getAllPermissions();
            List<String>listPermissions=new LinkedList<String>();
            for (String permission:lpermissions){
                if(subject.isPermitted(permission)){
                    logger.info("该用户的拥有权限---"+permission);
                    if(!listPermissions.contains(permission)){
                        listPermissions.add(permission);
                    }

                }
            }
            map.put("permissions",listPermissions);
            map.put("roles",listRoles);
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
