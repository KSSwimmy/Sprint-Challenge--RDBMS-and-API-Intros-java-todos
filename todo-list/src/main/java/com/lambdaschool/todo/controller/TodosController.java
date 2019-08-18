package com.lambdaschool.todo.controller;

import com.lambdaschool.todo.model.Todo;
import com.lambdaschool.todo.service.TodoService;
import com.lambdaschool.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodosController
{
    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

//     update todo by todoid
    @PutMapping(value = "/todo/{todoid}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> updateTodo(@RequestBody Todo todo, @PathVariable long todoid)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        todo.setUser(userService.findUserByName(authentication.getName()));
        todoService.update(todo, todoid);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }


}
