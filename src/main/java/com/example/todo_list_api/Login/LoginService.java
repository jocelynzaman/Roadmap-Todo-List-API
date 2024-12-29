package com.example.todo_list_api.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo_list_api.Registration.InvalidInputException;
import com.example.todo_list_api.Registration.Token;
import com.example.todo_list_api.Registration.UserRegistration;
import com.example.todo_list_api.repository.AuthRepository;
import com.example.todo_list_api.repository.UserRepository;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRepository;

    public Token loginUser(UserRegistration userRegistration)
    {
        // validate email exists
        UserRegistration foundUser = userRepository.findByEmail(userRegistration.getEmail());
        if (foundUser != null)
        {
            // validate password is valid
            boolean isPasswordValid = userRegistration.isPasswordValid(foundUser.getPassword());

            if (isPasswordValid) 
            {
                // update token
                Token foundToken = authRepository.findById(foundUser.getId()).get();
                foundToken.updateToken();
                authRepository.save(foundToken);
                return foundToken;
            }
            else
            {
                throw new InvalidInputException("Login", "password");
            }
        }
        else
        {
            throw new InvalidInputException("Login", "email");
        }
    }
}
