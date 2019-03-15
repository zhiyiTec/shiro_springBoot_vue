package com.zhiyi.shiro.service;

import com.zhiyi.shiro.mapper.GetMapper;
import com.zhiyi.shiro.model.Shiro_User;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class GetService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    GetMapper getMapper;


    public  Map<String,Object> getUserService(){
        Map<String,Object> map=new HashMap<String,Object>();
        List<Shiro_User> shiro_userList=getMapper.getAllUser();
       if( CollectionUtils.isEmpty(shiro_userList)){
           map.put("status",false);
       }else{
           map.put("status",true);
           map.put("users",shiro_userList);
       }
        return  map;
    }

    /**
     * 获取用户token,并且验证当前的token是否正确
     * @param userName
     * @return
     */
    public  Map<String,Object> confirmUserService(String userName,String sid){
        Map<String,Object> map=new HashMap<String,Object>();

        Shiro_User user=getMapper.getShiroUserByName(userName);
        String token=user.getToken();
        if(token!=null&&!"".equals(token)){
            if(sid.equals(token)){
                map.put("status",0);//代表验证成功
            }else{
                map.put("status",1);//代表验证失败
            }
        }else{
            map.put("status",1);
        }
        return  map;
    }
}
