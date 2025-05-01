package com.example.todo_list_api.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo_list_api.exceptions.InvalidInputException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // create user
    public User createUser(String name) {
        User newUser = new User(name);
        userRepository.save(newUser);
        return newUser;
    }

    // retrieve user
    public User getUser(Long userId)
    {
        User foundUser = userRepository.findById(userId).get();
        if (foundUser != null)
        {
            return foundUser;
        }
        else
        {
            throw new InvalidInputException("Authorization", "token");
        }
    }
}
