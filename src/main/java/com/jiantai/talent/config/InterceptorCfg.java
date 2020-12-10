package com.jiantai.talent.config;


import com.jiantai.talent.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class InterceptorCfg implements WebMvcConfigurer  {
    @Bean
    public MyInterceptor myInterceptor(){
        return  new MyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        //InterceptorRegistration registration = registry.addInterceptor(new MyInterceptor());
        InterceptorRegistration registration = registry.addInterceptor(myInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
//                "你的登陆路径",            //登录
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.png",
                "/**/*.jpg",
                "/**/*.gif",
                "/**/*.woff",
                "/**/*.ttf"
        );
    }



}
