package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.Review;
import com.example.demo.service.ReviewService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    
    @RequestMapping(value = "/reviews", method = RequestMethod.PUT)
    public String insertReview(
       @RequestBody Map<String, Object> map
    ) {
        System.out.println(map.get("userId"));
        return reviewService.insertReview(
           Long.valueOf(map.get("userId").toString()),
           Long.valueOf(map.get("sellerId").toString()),
           map.get("context").toString(),
           Float.valueOf( map.get("rating").toString()));
    }

    @RequestMapping(value = "/reviews/sellerId/{id}", method = RequestMethod.GET)
    public List<Review> getReviewsBySeller(
        @PathVariable("id") long sellerId
    ) {
        return  reviewService.getReviewsBySeller(sellerId);
    }

    
    @RequestMapping(value = "/reviews/userId/{id}", method = RequestMethod.GET)
    public List<Review> getReviewsByUser(
        @PathVariable("id") long userId
    ) {
       return reviewService.getReviewsByUser(userId);
    }

    @RequestMapping(value = "/reviews", method = RequestMethod.GET)
    public List<Review> getAllReview(
    ) {
        return reviewService.getAll();
    }

}