package com.example.vibechat.service;

import com.example.vibechat.model.User;
import com.example.vibechat.repository.ChatRepository;
import com.example.vibechat.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final ChatRepository chatRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, ChatRepository chatRepo){
        this.userRepo = userRepo;
        this.chatRepo = chatRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public String signUp(User user){
        String uname = user.getUserName();
        Optional<User> temp = userRepo.findByUsername(uname);
        if(temp.isPresent()){
            return "User Present";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "Success";
    }

    public String signIn(User user){
        String uname = user.getUserName();
        Optional<User> temp = userRepo.findByUsername(uname);
        if(temp.isEmpty()){ return "User not Present";}
        if(passwordEncoder.matches(user.getPassword(), temp.get().getPassword())) {
            return "Success";
        }
        return "Invalid Password";
    }

    public List<String> getAllNewChatUsers(String user) {
        List<String> existing = new ArrayList<>(chatRepo.getUsersInChatWithUser(user));
        existing.add(user);
        List<String> all = userRepo.findAllUsers();
        return all.stream()
                .filter(username -> !existing.contains(username))
                .toList();
    }

}
