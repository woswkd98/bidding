package com.example.demo.interceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.jwt.JwtProduct;
import com.example.demo.service.UserService;

import org.apache.http.HttpStatus;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor extends HandlerInterceptorAdapter {
    private final JwtProduct jwtProduct;
    private  HashOperations<String, String, String> hashOps; 
    private final RedisTemplate redisTemplate;
  
    private static final String USER_LOGIN_INFO = "USER_LOGIN_INFO";
    @PostConstruct
    public void Init() {
        hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("여기에 들어오니?");
        Cookie cookie[] = request.getCookies(); 
       
        for(int i =0; i < cookie.length; ++i) {   
            System.out.println(cookie[i].getName());
            if("jwt".equals(cookie[i].getName())){// 쿠키안에 jwt 들어가 있는게 
                System.out.println(cookie[i].getValue());
                String subject = jwtProduct.getSubject(cookie[i].getValue());
               
                if(subject == null) {
                    System.out.println("subject");
                    return false;
                } 
                
                boolean value = hashOps.hasKey(UserService.getUserHashKey(), subject); // 아이디에 해당하는게 있는지
                System.out.println("size  " + hashOps.entries(UserService.getUserHashKey()).size());
                
                if(value == false) System.out.println("value");
                return value;
            }
        }

        

        return false;
    }
}
