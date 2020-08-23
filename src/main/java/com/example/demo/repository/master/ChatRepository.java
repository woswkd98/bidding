package com.example.demo.repository.master;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Model.*;
public interface ChatRepository extends JpaRepository<ChatMsg, Long> {
    
}