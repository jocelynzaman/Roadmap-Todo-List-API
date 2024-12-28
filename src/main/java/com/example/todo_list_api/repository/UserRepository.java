package com.example.todo_list_api.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.todo_list_api.Registration.UserRegistration;

public interface UserRepository extends CrudRepository<UserRegistration, Long> {
    boolean existsByEmail(String email);
    UserRegistration findByEmail(String email);
}
