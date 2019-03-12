package com.zhiyi.shiro.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.zhiyi.shiro.config.MyRealm;
import com.zhiyi.shiro.config.MyRealm2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.DriverManager;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShiroJdbcMyRealm2Test {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    public void test(){
        DefaultSecurityManager securityManager=new DefaultSecurityManager();
        ModularRealmAuthenticator authenticator=new ModularRealmAuthenticator();//此处设置验证的策略
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());//设置至少有一匹配的策略
        securityManager.setAuthenticator(authenticator) ;

        //下面用于进行授权
        ModularRealmAuthorizer authorizer=new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);//设置授权到securityManager上


        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/shiro?autoReconnect=true&useUnicode=true&characterEncoding=utf-8&&zeroDateTimeBehavior=CONVERT_TO_NULL&&serverTimezone=GMT%2B8";

        dataSource.setUrl(url);
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        MyRealm2 myRealm2=new MyRealm2();
        myRealm2.setJdbcTemplate(jdbcTemplate);

        securityManager.setRealm(myRealm2);//设置数据源
        SecurityUtils.setSecurityManager(securityManager);

        //下面用于登录
        Subject subject=SecurityUtils.getSubject();//获取当前与系统交互的对象
        UsernamePasswordToken token=new UsernamePasswordToken("000001","123456");//由用户名和密码组成
        try{
            subject.login(token);
            logger.info("登录成功");
            if(subject.hasRole("admin")){
                logger.info("该用户的角色为admin");
            }
            if(subject.isPermitted("/add.html")){
                logger.info("该用户的拥有权限---/add.html");
            }
        }catch (AuthenticationException e){
            logger.info("验证失败");
        }
    }
}
