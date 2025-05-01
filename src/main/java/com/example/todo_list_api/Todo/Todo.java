package com.example.todo_list_api.Todo;

import com.example.todo_list_api.User.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "todo")
public class Todo {
    @Id //primary key
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "todo_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userInfo;

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

    // getters
    public Long getId()
    {
        return this.id;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getDescription()
    {
        return this.description;
    }

    public User getUserInfo()
    {
        return this.userInfo;
    }

    // setters
    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setUserInfo(User userInfo)
    {
        this.userInfo = userInfo;
    }

    @Override
    public String toString()
    {
        return String.format("User[id=%d, title='%s', description='%s']", id, title, description);
    }
}
