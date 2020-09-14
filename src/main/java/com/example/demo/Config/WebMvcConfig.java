package com.example.demo.Config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.example.demo.interceptor.JwtInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptor jwtInterceptor;

    private List<String> jwtPaths = new ArrayList<String>();

    @PostConstruct
    public void init() {
        jwtPaths.add("/sellers/*");
        jwtPaths.add("/bddings/*");
        jwtPaths.add("/Chat/*");
        jwtPaths.add("/rooms/*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
        /*
        registry
            .addInterceptor(jwtInterceptor)
            .addPathPatterns(jwtPaths);
        WebMvcConfigurer.super.addInterceptors(registry);
       */
    }


}
