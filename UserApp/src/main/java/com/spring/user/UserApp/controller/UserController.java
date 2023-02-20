package com.spring.user.UserApp.controller;

import com.spring.user.UserApp.exception.UserNotFoundException;
import com.spring.user.UserApp.model.User;
import com.spring.user.UserApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/users")
    public List<User> getAllUser(){
        return userService.retriveAllUsers();
    }

    //get specific user
    @GetMapping("/users/{userId}")
    public User getSpecificUser(@PathVariable int userId) throws UserNotFoundException {
        User user = userService.getAUser(userId);
        if (user == null)
            throw new UserNotFoundException("user with given id "+userId+" not exist !!!");
        return user;
    }

}
