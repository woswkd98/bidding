package com.example.demo.repository.master;

import java.util.List;

import com.example.demo.Model.Bidding;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BiddingRepository extends JpaRepository<Bidding, Long> {
    
    @Query(value = "SELECT b.* FROM Bidding b " +
    "INNER JOIN seller s " +
    "ON s.seller_id = b.seller_id " +
    "INNER JOIN request r " +
    "ON r.request_id = b.request_id " +
    "WHERE s.seller_id = :seller_id ", nativeQuery = true)
    public List<Bidding> getBiddingBySeller(
        @Param("seller_id") long sellerId
    );

    @Query(value = "SELECT b.* FROM Bidding b " +
    "INNER JOIN seller s " +
    "ON s.seller_id = b.seller_id " +
    "INNER JOIN request r " +
    "ON r.request_id = b.request_id " +
    "WHERE r.request_id = :request_id ", nativeQuery = true)
    public List<Bidding> getBiddingByRequest(
        @Param("request_id") long requestId
    );
}