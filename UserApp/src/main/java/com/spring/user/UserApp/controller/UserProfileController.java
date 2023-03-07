package com.spring.user.UserApp.controller;

import com.spring.user.UserApp.model.Users;
import com.spring.user.UserApp.model.profile.DataItem;
import com.spring.user.UserApp.model.profile.UserProfile;
import com.spring.user.UserApp.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @GetMapping(path = "/user/profile", produces = "application/json")
    public ResponseEntity<UserProfile> getAllUser(){
        return new ResponseEntity<>(userProfileService.retriveUserProfile(), HttpStatus.OK);
    }

    @PatchMapping(path = "/update/user/profile/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DataItem> patchUser(@RequestBody DataItem user, @PathVariable int id){

        DataItem returnedUser = userProfileService.getUser(id);
        if (returnedUser == null){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(userProfileService.updateUser(returnedUser,user),HttpStatus.OK);
    }
}
