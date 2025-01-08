package com.example.todo_list_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.todo_list_api.Todo.Todo;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Long>  {
    Todo findById(Long id);
    void save(Todo todo);
    void deleteById(Long id);
    Page<Todo> findAll(Pageable pageQuery);
}
