package com.example.vibechat.service;

import com.example.vibechat.model.Chat;
import com.example.vibechat.model.User;
import com.example.vibechat.repository.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatRepository chatRepo;

    ChatService(ChatRepository chatRepo){
        this.chatRepo = chatRepo;

    }

    public void createChatRoom(String u1, String u2){
        String chatRoomId =  u1.compareTo(u2) < 0 ? u1.concat(u2) : u2.concat(u1);
        chatRepo.createChatRoom(chatRoomId, u1, u2);
    }

    public void updateChat(String ChatId, Chat chat){
        chatRepo.updateChat(ChatId, chat);
    }

}
