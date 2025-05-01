package com.example.todo_list_api.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_table")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;

    public User()
    {
        this.name = "";
    }

    public User(String name)
    {
        this.name = name;
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

    @Override
    public String toString()
    {
        return String.format("User[id=%d, name='%s']", id, name);
    }
}
