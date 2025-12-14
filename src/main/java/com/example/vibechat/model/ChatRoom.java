package com.example.vibechat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    private String chatRoomId;
    private List<String> hasMessages;
    private List<String> hasUsers;

}
