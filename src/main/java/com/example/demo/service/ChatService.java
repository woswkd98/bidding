package com.example.demo.service;


import org.springframework.beans.propertyeditors.CharsetEditor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import jdk.jfr.Percentage;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Sort.Order;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.example.demo.Config.GetTimeZone;
import com.example.demo.Model.ChatMsg;
import com.example.demo.VO.*;
import com.example.demo.repository.master.ChatRepository;

@Component
@RequiredArgsConstructor
public class ChatService {
    
    private final RedisTemplate redisTemplate;
    private static final String ChattingRoomKey = "ChattingRoomKey";
    private Map<String, ChannelTopic> mapTopic;
    private HashOperations<String,String,ChattingRoom> hashOperations;
    private final ChatRepository chatRepository;
    private Sort sort;
    private final RedisConnectionFactory factory;
    
    @PostConstruct
    public void init() {
        mapTopic = new HashMap<>();
        hashOperations = redisTemplate.opsForHash();
        sort = Sort.by(Direction.ASC, "uploadAt");

    }
   
    public List<ChatMsg> getChatting(String roomId) {
        return chatRepository.findByRoomIdOrderByUploadAtAsc(roomId);
    }    
}