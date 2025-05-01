package com.example.todo_list_api.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo_list_api.User.User;
import com.example.todo_list_api.exceptions.DuplicateInputException;
import com.example.todo_list_api.exceptions.InvalidInputException;

@Service
public class AuthenticationService {
    @Autowired
    private AuthRepository authRepository;

    // validate email format is correct
    public boolean isEmailValid(String email)
    {
        String regex = "^([a-zA-Z0-9]+[_.-]*[a-zA-Z0-9]+)@([a-zA-Z0-9-]+).([a-zA-Z][a-zA-Z]+)";
        String regex2 = "^([a-zA-Z0-9]+)@([a-zA-Z0-9-]+).([a-zA-Z][a-zA-Z]+)";
        return ((email).matches(regex)) || ((email).matches(regex2));
    }

    // validate authentication
    public boolean isAuthenticationValidated(String email, String password) {
        // validate email format is correct
        if (!isEmailValid(email))
        {
            throw new InvalidInputException(email);
        }
        // validate unique email
        else if (authRepository.existsByEmail(email))
        {
            throw new DuplicateInputException(email);
        }
        // more validation for strong password
        else 
        {
            return true;
        }
    }

    // create authentication
    public Authentication createAuthentication(User userInfo, String email, String password) {
        Authentication newAuth = new Authentication(userInfo, email, password);
        newAuth.hashPassword(password);
        authRepository.save(newAuth);
        return newAuth;
    }

    // update authentication
    public Authentication authenticateUser(String email, String password)
    {
        // validate email exists
        Authentication foundUser = authRepository.findByEmail(email);
        if (foundUser != null)
        {
            // validate password is valid
            boolean isPasswordValid = foundUser.isPasswordValid(password);

            if (isPasswordValid) 
            {
                // update token
                foundUser.updateToken();
                authRepository.save(foundUser);
                return foundUser;
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

    // retrieve authentication
    public Authentication getAuthentication(String authToken)
    {
        Authentication foundToken = authRepository.findByToken(authToken);
        if (foundToken != null)
        {
            return foundToken;
        }
        else
        {
            throw new InvalidInputException("Authorization", "token");
        }
    }
}
