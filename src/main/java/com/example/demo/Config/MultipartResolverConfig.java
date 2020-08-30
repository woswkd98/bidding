package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MultipartResolverConfig {
    /*
    @Bean
    public MultipartResolver multipartResolver() {
      
        MultipartResolver multipartResolver 
            = new CommonsMultipartResolver();

        return multipartResolver;
    }*/
}