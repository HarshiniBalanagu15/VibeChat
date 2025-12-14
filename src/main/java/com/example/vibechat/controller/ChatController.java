package com.example.vibechat.controller;

import com.example.vibechat.dto.ChatRoomRequest;
import com.example.vibechat.model.Chat;
import com.example.vibechat.repository.ChatRepository;
import com.example.vibechat.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final ChatRepository chatRepo;

    public ChatController(ChatService chatService, ChatRepository chatRepo){
        this.chatService = chatService;
        this.chatRepo = chatRepo;
    }

    @PostMapping("/createNewChat")
    public ResponseEntity<?> CreateNewChat(@RequestBody ChatRoomRequest req){
        chatService.createChatRoom(req.getUser1(), req.getUser2());
        return ResponseEntity.ok().body(Collections.singletonMap("msg", "Successful"));
    }

    @PostMapping("/updateChat")
    public ResponseEntity<?> updateChat(@RequestBody Chat req){
        chatService.updateChat(req.getBelongToChatRoom(), req);
        return ResponseEntity.ok().body(Collections.singletonMap("msg", "Successful"));
    }

    @GetMapping("/getChat/{chatRoomId}")
    public ResponseEntity<?> getChat(@PathVariable String chatRoomId){
        return ResponseEntity.ok(chatRepo.getChat(chatRoomId));
    }

    @GetMapping("/getChatsForUser/{user}")
    public ResponseEntity<?> getChatsForUSer(@PathVariable String user){
        return ResponseEntity.ok(chatRepo.getChatsForUser(user));
    }

}
