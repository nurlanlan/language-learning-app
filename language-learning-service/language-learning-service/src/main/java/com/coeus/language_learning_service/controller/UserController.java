package com.coeus.language_learning_service.controller;

import com.coeus.language_learning_service.model.dto.UserDTO;
import com.coeus.language_learning_service.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class UserController {
   private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
       String jwtToken = userService.loginUser(email, password);
       return ResponseEntity.ok(jwtToken);
    }
    @GetMapping("/profile/{userID}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable Long userID) {
        UserDTO userDTO = userService.getProfile(userID);
        return ResponseEntity.ok(userDTO);
    }
    @PutMapping("/profile/{userID}")
    public ResponseEntity<UserDTO> updateProfile(@PathVariable Long userID, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateProfile(userID, userDTO);
        return ResponseEntity.ok(updatedUser);
    }
}
