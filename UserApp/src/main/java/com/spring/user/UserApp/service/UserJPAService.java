package com.spring.user.UserApp.service;

import com.spring.user.UserApp.exception.UserNotFoundException;
import com.spring.user.UserApp.model.Users;
import com.spring.user.UserApp.repository.UserJPARepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserJPAService {

    @Autowired
    UserJPARepository userJPARepository;

    public List<Users> retriveAllUsers() {
        return userJPARepository.findAll();
    }

    public Users getAUser(int id){
        Optional<Users> users = userJPARepository.findById(id);
        if (users.isEmpty())
            return null;
        return users.get();
    }

    public Users addUser(Users user){
        Users newUser = userJPARepository.save(user);
        return newUser;
    }

    public void deleteUser(int id) {
        userJPARepository.deleteById(id);
    }


    @SneakyThrows
    public Users updateUser(Users user) {
        Users newUser = userJPARepository
                .findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("user not found for the id "+user.getId()+" !!!"));
        newUser.setName(user.getName());
        newUser.setDob(user.getDob());
        userJPARepository.save(newUser);
        return newUser;
    }
}
