package com.example.todo_list_api.Registration;

/*
 * validate that a unique parameter doesn't exist in the database
 */
public class DuplicateInputException extends RuntimeException {
    private String message;

    public DuplicateInputException(String email)
    {
        super(email + " already has an account");
        this.message = email + " already has an account";
    }

    public String getMessage()
    {
        return this.message;
    }
}
