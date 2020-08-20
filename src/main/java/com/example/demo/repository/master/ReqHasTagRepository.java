package com.example.demo.repository.master;
import com.example.demo.Model.ReqHasTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReqHasTagRepository extends JpaRepository<ReqHasTag, Long> { 
    
}