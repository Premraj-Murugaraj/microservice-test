package com.spring.user.UserApp.service;

import com.spring.user.UserApp.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    public static  List<User> userLst = new ArrayList<>();
    public static int id=0;
    static {
        userLst.add(User.builder().id(++id).name("Bigilu").dob(LocalDate.now().minusYears(20)).build());
        userLst.add(User.builder().id(++id).name("Velayutham").dob(LocalDate.now().minusYears(10)).build());
        userLst.add(User.builder().id(++id).name("Gilli").dob(LocalDate.now().minusYears(25)).build());
    }
    public List<User> retriveAllUsers() {
        return userLst;
    }

    public User getAUser(int id){
        return userLst.stream()
                .filter(a-> a.id() == id)
                .findFirst()
                .orElse(null);
    }

    public User addUser(User user){
        User newUser = User.builder().id(++id).name(user.name()).dob(user.dob()).build();
        userLst.add(newUser);
        return newUser;
    }
}
