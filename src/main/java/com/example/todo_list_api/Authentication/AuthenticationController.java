package com.example.todo_list_api.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo_list_api.User.User;
import com.example.todo_list_api.User.UserService;
import com.example.todo_list_api.exceptions.InvalidInputException;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> loginUser(@RequestBody Authentication userRegistration) {
        try
        {
            // valid inputs aren't empty
            if (userRegistration == null || userRegistration.getEmail().equals("") || userRegistration.getPassword().equals(""))
            {
                throw new InvalidInputException(userRegistration);
            }

            // validate login
            Authentication token = service.authenticateUser(userRegistration.getEmail(), userRegistration.getPassword());

            // validate user exists
            User foundUser = userService.getUser(token.getUserInfo().getId());

            return ResponseEntity.status(HttpStatus.OK).body(token.getToken());
        }
        catch (InvalidInputException e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
