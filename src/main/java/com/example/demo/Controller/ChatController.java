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
        System.out.println("message");
        ChatMsg newMsg = new ChatMsg();
        
        newMsg.setUploadAt(GetTimeZone.StringToDate(GetTimeZone.getSeoulDate()));
        newMsg.setUserName(msg.getUserName());
        newMsg.setContext(msg.getContext());
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

    @RequestMapping(value = "/rooms/test/topic", method = RequestMethod.GET)
    public Set<String> getAllTopics() {
        return chatService.getTopics().keySet();
    }

    @RequestMapping(value = "/rooms", method = RequestMethod.DELETE)
    public String deleteRoom(@PathVariable String roomId) {
        ChannelTopic channel = chatService.getTopic(roomId);
        redisMessageListener.removeMessageListener(chatSub, channel);
        return "deleteRoom";
    }

    @RequestMapping(value = "/rooms/{roomId}", method = RequestMethod.GET)
    public Map<String, Object> getChats(@PathVariable String roomId) {
        Map<String, Object> test = new HashMap<String, Object>();
        List<ChatMsg> temp = new ArrayList<ChatMsg>();
        for(int i =0 ; i< 5; ++i) {
            ChatMsg ms = new ChatMsg();
            ms.setId(15215L);
            ms.setRoomId("125" + i);
            temp.add(ms);
        }
       

        test.put("chats", temp);
        
        
        return test;
    }


    @RequestMapping(value = "/chats/{roomId}", method = RequestMethod.GET) 
    public List<ChatMsg> getChatMsgs(@RequestParam String roomId) {
        return chatService.getChattingList(roomId);
    } 
}