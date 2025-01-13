package com.coeus.language_learning_service.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "app_users")
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long userID;
    private String name;
    private String surname;
    private String password;
    private String email;
    private int age;

}
