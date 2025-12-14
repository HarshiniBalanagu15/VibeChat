package com.example.vibechat.controller;

import com.example.vibechat.JwtUtil;
import com.example.vibechat.model.User;
import com.example.vibechat.repository.ChatRepository;
import com.example.vibechat.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User request) {
        try {
            String msg = userService.signUp(request);
            return ResponseEntity.ok("User created successfully ✔");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User req){
        try {
            String msg = userService.signIn(req);
            if(msg.equals("Success")){
                String token = JwtUtil.generateToken(req.getUserName());
                return ResponseEntity.ok(Map.of("username", req.getUserName(), "token", token));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msg);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server error: " + e.getMessage());
        }
    }
    @GetMapping("/getNewUsersForChat/{user}")
    public ResponseEntity<?> getAllUsers(@PathVariable String user){
        try {
            List<String> users = userService.getAllNewChatUsers(user);
            return ResponseEntity.ok().body(Map.of("users",users));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server error: " + e.getMessage());
        }
    }


}
