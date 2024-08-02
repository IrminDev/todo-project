package com.github.irmindev.todo_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.irmindev.todo_backend")
public class TodoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoBackendApplication.class, args);
	}

}
