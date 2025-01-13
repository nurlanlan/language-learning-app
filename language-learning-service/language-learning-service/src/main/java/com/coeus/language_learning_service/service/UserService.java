package com.coeus.language_learning_service.service;

import com.coeus.language_learning_service.domain.entity.User;
import com.coeus.language_learning_service.domain.repository.UserRepository;
import com.coeus.language_learning_service.model.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    // UserDTO-dan User Entity-ə çevrilmə
    private User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAge(userDTO.getAge());
        return user;
    }

    // User Entity-dən UserDTO-ya çevrilmə
    private UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAge(user.getAge());
        return userDTO;
    }

    // Registration
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        User user = toEntity(userDTO);
        user = userRepository.save(user);
        return toDTO(user);
    }

    // Login
    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong credentials");
        }
        // todo: JWT token yaratmaq
        return "JWT token";
    }

    // Profile details
    public UserDTO getProfile(Long userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDTO(user);
    }

    // Change profile info
    public UserDTO updateProfile(Long userID, UserDTO userDTO) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());
        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());
        }
        user = userRepository.save(user);
        return toDTO(user);
    }

    // Load user by username
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>() // Rollar və hüquqlar (lazım gələrsə əlavə edə bilərsiniz)
        );
    }
}
