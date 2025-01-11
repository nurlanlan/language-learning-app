package com.coeus.language_learning_service.service;

import com.coeus.language_learning_service.domain.entity.User;
import com.coeus.language_learning_service.domain.repository.UserRepository;
import com.coeus.language_learning_service.model.dto.UserDTO;
import com.coeus.language_learning_service.model.mapper.UserMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    // registeration
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    // login
    public String loginUser(String email, String Password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(Password, user.getPassword())) {
            throw new RuntimeException("Wrong credentials");
        }
        //todo
        return "JWT token";
    }
    // profile details
    public UserDTO getProfile(Long userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }
    // change profile info
    public UserDTO updateProfile(Long userID, UserDTO userDTO) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());
        //
        if(userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }
}
