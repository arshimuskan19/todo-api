package com.arshi.todo_api.service;

import com.arshi.todo_api.exception.TodoNotFoundException;
import com.arshi.todo_api.exception.UnauthorizedAccessException;
import com.arshi.todo_api.model.Todo;
import com.arshi.todo_api.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodo(Todo todo){
        todo.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        return todoRepository.save(todo);
    }

    public List<Todo> getUserTodos(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return todoRepository.findByUserName(userName);
    }

    public Todo updateTodo(Long id, Todo updatedTodo){
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Todo existingTodo = getUserById(id);
        if(!existingTodo.getUserName().equals(loggedInUser)){
            throw new UnauthorizedAccessException("You are not allowed to modify another users todos");
        }
        existingTodo.setDescription(updatedTodo.getDescription());
        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setCompleted(updatedTodo.isCompleted());
       return todoRepository.save(existingTodo);
    }

    public Todo getUserById(Long id) {
       return todoRepository.findById(id).orElseThrow(()-> new TodoNotFoundException("Todo is not found for "+id +" user id"));
    }

    public void deleteTodo(Long id){
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Todo existingTodo = getUserById(id);

        if(!existingTodo.getUserName().equals(loggedInUser)){
            throw new UnauthorizedAccessException("You are not allowed to modify another users todos");
        }
        todoRepository.delete(existingTodo);
    }
}
