package com.mk.config;

import com.mk.extract.impl.UserOperationObjectExtractor;
import com.mk.interceptor.CheckInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private CheckInterceptor checkInterceptor;

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {

        //需要拦截路径
        registry.addInterceptor(checkInterceptor).addPathPatterns("/**");//注册拦截器

    }*/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //addMapping 添加可跨域的请求地址
        registry.addMapping("/**")
                //设置跨域 域名权限 规定由某一个指定的域名+端口能访问跨域项目
                .allowedOriginPatterns("http://localhost:5173","http://192.168.0.185:5173")
                //是否开启cookie跨域
                .allowCredentials(true)
                //规定能够跨域访问的方法类型
                .allowedMethods("GET","POST","DELETE","PUT","HEAD","OPTIONS")
                //添加验证头信息  token
                .allowedHeaders("*")
                //预检请求存活时间 在此期间不再次发送预检请求
                .maxAge(3600);

    }
    @Bean
    public UserOperationObjectExtractor employeeOperationObjectExtractor() {
        return new UserOperationObjectExtractor();
    }
}
