package com.example.todo_list_api.Todo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.todo_list_api.Registration.InvalidInputException;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/todos")
public class TodoController {
    @Autowired
    private TodoService service;

    @PostMapping
    public ResponseEntity<Todo> createTodos(@RequestHeader("Authorization") String authToken, @RequestBody Todo newTodo) 
    {
        // check token from Authorization header is valid
        try
        {
            if (authToken ==  null || authToken.isEmpty())
            {
                throw new InvalidInputException("Authentication", "token");
            }

            Todo todo = service.createTodo(authToken.substring(7), newTodo);
            return new ResponseEntity<Todo>(todo, HttpStatus.OK);
        }
        catch (InvalidInputException exception)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodos(@RequestHeader("Authorization") String authToken, @PathVariable("id") long param, @RequestBody Todo updateTodo) 
    {
        try
        {
            Todo todo = service.updateTodo(authToken.substring(7), Long.valueOf(param), updateTodo);
            return new ResponseEntity<Todo>(todo, HttpStatus.OK);
        }
        catch (InvalidInputException exception)
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeTodos(@RequestHeader("Authorization") String authToken, @PathVariable("id") long todoId)
    {
        try
        {
            service.deleteTodo(authToken.substring(7), todoId);
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }   
        catch (InvalidInputException exception)
        {
            return new ResponseEntity<Object>(exception, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    public ResponseEntity<Map> getTodos(@RequestHeader("Authorization") String authToken, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "limit", required = false) Integer limit) 
    {
        try
        {
            Page<Todo> todoList = service.getAllTodos(authToken.substring(7), page = 1, limit = 5);

            LinkedHashMap<String, Object> todoPage = new LinkedHashMap<String, Object>();
            todoPage.put("data", todoList.getContent());
            todoPage.put("page", todoList.getNumber()+1);
            todoPage.put("limit", todoList.getSize());
            todoPage.put("total", todoList.getTotalElements());
            return new ResponseEntity<Map>(todoPage, HttpStatus.OK);
        }
        catch (InvalidInputException exception)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage());
        }
    }
}
