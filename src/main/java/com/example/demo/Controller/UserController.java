package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.Model.User;
import com.example.demo.VO.UserVO;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
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

import java.util.Iterator;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<?> insert(RequestEntity<MultiValueMap<String, Part>> req) {
              
        
        Iterator<String> keys = req.getBody().keySet().iterator();
        MultiValueMap<String, Part> reqMap = req.getBody();
        System.out.println(123);
        while(keys.hasNext()) 
        
        {
            String key = keys.next();
            System.out.println(key);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);




            //System.out.println(file[0].getOriginalFilename());
            /*s
            System.out.println(files.size());s
            for(int i =0; i < files.size(); ++i) {
                System.out.println(files.get(i).getOriginalFilename());
            } */

            /*
            if(user.getPhone() == null) {
                System.out.println("null");
                return null;
            }
            else {
                System.out.println(user.getPhone());
            }*/

      
        /*
        System.out.println(userPassword);
        return userService.insert(
            userPassword,
            userEmail,
            userName, 
            phone,
            file);*/ 
    }
    /*
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
    }*/
}
  
  