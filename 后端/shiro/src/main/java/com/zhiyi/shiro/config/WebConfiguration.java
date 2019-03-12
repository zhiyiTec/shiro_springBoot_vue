//package com.zhiyi.shiro.config;
//
//import org.apache.shiro.web.env.EnvironmentLoaderListener;
//import org.apache.shiro.web.servlet.ShiroFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class WebConfiguration {
//
//    /**
//     * 实现EnvironmentLoaderListener监听器
//     * @return
//     */
//  @Bean
//    public ServletListenerRegistrationBean<EnvironmentLoaderListener> EnvironmentLoaderListener(){
//      ServletListenerRegistrationBean<EnvironmentLoaderListener> EnvironmentLoaderListener = new ServletListenerRegistrationBean<EnvironmentLoaderListener>();
//      EnvironmentLoaderListener.setListener(new EnvironmentLoaderListener());
//      return EnvironmentLoaderListener;
//    }
//
//    @Bean
//    public FilterRegistrationBean ShiroFilter(){
//        FilterRegistrationBean ShiroFilter = new FilterRegistrationBean();
//        ShiroFilter.addUrlPatterns("/*");
//        ShiroFilter.setFilter(new ShiroFilter());
//        return ShiroFilter;
//    }
//
//
//
//}
