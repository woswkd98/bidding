package com.example.demo.Redis;

import com.example.demo.Model.ChatMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class ChatSub implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messageSendingOperations;

    public ChatSub(ObjectMapper objectMapper, RedisTemplate redisTemplate,
            SimpMessageSendingOperations messageSendingOperations) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.messageSendingOperations = messageSendingOperations;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // TODO Auto-generated method stub
        String pubMsg = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
        ChatMsg msg = null;
        try {
            msg = objectMapper.readValue(pubMsg, ChatMsg.class);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        messageSendingOperations.convertAndSend("/chatSub/room/" + msg.getRoomId(), msg);
        
    }
}