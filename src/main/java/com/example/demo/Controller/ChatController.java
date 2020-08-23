package com.example.demo.Controller;

import java.util.List;

import com.example.demo.Model.ChatMsg;
import com.example.demo.VO.ChatRoom;
import com.example.demo.service.ChatService;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {


    private final ChatService chatService;
    private final SimpMessageSendingOperations messagingTemplate;

    public ChatController(
        SimpMessageSendingOperations messagingTemplate,
        ChatService chatService
    ) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }
    
    @MessageMapping("/chat/put")
    public void message(ChatMsg message) {
        
    }

    @RequestMapping(value = "/verify/{userEmail}", method = RequestMethod.GET)  
    public String rooms(Model model) {
        return "/chat/room";
    }

    @GetMapping("/rooms")
    public List<ChatRoom> room() {
        return chatService.getRooms();
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}