package com.coeus.language_learning_service.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
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
