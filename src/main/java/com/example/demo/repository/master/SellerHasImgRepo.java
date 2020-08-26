package com.example.demo.repository.master;

import com.example.demo.Model.SellerHasImg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerHasImgRepo extends JpaRepository<SellerHasImg,Long> {
    
}