package com.example.demo.Redis;

import com.example.demo.Model.ChatMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatPub {

    private final RedisTemplate<String, Object> redisTemplate;
    

   

    public void publish(ChannelTopic topic, ChatMsg msg) {        
        System.out.println("Test2");
        redisTemplate.convertAndSend(topic.getTopic(), msg);
    }
}