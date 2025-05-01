package com.example.todo_list_api.User;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo_list_api.Authentication.Authentication;
import com.example.todo_list_api.Authentication.AuthenticationService;
import com.example.todo_list_api.exceptions.DuplicateInputException;
import com.example.todo_list_api.exceptions.InvalidInputException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/register")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationService authService;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRegistration) {
        // store user registration
        try
        {
            // valid inputs aren't empty
            if (userRegistration == null || userRegistration.getName().equals("") || userRegistration.getEmail().equals("") || userRegistration.getPassword().equals(""))
            {
                throw new InvalidInputException(userRegistration);
            }
            
            // validate authentication
            if (!authService.isAuthenticationValidated(userRegistration.getEmail(), userRegistration.getPassword()))
            {
                throw new InvalidInputException(userRegistration);
            }

            // create user
            // User newUser = new User(userRegistration.getName());
            User newUser = service.createUser(userRegistration.getName());

            // create token
            Authentication auth = authService.createAuthentication(newUser, userRegistration.getEmail(), userRegistration.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(auth.getToken());
        }
        // catch exceptions
        catch (InvalidInputException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        catch (DuplicateInputException d)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(d.getMessage());
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, d.getMessage(), d);
        }
    }
}
