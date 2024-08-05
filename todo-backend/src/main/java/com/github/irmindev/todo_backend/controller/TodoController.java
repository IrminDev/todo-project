package com.github.irmindev.todo_backend.controller;

import com.github.irmindev.todo_backend.model.Todo;
import com.github.irmindev.todo_backend.repository.TodoRepository;
import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private Connection natsConnection;

    @Value("${nats.subject}")
    private String natsSubject;

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        if (todo.getTodo().length() > 140) {
            System.out.println("Todo is too long");
            return null;
        }

        System.out.println("Todo is valid: " + todo.getTodo());
        Todo savedTodo = todoRepository.save(todo);

        // Enviar mensaje a NATS
        try {
            String message = "Todo created: " + savedTodo.getId() + " - " + savedTodo.getTodo();
            natsConnection.publish(natsSubject, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Message sent to NATS: " + message);
        } catch (Exception e) {
            System.err.println("Failed to send message to NATS: " + e.getMessage());
        }

        return savedTodo;
    }

    @PutMapping("/{id}")
    public Todo doneTodo(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo == null) {
            System.out.println("Todo not found");
            return null;
        }

        todo.setDone(true);
        return todoRepository.save(todo);
    }
}