package com.github.irmindev.todo_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.irmindev.todo_backend.repository.TodoRepository;

@RestController
@RequestMapping("/health")
public class HealthController {
    
    @Autowired
    private TodoRepository todoRepository;
    
    // Check the database connection
    @GetMapping
    public String checkHealth() {
        todoRepository.findAll();
        return "OK";
    }
}