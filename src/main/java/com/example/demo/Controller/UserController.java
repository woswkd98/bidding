package com.example.demo.Controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.Model.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public User insert(User user) {
        return userService.insert(user);
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> selectAll() {
        return userService.selectAll();
    }

    @RequestMapping(value = "/users", method = RequestMethod.PATCH)
    public User update(User user) {     
        return userService.update(user);
    }

    @RequestMapping(value = "/verify/{userEmail}", method = RequestMethod.GET)  
    public String verify(@PathVariable String userEmail) {     
        return userService.verify(userEmail);
    }

    @RequestMapping(value = "/email/{userEmail}",  method = RequestMethod.GET)  
    public String sendVerifyNumByEmail(@PathVariable String userEmail) {     
        return userService.sendVerifyNumByEmail(userEmail);
    }

    @RequestMapping(value = "/email/{userEmail}/{verifyNum}",  method = RequestMethod.GET)  
    public String verifyNumByEmail(@PathVariable String userEmail, @PathVariable int verifyNum) {     
        return userService.verifyEmail(userEmail,verifyNum);
    }
 
    
  
}
  