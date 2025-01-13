package com.coeus.language_learning_service.service;

import com.coeus.language_learning_service.domain.entity.User;
import com.coeus.language_learning_service.domain.repository.UserRepository;
import com.coeus.language_learning_service.model.dto.UserDTO;
import com.coeus.language_learning_service.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Registration
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        User user = userMapper.toEntity(userDTO);
        // Password encoding and security is removed here
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    // Login
    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong credentials");
        }
        // JWT token logic would go here (if needed)
        return "JWT token";
    }

    // Profile details
    public UserDTO getProfile(Long userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    // Update profile
    public UserDTO updateProfile(Long userID, UserDTO userDTO) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());

        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());  // No encoding, just plain password
        }

        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }
}
