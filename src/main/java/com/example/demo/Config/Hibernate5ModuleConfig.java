package com.example.demo.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Hibernate5ModuleConfig {

    @Bean 
    Hibernate5Module hibernate5Module() {
         Hibernate5Module hibernate5Module = new Hibernate5Module();
         return hibernate5Module;
    }


}