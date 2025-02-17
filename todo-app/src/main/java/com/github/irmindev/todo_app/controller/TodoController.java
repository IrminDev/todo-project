package com.github.irmindev.todo_app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import com.github.irmindev.todo_app.model.Todo;

import java.util.List;
import java.util.Map;

@Controller
public class TodoController {

    @Value("${backend.url}")
    private String backendUrl;

    @RequestMapping("/")
    public String showHomePage(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(backendUrl + "/todos", List.class);
            List<Todo> todos = response.getBody();
            model.addAttribute("todos", todos);
        } catch (Exception e) {
            model.addAttribute("todos", List.of());
        }
        
        model.addAttribute("imageUrl", "/image");
        return "index";
    }

    @PostMapping("/addTodo")
    public String addTodo(@RequestParam("inputField") String todo) {
        RestTemplate restTemplate = new RestTemplate();
        Todo newTodo = new Todo();
        newTodo.setTodo(todo);
        newTodo.setDone(false);
        restTemplate.postForObject(backendUrl + "/todos", newTodo, Todo.class);
        return "redirect:/";
    }
}