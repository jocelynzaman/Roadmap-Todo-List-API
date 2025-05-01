package com.example.todo_list_api.Todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.todo_list_api.User.User;
import com.example.todo_list_api.exceptions.InvalidInputException;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public Todo getTodoById(Long todoId)
    {
        Todo foundTodo = repository.findById(todoId);
        if (foundTodo != null)
        {
            return foundTodo;
        }
        else
        {
            throw new InvalidInputException("Finding to-do item", "to-do id: " + todoId);
        }
    }

    // check that owner of to-do item matches user
    public void isOwnerOfTodo(Long userId, Long ownerId)
    {
        if (userId != ownerId)
        {
            throw new InvalidInputException("User permission", "token: " + userId);
        }
    }

    // create to do item
    public Todo createTodo(User owner, Todo newTodo)
    {
        newTodo.setUserInfo(owner);
        repository.save(newTodo);
        return newTodo;
    }

    // update to do item
    public Todo updateTodo(Long userId, Long todoId, Todo updateTodo)
    {
        // validate to do item exists
        Todo existingTodo = getTodoById(todoId);

        // validate authorization
        isOwnerOfTodo(userId, existingTodo.getUserInfo().getId());

        // update existing to do item
        existingTodo.setTitle(updateTodo.getTitle());
        existingTodo.setDescription(updateTodo.getDescription());
        repository.save(existingTodo);
        return existingTodo;
    }

    // delete to do item
    public void deleteTodo(Long userId, Long todoId)
    {
        // validate to do item exists
        Todo existingTodo = getTodoById(todoId);

        // validate authorization
        isOwnerOfTodo(userId, existingTodo.getUserInfo().getId());

        repository.deleteById(todoId);
    }

    // retrieve to do items
    public Page<Todo> getAllTodos(Long userId, int page, int limit)
    {
        // TODO: filter by owner

        Pageable pageQuery = PageRequest.of(page-1, limit, Sort.by("id").ascending());

        return repository.findAll(pageQuery);
    }
}
