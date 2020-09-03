package com.example.demo.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.example.demo.Config.EmailSender;
import com.example.demo.Model.Images;
import com.example.demo.Model.User;
import com.example.demo.VO.UserVO;
import com.example.demo.common.JpaCrudServiceBase;
import com.example.demo.common.RegexPattern;
import com.example.demo.jwt.JwtProduct;
import com.example.demo.repository.master.*;
import com.nimbusds.jose.JOSEException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService  {

    private static final String userHashKey = "userHashKey";
    private static final String emailHashKey = "emailHashKey";

    private final RedisTemplate redisTemplate;
    private final JwtProduct jwtProduct;
    private final EmailSender emailSender;
    private final RegexPattern pattern;
    private final UserRepository repository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private  HashOperations<String, String, String> hashOps;


    public User insertImage(long userId, MultipartFile file) {
        User user = repository.findById(userId).get();
        if(user == null) {
            return null;
        }

        Images img = new Images();
            
        img.setUrl(imageService.upload(file));
        user.setImages(img);
        imageRepository.save(img);
        repository.save(user);
        return user;
    } 

    public String getImage(long userId) {
        User user = repository.getOne(userId);
        if(user.getImages() != null) {
            return user.getImages().getUrl();
        }
        return "이미지 없음";
    }

    public List<Map<String, Object>> getAll() {
        return repository.getAll();
    }
   
    public List<Map<String, Object>> getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }
    public String insert(UserVO vo) {
        
        if(!pattern.passwordChk(vo.getUserPassword(), "", vo.getUserEmail())) {
            return "비밀번호가 맞지 않는다";
        }

        if(!pattern.isValidEmail(vo.getUserEmail())) {
            return "이메일 형식이 아니다";
        }
        
        if(!pattern.phoneForm(vo.getPhone())) {
            return "휴대폰 형식";
        }

        User user = new User();
    
        user.setPhone(vo.getPhone());
        user.setState("1`234");
        user.setUserEmail(vo.getUserEmail());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setUserPassword(encoder.encode(vo.getUserPassword()));
        user.setUserName(vo.getUserName());
        
        return repository.save(user).getId().toString();
    }

    // 업데이트 패스워드같은경우는 아이디가 있다는 경우므로  쓴다
    public User updatePassword(long userId, String pwd) {
        User user = repository.findById(userId).get();

        if(!pattern.passwordChk(pwd, "", user.getUserEmail())) {
            return null;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setUserPassword(encoder.encode(pwd));
        return repository.save(user);
    }
    
    public List<User> findAll() {
        return repository.findAll();
    }

    public User updateUserInfo(long id, String phone, String profileImage, String state) {
        User user = repository.getOne(id);

        if(phone != null) {
            user.setPhone(phone);
        }

        return repository.save(user);
    }

    public User setUserState(long id, String state) {
          User user = repository.getOne(id);
          user.setState(state);
          return user;
    }

    public String verify(String userEmail, String jwt) {
        try {
            if (jwtProduct.verify(userEmail, jwt)) {
                return "성공";
            }
        } catch (ParseException | JOSEException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "실패";
    }

    public String login(String userEmail, String userPassword) {
         
        User user = repository.findByUserEmail(userEmail);
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(userPassword, user.getUserPassword())) {
            String token = jwtProduct.getKey(user.getUserEmail());
            
            return token;
        }
        return null;
    }


    public String sendVerifyNumByEmail(String userEmail) {

        int rand  = (int)(Math.random() * 100000);
        hashOps.put(emailHashKey, userEmail,String.valueOf(rand));
        emailSender.sendMail(userEmail, rand);
        return "성공";
    }

    public String verifyEmail(String userEmail, int number) {

        if(Integer.valueOf(hashOps.get(emailHashKey, userEmail)) == number) {
            hashOps.delete(emailHashKey, userEmail);
            return "성공";
        }
        return "실패";
    }
}