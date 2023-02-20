package com.spring.user.UserApp.controller;

import com.spring.user.UserApp.model.Phone;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class PhoneController {
    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String sayHello(){
        return "hello, Welcome to the session !!!";
    }

    @RequestMapping(path = "/phones", method = RequestMethod.GET)
    public List<Phone> getPhones(){
        Phone iPhone = new Phone("X","iPhone X", "10");
        Phone android = new Phone("one Plus","android 21", "10");

        return Arrays.asList(iPhone,android);
    }
}
