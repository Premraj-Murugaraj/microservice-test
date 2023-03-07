package com.spring.user.UserApp.service;

import com.spring.user.UserApp.exception.UserNotFoundException;
import com.spring.user.UserApp.model.Users;
import com.spring.user.UserApp.model.profile.DataItem;
import com.spring.user.UserApp.model.profile.Support;
import com.spring.user.UserApp.model.profile.UserProfile;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserProfileService {

    public static UserProfile usrProfile;
    static List<DataItem> dataItemList = new ArrayList<>();

    public static int id=0;
    static {
        dataItemList.add(DataItem.builder().id(++id).firstName("Mersal").lastName("Kuruvi").email("mersal@yopmail.com").avatar("https://reqres.in/img/faces/7-image.jpg").build());
        dataItemList.add(DataItem.builder().id(++id).firstName("Jilla").lastName("Killa").email("jilla@yopmail.com").avatar("https://reqres.in/img/faces/8-image.jpg").build());
        usrProfile = UserProfile.builder().page(1).perPage(1).totalPages(1).total(1).data(dataItemList).support(Support.builder().text("To keep ReqRes free, contributions towards server costs are appreciated!").url("https://reqres.in/#support-heading").build()).build();
    }

    public UserProfile retriveUserProfile() {
        return usrProfile;
    }

    @SneakyThrows
    public DataItem getUser(int id){
        return usrProfile.getData().stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("user not found for the id "+id+" !!!"));
    }


    public DataItem updateUser(DataItem returnedUser,DataItem user){
        returnedUser.setAvatar(user.getAvatar());
        returnedUser.setEmail(user.getEmail());
        returnedUser.setFirstName(user.getFirstName());
        returnedUser.setLastName(user.getLastName());
        return returnedUser;
    }
}
