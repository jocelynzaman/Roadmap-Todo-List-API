package com.example.todo_list_api.Registration;

/*
 * validate user input
 */
public class InvalidInputException extends RuntimeException {
    private String message;

    //missing input
    public InvalidInputException(UserRegistration user) 
    {
        super(user + " missing user information");
        this.message = "Registration is missing user information";
    }

    //email format
    public InvalidInputException(String email)
    {
        super(email + " is invalid. Please correct and try again");
        this.message = email + " is invalid. Please correct and try again";
    }

    public InvalidInputException(String type, String input)
    {
        super(type + " failed. Your email or password is incorrect. Please try again.");
        this.message = type + " failed. Your " + input + " is incorrect. Please try again.";
    }

    public String getMessage()
    {
        return this.message;
    }
}
