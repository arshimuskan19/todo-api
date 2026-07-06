package com.arshi.todo_api.repository;

import com.arshi.todo_api.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserName(String userName);
}
