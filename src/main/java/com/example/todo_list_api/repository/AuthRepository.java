package com.example.todo_list_api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.todo_list_api.Registration.Token;

public interface AuthRepository extends CrudRepository<Token, Long> {
    Optional<Token> findById(Long id);
}
