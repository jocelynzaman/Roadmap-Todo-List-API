package com.example.todo_list_api.Registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo_list_api.repository.AuthRepository;
import com.example.todo_list_api.repository.UserRepository;

@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthRepository authRepository;

    // validate email format is correct
    public boolean isEmailValid(String email)
    {
        String regex = "^([a-zA-Z0-9]+[_.-]*[a-zA-Z0-9]+)@([a-zA-Z0-9-]+).([a-zA-Z][a-zA-Z]+)";
        String regex2 = "^([a-zA-Z0-9]+)@([a-zA-Z0-9-]+).([a-zA-Z][a-zA-Z]+)";
        return ((email).matches(regex)) || ((email).matches(regex2));
    }

    // create user
    public UserRegistration createUser(UserRegistration user) {
        // validate email format is correct
        if (!isEmailValid(user.getEmail()))
        {
            throw new InvalidInputException(user.getEmail());
        }
        // validate unique email
        else if (userRepository.existsByEmail(user.getEmail()))
        {
            throw new DuplicateInputException(user.getEmail());
        }
        else 
        {
            UserRegistration newUser = new UserRegistration(user.getName(), user.getEmail(), user.getPassword());
            userRepository.save(newUser);
            return newUser;
        }
    }

    // create token
    public Token createToken(Long id) {
        Token newToken = new Token(id);
        authRepository.save(newToken);
        return newToken;
    }

    // register user
    public Token registerUser(UserRegistration user) {
        UserRegistration newUser = createUser(user);
        Token newToken = createToken(newUser.getId());
        return newToken;
    }
}
