package com.example.vibechat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    private Long id;
    private LocalDateTime time;
    private String sender;
    private String message;
    private String chatId;

}
