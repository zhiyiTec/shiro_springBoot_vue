package com.zhiyi.shiro.mapper;

import com.zhiyi.shiro.model.Shiro_User;
import com.zhiyi.shiro.model.Shiro_User_Role;

import java.util.List;

public interface GetMapper {
    public Shiro_User getShiroUserByName(String userName);//通过用户名获取这个用户所有的信息
    public List<String> getUserRolesByName(String  userName);//通过用户名获取角色表里的所有信息
    public List<String> getAllROles();//获取所有的角色名
    public List<String> getPermissionByRoleName(String roleName);//通过角色名名获取用户权限名
    public List<String> getAllPermissions();//获取所有的权限信息
}
