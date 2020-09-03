package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.Model.User;
import com.example.demo.VO.UserVO;
import com.example.demo.service.ImageService;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ImageService imageService;
    private static final int cookieMaxAge = 60 * 60 * 2; //2시간

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.POST)
    public ResponseEntity<?> insertImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") long userId
        ) { 
           //System.out.println(userId);
        return new ResponseEntity<>(userService.insertImage(userId, file), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<?> insertUser(
        RequestEntity<UserVO> req
    ) { 
        return new ResponseEntity<>(userService.insert(req.getBody()), HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(
        HttpServletResponse res,
        RequestEntity<Map<String, Object>> req
    ) {
        Map<String, Object> map = req.getBody();
        String rs = userService.login(
            map.get("email").toString(), 
            map.get("pwd").toString());        
            if(rs != null) {
                Cookie cookie = new Cookie("jwt", rs);
                cookie.setHttpOnly(true);
                cookie.setMaxAge(cookieMaxAge);
                res.addCookie(cookie);       
                return new ResponseEntity<>("success", HttpStatus.OK);
            }
        
        return new ResponseEntity<>("fali", HttpStatus.OK);
    }

    
    @RequestMapping(value = "/logout", method = RequestMethod.HEAD)
    public ResponseEntity<?> logout(
        HttpServletResponse res,
        @CookieValue("jwt") String jwt
    ) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        res.addCookie(cookie);   
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
   
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public List<User> selectAll() {
        return userService.findAll();
    }

    @RequestMapping(value = "/users", method = RequestMethod.PATCH)
    public ResponseEntity<?> updatePwd(RequestEntity<Map<String, Object>> req) {
        Map<String, Object> map = req.getBody();

        User user = userService.updatePassword(
           (Long)map.get("userId"), map.get("pwd").toString());
        if(user == null) {
            return ResponseEntity.ok().body("아이디가 없네");
        }

        return ResponseEntity.ok().body(user);
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)  
    public String verify(RequestEntity<Map<String, Object>> req, @CookieValue("jwt") String jwt) {     
        System.out.println("0-0----------------------");
        String body = req.getBody().get("email").toString();
        System.out.println(jwt);
        System.out.println(body);
        return userService.verify(body,jwt);
    }

    @RequestMapping(value = "/email/{userEmail}",  method = RequestMethod.GET)  
    public String sendVerifyNumByEmail(@PathVariable String userEmail) {     
        return userService.sendVerifyNumByEmail(userEmail);
    }

    @RequestMapping(value = "/email/{userEmail}/{verifyNum}",  method = RequestMethod.GET)  
    public String verifyNumByEmail(@PathVariable String userEmail, @PathVariable int verifyNum) {     
        return userService.verifyEmail(userEmail,verifyNum);
    }

    @RequestMapping(value = "/users",  method = RequestMethod.GET)  
    public List<Map<String,Object>> getAllUser() {     
        return userService.getAll();
    }

    
    @RequestMapping(value = "/users/image/{id}",  method = RequestMethod.GET)  
    public String getAllUser(@PathVariable long id) {     
        return userService.getImage(id);
    }

    @RequestMapping(value = "/users/email",  method = RequestMethod.POST)  
    public List<Map<String,Object>> getUserByEmail(RequestEntity<Map<String, Object>> req) {     
        System.out.println(req.getBody().get("userEmail").toString());
        return userService.getUserByEmail(req.getBody().get("userEmail").toString());
    }
}
  
  