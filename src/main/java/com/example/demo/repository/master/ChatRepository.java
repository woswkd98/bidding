package com.example.demo.repository.master;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.demo.entity.*;
@Repository
public interface ChatRepository extends JpaRepository<ChatMsg, Long> {
    List<ChatMsg> findByRoomIdOrderByUploadAtAsc(String room_id);
}