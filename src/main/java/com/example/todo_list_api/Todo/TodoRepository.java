package com.example.todo_list_api.Todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Long>  {
    Todo findById(Long id);
    void save(Todo todo);
    void deleteById(Long id);
    Page<Todo> findAll(Pageable pageQuery);
}
