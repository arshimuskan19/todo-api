package com.arshi.todo_api.controller;

import com.arshi.todo_api.model.Todo;
import com.arshi.todo_api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<Todo> getAllTodos(){
        return todoService.getUserTodos();
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo){
        Todo created = todoService.createTodo(todo);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo){
        return todoService.updateTodo(id, todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
