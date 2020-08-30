package com.example.demo.service;

import com.example.demo.repository.master.ReviewRepository;
import com.example.demo.repository.master.SellerRepository;
import com.example.demo.repository.master.UserRepository;

import java.util.List;

import com.example.demo.Config.GetTimeZone;
import com.example.demo.Model.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    @Transactional
    public String insertReview(
        long userId, 
        long sellerId, 
        String context, 
        float rating
    ) {
        if(reviewRepository.getReviewCountBySellerIdAndUserId(userId, sellerId) >0) {
            return "이미 리뷰를 썼음";
        }
        
        Review nReview = new Review();
        
        
        Seller seller = sellerRepository.findById(sellerId).get();
        seller.setReviewCount(seller.getReviewCount() + 1); // 카운트를 더한다
        seller.setSumRate(seller.getSumRate() + rating); // 레이팅을 더한다
        nReview.setSeller(seller);
        nReview.setUser(userRepository.findById(userId).get());
        nReview.setContext(context);
        nReview.setGrade(rating);
        nReview.setUploadAt(GetTimeZone.StringToDate(GetTimeZone.getSeoulDate()));
        return String.valueOf(reviewRepository.save(nReview).getId());
    }
    @Transactional
    public void deleteReview(long reviewId) {
        Review nReview = reviewRepository.getOne(reviewId);
        Seller seller = nReview.getSeller();
        seller.setReviewCount(seller.getReviewCount() - 1);
        seller.setSumRate(seller.getSumRate() - nReview.getGrade());
        reviewRepository.delete(nReview);
    }
    // 내가 쓴 리뷰 확인
    @Transactional
    public List<Review> getReviewsByUser(long userId) {
        User user = userRepository.findById(userId).get();
        System.out.println(user.getReviews().size());
        return user.getReviews();
    }
    // seller의 리뷰 확인
    @Transactional
    public List<Review> getReviewsBySeller(long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).get();
        System.out.println(seller.getReview().size());
        return seller.getReview();
    }

    

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }
    
}