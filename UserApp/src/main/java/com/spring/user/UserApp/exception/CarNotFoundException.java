package com.spring.user.UserApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CarNotFoundException extends Exception {
    public CarNotFoundException(String message){
        super(message);
    }
}
