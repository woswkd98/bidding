package com.example.demo.Redis;

import com.example.demo.entity.ChatMsg;
import com.example.demo.repository.master.ChatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class ChatSub implements MessageListener {
    
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRepository chatRepo;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // TODO Auto-generated method stub
        String pubMsg = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
        ChatMsg msg = null;
        System.out.print("test");

        
        try {
            msg = objectMapper.readValue(pubMsg, ChatMsg.class);
            msg.setState(1);
            System.out.print(msg.getRoomId());
            System.out.println(msg.getMessage());    
            System.out.println(msg.getUploadAt());    
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
     
        messagingTemplate.convertAndSend("/sub/room/" + msg.getRoomId(), msg);
        
    }
}