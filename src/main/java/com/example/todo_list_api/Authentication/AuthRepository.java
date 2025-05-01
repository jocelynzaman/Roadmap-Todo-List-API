package com.example.todo_list_api.Authentication;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AuthRepository extends CrudRepository<Authentication, Long> {
    Optional<Authentication> findById(Long id);
    Authentication findByToken(String token);
    boolean existsByEmail(String email);
    Authentication findByEmail(String email);
}
