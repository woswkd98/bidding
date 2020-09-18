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
import com.example.demo.VO.*;
import com.example.demo.entity.ChatMsg;
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
   
    @PreDestroy
    public void destroy() {

        // destroy 되면 다 날려라 
        factory.getConnection().flushAll();

    }

    public Long RoomCount() {
        return hashOperations.size(ChattingRoomKey);
    }

    public Map<String, ChattingRoom> getRooms() {
        return hashOperations.entries(ChattingRoomKey);
    }

    public String createRoom(ChattingRoom room) {
      
        hashOperations.put(ChattingRoomKey, room.getRoom_id(), room);
        return room.getRoom_id();
    }

    public Long deleteRoom(Long sellerId, Long buyerId) {
        
        return hashOperations.delete(ChattingRoomKey, String.valueOf(buyerId) +  String.valueOf(sellerId));
    }

    public void enterChattingRoom(String roomId) {

        ChannelTopic topic = mapTopic.get(roomId);
        if(topic == null) {

            topic = new ChannelTopic(roomId);
            mapTopic.put(roomId, topic);
        }
    }   

    public ChannelTopic getTopic(String roomId) {
        return mapTopic.get(roomId);
    }

    public Map<String, ChannelTopic> getTopics() {
        return mapTopic;
    }

    public List<ChatMsg> getChattingList(String roomId) {
      
        return chatRepository.findByRoomIdOrderByUploadAtAsc(roomId);
    }
}