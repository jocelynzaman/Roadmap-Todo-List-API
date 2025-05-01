package com.example.todo_list_api.User;

public class UserRegistrationRequest {
    private String name;
    private String email;
    private String password;

    public UserRegistrationRequest()
    {
        this.name = "";
    }

    public UserRegistrationRequest(String name, String email, String password)
    {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // getters
    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    @Override
    public String toString()
    {
        return String.format("User[name='%s', email='%s', password='%s']", name, email, password);
    }
}
