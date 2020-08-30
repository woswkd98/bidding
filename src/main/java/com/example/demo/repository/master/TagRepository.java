package com.example.demo.repository.master;

import java.util.List;
import java.util.Optional;

import com.example.demo.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository  extends JpaRepository<Tag, Long> {
    
   Tag findByContext(String context);
}