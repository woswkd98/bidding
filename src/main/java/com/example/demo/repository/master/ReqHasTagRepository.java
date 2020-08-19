package com.example.demo.repository.master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Model.ReqHasTag;

@Repository
public interface ReqHasTagRepository extends JpaRepository<ReqHasTag, Long> { 
    
}