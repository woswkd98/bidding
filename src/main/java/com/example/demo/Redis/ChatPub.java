package com.example.demo.Redis;

import java.util.Date;

import com.example.demo.Model.ChatMsg;
import com.example.demo.repository.master.ChatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatPub {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatRepository chatRepo;
    public void publish(ChannelTopic topic, ChatMsg msg) {        

        chatRepo.save(msg);
        redisTemplate.convertAndSend(topic.getTopic(), msg);
    }
}