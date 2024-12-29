package com.example.todo_list_api.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo_list_api.Registration.InvalidInputException;
import com.example.todo_list_api.Registration.Token;
import com.example.todo_list_api.Registration.UserRegistration;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService service;
    
    @PostMapping
    public ResponseEntity<String> loginUser(@RequestBody UserRegistration userRegistration) {
        try
        {
            // valid inputs aren't empty
            if (userRegistration == null || userRegistration.getEmail() ==  "" || userRegistration.getPassword() == "")
            {
                throw new InvalidInputException(userRegistration);
            }

            // validate login
            Token token = service.loginUser(userRegistration);
            return ResponseEntity.status(HttpStatus.OK).body(token.getToken());
        }
        catch (InvalidInputException e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
