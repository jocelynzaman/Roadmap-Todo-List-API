package com.example.todo_list_api.Todo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "todo_items")
public class Todo {
    @Id //primary key
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "todo_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // private UserRegistration user;

    public Todo()
    {
        this.title = "";
        this.description = "";
    }

    public Todo(String title)
    {
        this.title = title;
        this.description = "";
    }

    public Todo(String title, String description)
    {
        this.title = title;
        this.description = description;
    }

    public Todo(Long id, String title, String description)
    {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // getters
    public Long getId()
    {
        return this.id;
    }

    public Long getUserId()
    {
        return this.user_id;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getDescription()
    {
        return this.description;
    }

    // setters
    public void setUserId(Long userId)
    {
        this.user_id = userId;
    }
    // public void setUser(UserRegistration user)
    // {
    //     this.user = user;
    // }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return String.format("User[id=%d, title='%s', description='%s']", id, title, description);
    }
}
