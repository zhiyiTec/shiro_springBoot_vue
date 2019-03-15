package com.zhiyi.shiro.mapper;

import com.zhiyi.shiro.model.Shiro_User;

import java.util.List;

public interface UpdateMapper {
    public void SaveTokenToUserByUN(Shiro_User user);//通过用户名将用户的token存放到shiro_user表中
}
