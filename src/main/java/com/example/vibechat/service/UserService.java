package com.example.vibechat.service;

import com.example.vibechat.model.User;
import com.example.vibechat.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public String signUp(User user){
        String uname = user.getUserName();
        Optional<User> temp = userRepo.findByUsername(uname);
        if(temp.isPresent()){
            return "User Present";
        }
        userRepo.save(user);
        return "Success";
    }

    public String signIn(User user){
        String uname = user.getUserName();
        Optional<User> temp = userRepo.findByUsername(uname);
        System.out.println(temp);
        System.out.println(user);
        if(temp.isEmpty()){ return "User not Present";}
        if(temp.get().getPassword().equals(user.getPassword())) {
            return "Success";
        }
        return "Invalid Password";
    }

}
