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
import com.example.demo.repository.master.ChatRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {


    private final ChatService chatService;

    private final RedisMessageListenerContainer redisMessageListener;
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatPub chatPub;

    private final ChatSub chatSub;

    @MessageMapping("/chat") 
    public void message(SendingMsg msg) {
        
        ChatMsg newMsg = new ChatMsg();
        
        newMsg.setUploadAt(GetTimeZone.StringToDate(GetTimeZone.getSeoulDate()));
        newMsg.setUserName(msg.getUserName());
        newMsg.setMessage(msg.getMessage());
        newMsg.setRoomId(msg.getRoomId());
        
        ChannelTopic topic = chatService.getTopic(newMsg.getRoomId());
        
        
        chatPub.publish(topic, newMsg);
    }

    @RequestMapping(value = "/rooms", method = RequestMethod.PUT)
    public String createRoom(@RequestBody ChattingRoom room) {
 
        System.out.println(room.getRoom_id());
        chatService.enterChattingRoom(room.getRoom_id());
        room.setRoom_id(room.getRoom_id());
        chatService.createRoom(room);
        ChannelTopic channel = chatService.getTopic(room.getRoom_id());
        redisMessageListener.addMessageListener(chatSub, channel);
        return room.getRoom_id();
    }    

    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public Map<String, ChattingRoom> getAllRoom() {
        return chatService.getRooms();
    }

    @RequestMapping(value = "/rooms/{requestId}/{sellerId}", method = RequestMethod.DELETE)
    public String deleteRoom(@PathVariable long requestId, @PathVariable long sellerId) {
        ChannelTopic channel = chatService.getTopic(requestId + "/" + sellerId);
        redisMessageListener.removeMessageListener(chatSub, channel);
        return "deleteRoom";
    }

    @RequestMapping(value = "/chats/{requestId}/{sellerId}", method = RequestMethod.GET) 
    public List<ChatMsg> getChatMsgs(@PathVariable long requestId, @PathVariable long sellerId) {
        return chatService.getChattingList(requestId + "/" + sellerId);
    } 
}