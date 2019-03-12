package com.zhiyi.shiro.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.RealmFactory;

public class MyRealm  implements Realm {
    /**
     * 设置数据源的名字
     * @return
     */
    @Override
    public String getName() {
        return "myRealm";
    }

    /**
     * 设置数据源的支持策略，本数据源设置只支持userName,Password策略
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 获取认证信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName=String.valueOf(token.getPrincipal());
        String password=String.valueOf((char [])token.getCredentials());
        if(!"root".equals(userName)){
            throw new UnknownAccountException();
        }else if(!"123456".equals(password)){
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(userName,password,getName());
    }
}
