package com.example.vibechat.controller;

import com.example.vibechat.model.User;
import com.example.vibechat.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
     private final UserRepository userRepo;

     public UserController(UserRepository userRepo){
         this.userRepo = userRepo;
     }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User request) {
         System.out.println(request);
        try {
            userRepo.save(request);
            return ResponseEntity.ok("User created successfully ✔");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server error: " + e.getMessage());
        }
    }
}
