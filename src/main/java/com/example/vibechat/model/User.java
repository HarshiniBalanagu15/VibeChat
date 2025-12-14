package com.example.vibechat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;

}