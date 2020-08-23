package com.example.demo.service;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.demo.VO.*;

@Component
public class ChatService {
    
    private final RedisTemplate redisTemplate;
    private static final String chatRoomKey = "chatRoomKey";

    public ChatService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        
    }
    
    public Long RoomCount() {
        SetOperations<String,ChatRoom> setOperations = redisTemplate.opsForSet();
        return setOperations.size(chatRoomKey);
    }

    public Set<ChatRoom> getRooms() {
        SetOperations<String,ChatRoom> setOperations = redisTemplate.opsForSet();
        return setOperations.members(chatRoomKey);
    }

    public String createRoom(ChatRoom room) {

        if()

        SetOperations<String,ChatRoom> setOperations = redisTemplate.opsForSet();
   
        setOperations.add(room.getBuyer(), room);
        return strUuid;
    }
}