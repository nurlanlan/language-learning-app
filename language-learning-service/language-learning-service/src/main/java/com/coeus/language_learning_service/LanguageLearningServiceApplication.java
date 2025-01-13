package com.coeus.language_learning_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.coeus.language_learning_service")
public class LanguageLearningServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanguageLearningServiceApplication.class, args);
	}

}
