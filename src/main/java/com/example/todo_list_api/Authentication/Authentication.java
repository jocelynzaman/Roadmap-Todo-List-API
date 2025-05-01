package com.example.todo_list_api.Authentication;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.todo_list_api.User.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "auth")
public class Authentication {
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "auth_id", nullable = false, unique = true)
    private Long authId;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    // @MapsId // primary key copied from User
    @JoinColumn(name = "user_id")
    private User userInfo;

    public Authentication()
    {
        this.token = generateToken();
    }

    public Authentication(String email, String password)
    {
        this.token = generateToken();
        this.email = email;
        this.password = password;
    }

    public Authentication(User userInfo, String email, String password)
    {
        this.userInfo = userInfo;
        this.token = generateToken();
        this.email = email;
        this.password = password;
    }

    // getters
    public Long getId()
    {
        return authId;
    }

    public String getToken()
    {
        return token;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public User getUserInfo()
    {
        return userInfo;
    }

    // setters
    public void setUserInfo(User userInfo)
    {
        this.userInfo = userInfo;
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

    // hash password for security
    public void hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    // match password for login
    public boolean isPasswordValid(String rawPassword)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matchPassword = encoder.matches(rawPassword, this.password);
        return matchPassword;
    }
}
