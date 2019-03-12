package com.zhiyi.shiro.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.LinkedList;
import java.util.List;

public class MyRealm2 extends AuthorizingRealm {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 权限验证调用（授权）
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //先通过用户名查询角色
        String sql="select roleName from shiro_user_role where userId=?";
        String userName=String.valueOf(principals.getPrimaryPrincipal());
        List<String>roles=jdbcTemplate.queryForList(sql,String.class,userName);
        String sql2="select permissionName from shiro_role_permission where roleName=?";
        List<String > permissionNames=new LinkedList<String>();
        for(String role:roles){
           permissionNames= jdbcTemplate.queryForList(sql2,String.class,role);
        }

        //将查询出的结果封装在权限信息里面
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissionNames);
        return info;
    }

    /**
     * 登录的时候调用(用于认证)
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String sql="select password from shiro_user where userId = ?";
        String userName=String.valueOf(token.getPrincipal());
        String password=jdbcTemplate.queryForObject(sql,String.class,userName);
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(userName,password,null,this.getName());
        return info;
    }
}
