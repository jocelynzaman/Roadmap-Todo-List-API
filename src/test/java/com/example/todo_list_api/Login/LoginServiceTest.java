package com.example.todo_list_api.Login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todo_list_api.Authentication.AuthRepository;
import com.example.todo_list_api.Authentication.Authentication;
import com.example.todo_list_api.Authentication.AuthenticationService;
import com.example.todo_list_api.User.User;
import com.example.todo_list_api.User.UserRegistrationRequest;
import com.example.todo_list_api.User.UserRepository;
import com.example.todo_list_api.exceptions.DuplicateInputException;
import com.example.todo_list_api.exceptions.InvalidInputException;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @InjectMocks
    private AuthenticationService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthRepository authRepository;

    // create token
    @Test
    public void createToken()
    {
        // set up a new user request
        UserRegistrationRequest userToRegister = new UserRegistrationRequest("wicked", "wicked@wick.com", "revenge");

        User newUser = new User(userToRegister.getName());
        userRepository.save(newUser);
        // when(authRepository.save(any(Authentication.class))).thenReturn(newToken);
        
        Authentication createdToken = service.createAuthentication(newUser, userToRegister.getEmail(), userToRegister.getPassword());
        assertNotNull(createdToken);
        assertEquals(newUser.getId(), createdToken.getUserInfo().getId());
    }

    @Test
    public void shouldThrowInvalidEmail() throws Exception
    {
        UserRegistrationRequest userToRegister = new UserRegistrationRequest("wicked", "-wicked@wick.com", "revenge");

        Exception e = assertThrows(InvalidInputException.class, () -> service.isAuthenticationValidated(userToRegister.getEmail(), userToRegister.getPassword()));
        assertEquals(userToRegister.getEmail() + " is invalid. Please correct and try again", e.getMessage());
    }

    // additional tests for Postman
    // 1. duplicate email: output "[email] already has an account"
    // 2. invalid password: output "Login failed. Your password is incorrect. Please try again."
    // 3. update token: new token is created and previous token is invalid
}
