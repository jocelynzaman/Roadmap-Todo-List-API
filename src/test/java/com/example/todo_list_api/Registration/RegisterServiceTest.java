package com.example.todo_list_api.Registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todo_list_api.Authentication.AuthRepository;
import com.example.todo_list_api.User.User;
import com.example.todo_list_api.User.UserRepository;
import com.example.todo_list_api.User.UserService;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {
    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthRepository authRepository;

    @Test
    void saveUser()
    {
        User createdUser = service.createUser("John Wick");
        assertNotNull(createdUser);
        assertEquals(createdUser.getName(), "John Wick");
    }
}
