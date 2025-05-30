package com.example.todo_list_api.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsById(Long id);
}
