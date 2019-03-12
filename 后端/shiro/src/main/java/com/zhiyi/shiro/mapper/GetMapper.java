package com.zhiyi.shiro.mapper;

import com.zhiyi.shiro.model.Shiro_User;
import com.zhiyi.shiro.model.Shiro_User_Role;

import java.util.List;

public interface GetMapper {
    public Shiro_User getShiroUserByName(String userName);//通过用户名获取这个用户所有的信息
    public List<String> getUserRolesByName(String  userName);//通过用户名获取角色表里的所有信息
}
