package com.mk.interceptor;

import com.mk.constant.JwtClaimsConstant;
import com.mk.context.BaseContext;
import com.mk.properties.JwtProperties;
import com.mk.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//定义拦截器
@Slf4j
@Component
public class CheckInterceptor implements HandlerInterceptor {


    private final JwtProperties jwtProperties;
    private CheckInterceptor(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }
    //目标资源运行前运行，返回ture则放行，否则不放
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //如果是OPTIONS请求的话 进行直接放行
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            log.info("跨域请求");
            return true;
        }

        //1.获取请求url
        String url = request.getRequestURI().toString();

        //2.判断请求头中是否包含login，如果包含，说明是登录操作，放行
        if (url.contains("login")) {
            log.info("登录操作，放行");
            return true;
        }

        //3.获取请求头中的令牌（token）
        String jwt = request.getHeader(jwtProperties.getUserTokenName());



        //5.解析token，如果解析失败，返回错误信息（未登录）
        try {
            Claims claims = JwtUtil.parseJwt(jwtProperties.getUserSecretKey(),jwt);
            // 存储用户id到线程中
            BaseContext.setCurrentId(Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString()));
            return true;
        } catch (Exception e) {  //解析失败
            log.error("jwt解析错误");
            response.setStatus(401);
            return false;
        }



    }

    //运行目标资源后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {

    }

    //视图渲染完毕后运行，最后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
    }
}
