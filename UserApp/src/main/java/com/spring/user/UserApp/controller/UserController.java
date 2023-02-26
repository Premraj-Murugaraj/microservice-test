package com.spring.user.UserApp.controller;

import com.spring.user.UserApp.exception.UserNotFoundException;
import com.spring.user.UserApp.model.User;
import com.spring.user.UserApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/users", produces = "application/json")
    public ResponseEntity<List<User>> getAllUser(){

        return new ResponseEntity<List<User>>(userService.retriveAllUsers(), HttpStatus.OK);
    }

    //get specific user
    @GetMapping(path = "/users/{userId}", produces = "application/json")
    public ResponseEntity<User> getSpecificUser(@PathVariable int userId) throws UserNotFoundException {
        User user = userService.getAUser(userId);
        if (user == null)
            throw new UserNotFoundException("user with given id "+userId+" not exist !!!");
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @PostMapping(path = "/create/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user){
        if (user==null){
            return ResponseEntity.internalServerError().body(user);
        }
        User newUser = userService.addUser(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUser.id())
                .toUri();
        return ResponseEntity.created(uri).build();
        //return newUser == null ? ResponseEntity.internalServerError().body(newUser) : new ResponseEntity<User>(newUser, HttpStatus.CREATED)
    }

}
