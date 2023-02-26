package com.spring.user.UserApp.controller;

import com.spring.user.UserApp.exception.UserNotFoundException;
import com.spring.user.UserApp.model.User;
import com.spring.user.UserApp.model.Users;
import com.spring.user.UserApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/users", produces = "application/json")
    public ResponseEntity<List<Users>> getAllUser(){

        return new ResponseEntity<List<Users>>(userService.retriveAllUsers(), HttpStatus.OK);
    }

    //get specific user
    @GetMapping(path = "/users/{userId}", produces = "application/json")
    public ResponseEntity<Users> getSpecificUser(@PathVariable int userId) throws UserNotFoundException {
        Users user = userService.getAUser(userId);
        if (user == null)
            throw new UserNotFoundException("user with given id "+userId+" not exist !!!");
        return new ResponseEntity<Users>(user,HttpStatus.OK);
    }

    @PostMapping(path = "/create/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Users> createUser(@RequestBody Users user){
        if (user==null){
            return ResponseEntity.internalServerError().body(user);
        }
        Users newUser = userService.addUser(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
        //return newUser == null ? ResponseEntity.internalServerError().body(newUser) : new ResponseEntity<User>(newUser, HttpStatus.CREATED)
    }

    @DeleteMapping(path = "/delete/user/{userId}")
    public ResponseEntity deleteUser(@PathVariable int userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/update/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Users> updateUser(@RequestBody Users user){
        if (user.getDob() == null || user.getName() == null){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(userService.updateUser(user),HttpStatus.OK);
    }

    @PatchMapping(path = "/update/user/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Users> patchUser(@RequestBody Users user,@PathVariable int id){

        Users returnedUser = userService.getAUser(id);
        if (returnedUser.getDob() == null || returnedUser.getName() == null){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(userService.updateUser(returnedUser,user),HttpStatus.OK);
    }
    @Autowired
    MessageSource messageSource;

    @GetMapping(path = "custom/message")
    public String sayHello(){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("welcome.message", new Object[]{"prem","raj"},
                "default welcome message",locale);
    }



}
