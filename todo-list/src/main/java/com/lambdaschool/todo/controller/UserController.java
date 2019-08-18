package com.lambdaschool.todo.controller;

import com.lambdaschool.todo.model.Todo;
import com.lambdaschool.todo.model.User;
import com.lambdaschool.todo.service.TodoService;
import com.lambdaschool.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController

{

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    //http://localhost:2019/h2-console

//    //localhost:2019/users/getusername
//    @GetMapping(value = "/getusername",
//                produces = {"application/json"})
//    @ResponseBody
//    public ResponseEntity<?> getCurrentUserName(Authentication authentication)
//    {
//        return new ResponseEntity<>(userService.findUserByName(authentication.getName()), HttpStatus.OK);
//        // return new ResponseEntity<>(userService.findUserByName(authentication.getName()).getUserid(), HttpStatus.OK);
//    }

    // GET localhost:2019/users/mine
    //GET /users/mine - return the user and todo based off of the authenticated user.
    // You can only look up your own.
    // It is okay if this also lists the users roles and authorities.
    @GetMapping(value = "/mine",
                produces = {"application/json"})
    public ResponseEntity<?> getUsersMine()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User myUser = userService.findUserByName(authentication.getName());
        return new ResponseEntity<>(myUser, HttpStatus.OK);
    }


//    @GetMapping(value = "/users",
//                produces = {"application/json"})
//    public ResponseEntity<?> listAllUsers()
//    {
//        List<User> myUsers = userService.findAll();
//        return new ResponseEntity<>(myUsers, HttpStatus.OK);
//    }


//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @GetMapping(value = "/user/{userId}",
//                produces = {"application/json"})
//    public ResponseEntity<?> getUser(
//            @PathVariable
//                    Long userId)
//    {
//        User u = userService.findUserById(userId);
//        return new ResponseEntity<>(u, HttpStatus.OK);
//    }

    // POST localhost:2019/users/todo/{userid}

    //POST /users/todo/{userid}
    // - adds a todo to the assigned user.
    //  Can be done by any user. You can add this todo
    @PostMapping(value = "/users/todo/{userId}",
                 produces = {"application/json"})
    public ResponseEntity<?> addNewTodo (
            @RequestBody Todo newTodo,
            @PathVariable
            Long userId)
    {
        newTodo.setUser(userService.findUserById(userId));
        newTodo = todoService.save(newTodo);
        return new ResponseEntity<>(newTodo, HttpStatus.OK);
    }

    //localhost:2019/users/userid/{userid}


    //DELETE /users/userid/{userid}
    // - Deletes a user based off of their userid and deletes all their associated todos.
    // Can only be done by an admin.
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/users/userid/{userid}",
                   produces = {"application/json"})
    public ResponseEntity<?> deleteUserById (@PathVariable long userid)
    {
        userService.delete(userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //POST localhost:2019/users/users

    //POST /users - adds a user. Can only be done by an admin.
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/users",
                 produces = {"application/json"},
                 consumes = {"application/json"})
    public ResponseEntity<?> addNewUser(@Valid
                                        @RequestBody
                                                User newuser) throws URISyntaxException
    {
        newuser = userService.save(newuser);


        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userid}").buildAndExpand(newuser.getUserid()).toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
}


