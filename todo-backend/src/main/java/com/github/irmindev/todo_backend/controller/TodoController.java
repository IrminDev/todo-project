package com.github.irmindev.todo_backend.controller;

import com.github.irmindev.todo_backend.model.Todo;
import com.github.irmindev.todo_backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        if(todo.getTodo().length() > 140) {
            System.out.println("Todo is too long");
            return null;
        }

        System.out.println("Todo is valid: " + todo.getTodo());
        return todoRepository.save(todo);
    }
}