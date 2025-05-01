package com.example.todo_list_api.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todo_list_api.User.User;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @InjectMocks
    private TodoService service;

    @Mock
    private TodoRepository todoRepository;

    @Test
    public void createTodoItem()
    {
        User owner = new User("Bob");
        Todo todoItem = new Todo("grocery", "milk and eggs");

        Todo createdTodo = service.createTodo(owner, todoItem);

        assertNotNull(createdTodo);
        assertEquals(createdTodo.getUserInfo(), owner);
        assertEquals(createdTodo.getId(), todoItem.getId());
        assertEquals(createdTodo.getTitle(), todoItem.getTitle());
        assertEquals(createdTodo.getDescription(), todoItem.getDescription());
    }

    @Test
    public void updateTodoItem()
    {
        User owner = new User("Bob");
        Todo todoItem = new Todo("grocery", "milk and eggs");
        Todo createdTodoItem = service.createTodo(owner, todoItem);

        Todo editedTodoItem = new Todo("milk and cookies");

        when(todoRepository.findById(todoItem.getId())).thenReturn(todoItem);

        Todo updatedTodo = service.updateTodo(owner.getId(), todoItem.getId(), editedTodoItem);

        assertNotNull(updatedTodo);
        assertEquals(owner, updatedTodo.getUserInfo());
        assertEquals(todoItem.getTitle(), updatedTodo.getTitle());
        assertEquals(editedTodoItem.getDescription(), updatedTodo.getDescription());
        assertNotEquals("milk and eggs", updatedTodo.getDescription());
    }

    @Test
    public void deleteTodoItem()
    {
        User owner = new User("Bob");
        Todo todoItem = new Todo("grocery", "milk and eggs");
        Todo createdTodoItem = service.createTodo(owner, todoItem);

        when(todoRepository.findById(todoItem.getId())).thenReturn(todoItem);
        service.deleteTodo(owner.getId(), todoItem.getId());

        when(todoRepository.findById(todoItem.getId())).thenReturn(null);
        boolean isItemDeleted = (todoRepository.findById(todoItem.getId()) == null);
        
        assertEquals(true, isItemDeleted);
    }
}
