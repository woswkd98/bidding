package com.example.demo.repository.master;

import java.util.List;
import java.util.Map;

import com.example.demo.Model.Bidding;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BiddingRepository extends JpaRepository<Bidding, Long> {
    
    @Query(value = "SELECT b.*, u.user_id, u.user_name, u.user_email FROM Bidding b " +
    "INNER JOIN seller s " +
    "ON s.seller_id = b.seller_id " +
    "INNER JOIN request r " +
    "ON r.request_id = b.request_id " +
    "inner join user u " +
    "on u.user_id = s.user_id " + 
    "WHERE s.seller_id = :seller_id ", nativeQuery = true)
    public List<Map<String,Object>> getBiddingBySeller(
        @Param("seller_id") long sellerId
    );

    @Query(value = "SELECT b.*, u.user_id, u.user_name, u.user_email,u.phone FROM Bidding b " +
    "INNER JOIN seller s " +
    "ON s.seller_id = b.seller_id " +
    "INNER JOIN request r " +
    "ON r.request_id = b.request_id " +
    "inner join user u " +
    "on u.user_id = s.user_id " + 
    "WHERE r.request_id = :request_id ", nativeQuery = true)
    public List<Map<String,Object>> getBiddingByRequest(
        @Param("request_id") long requestId
    );

    public Bidding findBySellerIdAndRequestId(long sellerId, long requestId);
}