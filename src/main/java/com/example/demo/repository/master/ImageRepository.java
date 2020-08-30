package com.example.demo.repository.master;

import java.util.List;

import com.example.demo.Model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {
    
    @Query(value = "SELECT i.* FROM images i " +
    "INNER JOIN seller_has_img shi " +
    //"ON i.images_id = shi.seller_has_img_id " +
    "INNER JOIN seller s " +
    "ON s.seller_id = shi.seller_has_img_id " +
    "WHERE s.seller_id = :sellerId", nativeQuery = true)
    public List<Images> getImgBySeller(long sellerId);
}