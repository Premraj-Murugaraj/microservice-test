package com.spring.user.UserApp.controller;

import com.spring.user.UserApp.exception.CarNotFoundException;
import com.spring.user.UserApp.model.Car;
import com.spring.user.UserApp.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping(path = "/cars")
    public List<Car> getAllCar(){
        return carService.retrieveAllCars();
    }

    @GetMapping(path = "/car")
    public Car getCar() throws CarNotFoundException {
        Car car = carService.getACar();
        if (car == null)
            throw  new CarNotFoundException("car list is empty !!!");
        return car;
    }
}
