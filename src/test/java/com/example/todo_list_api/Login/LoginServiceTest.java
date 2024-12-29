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

import com.example.todo_list_api.Registration.InvalidInputException;
import com.example.todo_list_api.Registration.Token;
import com.example.todo_list_api.Registration.UserRegistration;
import com.example.todo_list_api.repository.AuthRepository;
import com.example.todo_list_api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @InjectMocks
    private LoginService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthRepository authRepository;

    // update token
    @Test
    void updateToken()
    {
        UserRegistration newUser = new UserRegistration("wicked", "wicked@wick.com", "revenge");
        userRepository.save(newUser);

        Token newToken = new Token(newUser.getId());
        authRepository.save(newToken);

        when(authRepository.save(any(Token.class))).thenReturn(newToken);
        
        Token updatedToken = service.loginUser(newUser);

        assertNotNull(newToken);
        assertEquals(newToken.getId(), updatedToken.getId());
        assertNotEquals(newToken.getToken(), updatedToken.getToken());
    }

    // match password
    @Test
    void shouldThrowInvalidPassword() throws Exception
    {
        UserRegistration newUser = new UserRegistration("wicked", "wicked@wick.com", "revenge");
        userRepository.save(newUser);
        UserRegistration wrongPassword = new UserRegistration("wicked", "wicked@wick.com", "avenge");
        // UserRegistration foundUser = userRepository.findByEmail(newUser.getEmail());
        // boolean isPasswordValid = newUser.isPasswordValid(foundUser.getPassword());

        Exception e = assertThrows(InvalidInputException.class, () -> service.loginUser(wrongPassword));
        assertEquals("Login failed. Your password is incorrect. Please try again.", e.getMessage());
        // assertEquals(false, isEmailValid);
    }
}
