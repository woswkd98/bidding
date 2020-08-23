package com.example.demo.Redis;

import com.example.demo.Model.ChatMsg;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class ChatPub {
    private final RedisTemplate<String, Object> redisTemplate;

    public ChatPub(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    } 

    public void publish(ChannelTopic topic, ChatMsg msg) {
        redisTemplate.convertAndSend(topic.getTopic(), msg);
    }
}