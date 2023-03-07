package com.spring.user.UserApp.controller;

import com.spring.user.UserApp.exception.UserNotFoundException;
import com.spring.user.UserApp.model.Users;
import com.spring.user.UserApp.service.UserJPAService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/jpa")
public class UserJPAController {
    @Autowired
    UserJPAService userJPAService;

    @GetMapping(path = "/users", produces = "application/json")
    public ResponseEntity<List<Users>> getAllUser(){

        return new ResponseEntity<List<Users>>(userJPAService.retriveAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{userId}", produces = "application/json")
    public ResponseEntity<Users> getSpecificUser(@Valid @PathVariable Integer userId) throws UserNotFoundException {
        Users user = userJPAService.getAUser(userId);
        return new ResponseEntity<Users>(user,HttpStatus.OK);
    }

    @PostMapping(path = "/create/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user){
        if (user==null){
            return ResponseEntity.internalServerError().body(user);
        }
        Users newUser = userJPAService.addUser(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
        //return newUser == null ? ResponseEntity.internalServerError().body(newUser) : new ResponseEntity<User>(newUser, HttpStatus.CREATED)
    }

    @DeleteMapping(path = "/delete/user/{userId}")
    public ResponseEntity deleteUser(@PathVariable int userId){
        userJPAService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/update/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Users> updateUser(@RequestBody Users user){
        if (user.getDob() == null || user.getName() == null){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(userJPAService.updateUser(user),HttpStatus.OK);
    }
}
