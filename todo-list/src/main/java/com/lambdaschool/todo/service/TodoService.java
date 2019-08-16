package com.lambdaschool.todo.service;

import com.lambdaschool.todo.model.Todo;
//import com.lambdaschool.todo.view.CountTodos;
import com.lambdaschool.todo.view.CountTodos;

import java.util.ArrayList;
import java.util.List;

public interface TodoService
{
    List<Todo> findAll();

    Todo findQuoteById(long id);

    List<Todo> findByUserName(String username);

    void delete(long id);

    Todo save(Todo todo);

    Todo update(Todo todo, long id);

    ArrayList<CountTodos> getCountTodos();
}