package com.example.demo.repository.master;

import com.example.demo.entity.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 내가 쓴 리뷰가 존재하는지 검사 중복 안되게 
    @Query(value = "SELECT COUNT(*) FROM seller s " +
    "INNER JOIN review r " +
    "ON s.seller_id = r.seller_id " +
    "INNER JOIN USER u " +
    "ON u.user_id = r.user_id " +
    "WHERE u.user_id = :user_id AND s.seller_id = :seller_id ", nativeQuery = true)
    public int getReviewCountBySellerIdAndUserId(
        @Param("user_id") long userId, 
        @Param("seller_id") long sellerId
    );
}