package com.example.demo.service;

import java.text.ParseException;
import java.util.List;

import com.example.demo.CRUDInterface;
import com.example.demo.Config.EmailSender;
import com.example.demo.Model.User;
import com.example.demo.jwt.JwtProduct;
import com.example.demo.repository.master.*;
import com.nimbusds.jose.JOSEException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
public class UserService implements CRUDInterface<User> {

    private static final String userHashKey = "userHashKey";
    private static final String emailHashKey = "emailHashKey";
    private final UserRepository userRepository;
    private final RedisTemplate redisTemplate;
    private final JwtProduct jwtProduct;
    private final EmailSender emailSender;
    @Autowired
    public UserService(
        UserRepository userRepository, 
        RedisTemplate redisTemplate, 
        JwtProduct jwtProduct,
        EmailSender emailSender
        ) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.jwtProduct = jwtProduct;
        this.emailSender = emailSender;
    }

    @Override
    public User insert(User t) {
        return userRepository.save(t);
    }

    @Override
    public User update(User t) {
        return userRepository.save(t); // 테스트 필요
    }
    
    @Override
    public List<User> selectAll() {
        // TODO Auto-generated method stub
        return userRepository.findAll();
    }

    @Override
    public void deleteByKey(Long Key) {
        // TODO Auto-generated method stub
        userRepository.deleteById(Key);
    }

    @Override
    public User selectByKey(Long key) {
        // TODO Auto-generated method stub
        return userRepository.findById(key).get();
    }

    public String verify(String userEmail) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        try {
            if (jwtProduct.verify(hashOps.get(userHashKey, userEmail), userEmail)) {
                return "성공";
            }
        } catch (ParseException | JOSEException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "실패";
    }

    public String login(String userEmail, String userPassword) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        User user = userRepository.findByUserEmail(userEmail);
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(userEmail, user.getUserPassword())) {
            String token = jwtProduct.getKey(user.getUserEmail());
            hashOps.put(userHashKey, user.getUserEmail(),token);
            return "성공";
        }
        return "실패";
    }

    public String logout(String userEmail) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.delete(userHashKey, userEmail);
        return "성공";
    }

    public String sendVerifyNumByEmail(String userEmail) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        int rand  = (int)(Math.random() * 100000);
        hashOps.put(userHashKey, userEmail,String.valueOf(rand));
        emailSender.sendMail(userEmail, rand);
        return "성공";
    }

    public String verifyEmail(String userEmail, int number) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        if(Integer.valueOf(hashOps.get(userHashKey, userEmail)) == number) {
            hashOps.delete(userHashKey, userEmail);
            return "성공";
        }
        return "실패";
    }

}