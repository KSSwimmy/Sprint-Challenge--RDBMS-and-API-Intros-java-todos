package com.lambdaschool.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todos")
public class Todo extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long todoid;

    @Column(nullable = false)
    private String todo;

    private Date datestarted;

    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid",
                nullable = false)
    @JsonIgnoreProperties({"todo", "hibernateLazyInitializer"})
    private User user;

    public Todo()
    {
    }

    public Todo(String todo, Date datestarted, User user)
    {
        this.todo = todo;
        this.datestarted = datestarted;
        this.user = user;
    }

    public Todo(String todo, User user)
    {
        this.todo = todo;
        this.user = user;
    }


    public long getTodoid()
    {
        return todoid;
    }

    public void setTodoid(long todoid)
    {
        this.todoid = todoid;
    }

    public String getTodo()
    {
        return todo;
    }

    public void setTodo(String todo)
    {
        this.todo = todo;
    }

    public Date getDatestarted()
    {
        return datestarted;
    }

    public void setDatestarted(Date datestarted)
    {
        this.datestarted = datestarted;
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public void setCompleted(boolean completed)
    {
        this.completed = completed;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}