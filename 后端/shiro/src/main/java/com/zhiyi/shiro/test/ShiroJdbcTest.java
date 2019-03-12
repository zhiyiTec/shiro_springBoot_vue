package com.zhiyi.shiro.test;

import com.zhiyi.shiro.config.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShiroJdbcTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test(){
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro_mysql.ini");
        SecurityManager securityManager=factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);//此处是全局的设置，只需要设置一次即可
        Subject subject=SecurityUtils.getSubject();//获取当前与系统交互的对象
        UsernamePasswordToken token=new UsernamePasswordToken("000001","123456");//由用户名和密码组成
        try {

            subject.login(token);//调用subject的login方法进行登录
            if(subject.isAuthenticated()){
                logger.info("用户验证成功");
                if(subject.hasRole("admin")){
                    logger.info("该用户当前身份为管理员");
                }
                if(subject.isPermitted("search")){
                    logger.info("该用户拥有search权限");
                }
                if(subject.isPermittedAll("search","add","del")){
                    logger.info("该用户拥有search,add权限");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("用户名或密码错误，验证失败");
        }
    }
}
