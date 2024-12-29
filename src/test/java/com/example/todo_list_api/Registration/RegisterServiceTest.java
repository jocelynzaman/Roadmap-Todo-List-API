package com.example.todo_list_api.Registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todo_list_api.repository.AuthRepository;
import com.example.todo_list_api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {
    @InjectMocks
    private RegistrationService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthRepository authRepository;

    @Test
    void saveUser()
    {
        UserRegistration newUser = new UserRegistration("John Wick", "John.Wick@gmail.com", "revenge");

        when(userRepository.save(any(UserRegistration.class))).thenReturn(newUser);
        
        UserRegistration createdUser = service.createUser(newUser);
        assertNotNull(createdUser);
        assertEquals(createdUser.getEmail(), "John.Wick@gmail.com");
    }

    @Test
    void saveToken()
    {
        long id = 1052;
        Token newToken = new Token(Long.valueOf(id));

        when(authRepository.save(any(Token.class))).thenReturn(newToken);
        
        Token createdToken = service.createToken(Long.valueOf(id));
        assertNotNull(createdToken);
        assertEquals(createdToken.getId(), newToken.getId());
    }

    @Test
    void shouldThrowInvalidEmail() throws Exception
    {
        UserRegistration newUser = new UserRegistration("wicked", "-@wick.com", "revenge");
        boolean isEmailValid = service.isEmailValid(newUser.getEmail());

        Exception e = assertThrows(InvalidInputException.class, () -> service.createUser(newUser));
        assertEquals(newUser.getEmail() + " is invalid. Please correct and try again", e.getMessage());
        assertEquals(false, isEmailValid);
    }

    @Test
    void shouldThrowDuplicateEmail() throws Exception
    {
        UserRegistration newUser = new UserRegistration("wicked", "john@wick.com", "revenge");

        // when(userRepository.save(any(UserRegistration.class))).thenReturn(newUser);
        UserRegistration createdUser = service.createUser(newUser);
        // userRepository.save(newUser);
        

        Exception e = assertThrows(DuplicateInputException.class, () -> service.createUser(newUser));
        assertEquals(newUser.getEmail() + " already has an account", e.getMessage());
    }
}
