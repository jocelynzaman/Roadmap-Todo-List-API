package com.example.todo_list_api.Registration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserRegistration {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    public UserRegistration()
    {
        this.name = "";
        this.email = "";
        this.password = "";
    }

    public UserRegistration(String name, String email, String password)
    {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // getters
    public Long getId()
    {
        return id;
    }

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

    // hash password for security
    public void hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    // match password for login
    public boolean isPasswordValid(String hashPassword)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matchPassword = encoder.matches(this.password, hashPassword);
        return matchPassword;
    }

    @Override
    public String toString()
    {
        return String.format("User[id=%d, name='%s', email='%s']", id, name, email);
    }
}
