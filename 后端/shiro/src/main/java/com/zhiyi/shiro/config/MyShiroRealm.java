package com.zhiyi.shiro.config;

import com.zhiyi.shiro.mapper.GetMapper;
import com.zhiyi.shiro.model.Shiro_User;
import com.zhiyi.shiro.model.Shiro_User_Role;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.LinkedList;
import java.util.List;

public class MyShiroRealm    extends AuthorizingRealm {
    private JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GetMapper getMapper;


    /**
     * 用于获取登录成功后的角色、权限等信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //先通过用户名查询角色

        String userName=String.valueOf(principals.getPrimaryPrincipal());
        List<String> roles=getMapper.getUserRolesByName(userName);
        String sql2="select permissionName from shiro_role_permission where roleName=?";
        List<String > permissionNames=new LinkedList<String>();


        //将查询出的结果封装在权限信息里面
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissionNames);
        return info;
    }
    /**
     * 验证当前登录的Subject
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        Shiro_User user=getMapper.getShiroUserByName(username);
        if (user==null){
            logger.info("数据库中不存在该用户");
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        return info;
    }
}
