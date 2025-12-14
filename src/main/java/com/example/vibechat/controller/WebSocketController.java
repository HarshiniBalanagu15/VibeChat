package com.example.vibechat.controller;

import com.example.vibechat.model.Chat;
import com.example.vibechat.service.ChatService;
import com.example.vibechat.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    public WebSocketController(SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }   

    @MessageMapping("/chat.send")
    public void send(Chat msg) {
        msg.setTime(LocalDateTime.now());

        chatService.updateChat(msg.getBelongToChatRoom(), msg);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + msg.getBelongToChatRoom(),
                msg
        );
    }
}
