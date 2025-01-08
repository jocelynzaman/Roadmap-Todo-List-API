package com.example.todo_list_api.Todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.todo_list_api.Registration.InvalidInputException;
import com.example.todo_list_api.Registration.Token;
import com.example.todo_list_api.repository.AuthRepository;
import com.example.todo_list_api.repository.TodoRepository;
import com.example.todo_list_api.repository.UserRepository;

@Service
public class TodoService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    public Token findTokenByValue(String todoToken)
    {
        Token foundToken = authRepository.findByToken(todoToken);
        if (foundToken != null && userRepository.existsById(foundToken.getId()) && foundToken.getToken().equals(todoToken))
        {
            return foundToken;
        }
        else
        {
            throw new InvalidInputException("Authorization", "token");
        }
    }

    public Todo findTodoById(Long todoId)
    {
        Todo foundTodo = todoRepository.findById(todoId);
        if (foundTodo != null)
        {
            return foundTodo;
        }
        else
        {
            throw new InvalidInputException("Finding to-do item", "to-do id");
        }
    }

    public void validateUserId(Long tokenId, Long todoUserId)
    {
        if (!tokenId.equals(todoUserId))
        {
            throw new InvalidInputException("User permission", "token");
        }
    }

    // create to do item
    public Todo createTodo(String todoToken, Todo newTodo)
    {
        // validate authentication
        Token foundToken = findTokenByValue(todoToken);
        System.out.println("ID from user: " + userRepository.findById(foundToken.getId()).get().getId());

        newTodo.setUserId(foundToken.getId());
        System.out.println("ID: " + newTodo.getId());
        todoRepository.save(newTodo);
        return newTodo;
    }

    // update to do item
    public Todo updateTodo(String todoToken, Long todoId, Todo updateTodo)
    {
        // validate authentication
        Token foundToken = findTokenByValue(todoToken);
        System.out.println("ID from user: " + userRepository.findById(foundToken.getId()).get().getId());

        // validate to do item exists
        Todo existingTodo = findTodoById(todoId);

        // validate authorization
        validateUserId(foundToken.getId(), existingTodo.getUserId());

        // update existing to do item
        existingTodo.setTitle(updateTodo.getTitle());
        existingTodo.setDescription(updateTodo.getDescription());
        todoRepository.save(existingTodo);
        return existingTodo;
    }

    // delete to do item
    public void deleteTodo(String todoToken, Long todoId)
    {
        // validate authentication
        Token foundToken = findTokenByValue(todoToken);
        // System.out.println("ID from user: " + userRepository.findById(foundToken.getId()).get().getId());

        // validate to do item exists
        Todo existingTodo = findTodoById(todoId);

        // validate authorization
        validateUserId(foundToken.getId(), existingTodo.getUserId());

        todoRepository.deleteById(todoId);
    }

    // retrieve to do items
    public Page<Todo> getAllTodos(String todoToken, int page, int limit)
    {
        // validate authorization
        Token foundToken = findTokenByValue(todoToken);
        System.out.println("ID from user: " + userRepository.findById(foundToken.getId()).get().getId());

        Pageable pageQuery = PageRequest.of(page-1, limit, Sort.by("id").ascending());

        return todoRepository.findAll(pageQuery);
    }
}
