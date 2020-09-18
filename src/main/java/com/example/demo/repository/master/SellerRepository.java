package com.example.demo.repository.master;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.Seller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByUserId(long userId);



    @Query(value = 
    "select s.*, u.user_name, u.user_email, u.user_phone " +  
    "from seller s" + 
    "inner join user u on u.user_id = r.user_id " +
    "where s.user_id = :sellerId", nativeQuery = true)
    public  List<Map<String, Object>> getSellerInfo(long sellerId);
}