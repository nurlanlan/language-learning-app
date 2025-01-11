package com.coeus.language_learning_service.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String surname;
    private String password;
    private String email;
    private int age;
}
