package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.Model.Seller;
import com.example.demo.service.ReviewService;
import com.example.demo.service.SellerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*

 @Column(name = "portfolio", nullable = false)
    private String portfolio;

    @Column(name = "sellerGrage", nullable = true)
    private float sumRate;

    @Column(name = "reviewCount", nullable = true)
    private Long reviewCount;

    @Column(name = "state", nullable = false)
    private String state;
    

*/

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;
    private final ReviewService reviewService;
    @RequestMapping(value = "/sellers", method = RequestMethod.POST) 
    public Seller insertSeller(
        @RequestParam("file") MultipartFile files[], 
        @RequestParam("userId") long userId, 
        @RequestParam("portfolio") String portfolio)  
    {
        return  sellerService.insertSeller(userId,portfolio,files);
    }

    @RequestMapping(value = "/sellers", method = RequestMethod.GET) 
    public List<Seller> getSeller() {
        return sellerService.findAll();
    }

    @RequestMapping(value = "/sellers/{sellerId}", method = RequestMethod.GET) 
    public Map<String, Object> getSellerId(@PathVariable long sellerId) {
        Seller seller = sellerService.findById(sellerId).get();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sellerId", seller.getId());
        map.put("portfolio", seller.getPortfolio());
        map.put("state", seller.getState());
        map.put("phone", seller.getUser().getPhone());
        if(seller.getUser().getImages() == null)
            map.put("profileImage", null);
        else map.put("profileImage", seller.getUser().getImages().getUrl());
        map.put("userName", seller.getUser().getUserName());
        map.put("userEmail", seller.getUser().getUserEmail());
        map.put("review", reviewService.getReviewsBySeller(sellerId));
        map.put("exampleImage", sellerService.getImageBySellerId(sellerId));
        return map;
    }

    /*
    @RequestMapping(value = "/sellers", method = RequestMethod.PATCH) 
    public Seller updateSeller(
        @RequestParam("file") MultipartFile files[], 
        @RequestParam("portfolio") String portfolio
        
        )  
    {
        return  sellerService.insertSeller(userId,portfolio,files);
    }*/

}