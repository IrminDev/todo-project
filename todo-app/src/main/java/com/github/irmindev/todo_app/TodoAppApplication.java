package com.github.irmindev.todo_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This is for test commit

@SpringBootApplication(scanBasePackages = "com.github.irmindev.todo_app")
public class TodoAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}
}
