package com.example.todo_list_api.Registration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    private RegistrationService service;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserRegistration userRegistration) {
        // store user registration
        try
        {
            // valid inputs aren't empty
            if (userRegistration == null || userRegistration.getName() == "" || userRegistration.getEmail() ==  "" || userRegistration.getPassword() == "")
            {
                throw new InvalidInputException(userRegistration);
            }
            String token = service.registerUser(userRegistration).getToken();
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
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
