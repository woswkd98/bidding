package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;

import com.example.demo.Config.GetTimeZone;
import com.example.demo.Model.ChatMsg;
import com.example.demo.Redis.ChatPub;
import com.example.demo.Redis.ChatSub;
import com.example.demo.VO.ChattingRoom;
import com.example.demo.VO.SendingMsg;
import com.example.demo.service.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class RoomController {
    
}