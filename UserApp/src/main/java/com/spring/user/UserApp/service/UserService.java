package com.spring.user.UserApp.service;

import com.spring.user.UserApp.exception.UserNotFoundException;
import com.spring.user.UserApp.model.User;
import com.spring.user.UserApp.model.Users;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    public static  List<Users> usersLst = new ArrayList<>();

    public static int id=0;
    static {
        usersLst.add(Users.builder().id(++id).name("Bigilu").dob(LocalDate.now().minusYears(20)).build());
        usersLst.add(Users.builder().id(++id).name("Velayutham").dob(LocalDate.now().minusYears(10)).build());
        usersLst.add(Users.builder().id(++id).name("Gilli").dob(LocalDate.now().minusYears(25)).build());
    }

    public List<Users> retriveAllUsers() {
        return usersLst;
    }

    public Users getAUser(int id){
        return usersLst.stream()
                .filter(a-> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Users addUser(Users user){
        Users newUser = Users.builder().id(++id).name(user.getName()).dob(user.getDob()).build();
        usersLst.add(newUser);
        return newUser;
    }

    public void deleteUser(int id) {
        usersLst.removeIf(user -> user.getId() == id);
    }


    @SneakyThrows
    public Users updateUser(Users user) {
        Users newUser;

            newUser = usersLst.stream()
                    .filter(lstUser -> lstUser.getId() == user.getId())
                    .findFirst()
                    .orElseThrow(() -> new UserNotFoundException("user not found for the id "+user.getId()+" !!!"));

            newUser.setName(user.getName());
            newUser.setDob(user.getDob());
        return newUser;
    }


    public Users updateUser(Users returnedUser, Users user) {

        returnedUser.setName(user.getName());
        returnedUser.setDob(user.getDob());
        return returnedUser;
    }
}
