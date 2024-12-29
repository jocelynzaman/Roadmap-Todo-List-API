package com.example.todo_list_api.Registration;

import org.apache.commons.text.RandomStringGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    public Token()
    {
        this.token = generateToken();
    }

    public Token(Long userId)
    {
        this.userId = userId;
        this.token = generateToken();
    }

    // getters
    public Long getId()
    {
        return userId;
    }

    public String getToken()
    {
        return token;
    }

    // generate new token
    public void updateToken()
    {
        this.token = generateToken();
    }

    // generate alphanumeric characters for token
    private String generateToken() {
        char [][] pairs = {{'a','z'}, {'0','9'}, {'A','Z'}}; //characters to allow in token
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).build();
        String token = generator.generate(25);
        return token;
    }
}
