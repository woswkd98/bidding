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
import java.util.Iterator;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.POST)
    public ResponseEntity<?> insertImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable long userId
        ) { 
           //System.out.println(userId);
        return new ResponseEntity<>(userService.insertImage(userId, file), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<?> insertUser(
        RequestEntity<UserVO> req
    ) { 
       //System.out.println(userId);
        return new ResponseEntity<>(userService.insert(req.getBody()), HttpStatus.OK);
    }

    
   
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> selectAll() {
        return userService.findAll();
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
  
  