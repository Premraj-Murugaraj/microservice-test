package com.spring.car.controller;

import com.spring.car.model.Car;
import com.spring.car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping(path = "/cars",produces = "application/json")
    public ResponseEntity<List<Car>> getAllUser(){
        return new ResponseEntity<List<Car>>(carService.retrieveAllCars(), HttpStatus.OK);
    }

    @GetMapping(path ="/car/{id}")
    public ResponseEntity<Car> getCar(@PathVariable Integer id){
        Car car = carService.getCar(id);
        return ResponseEntity.ok(car);
    }

    @GetMapping(path = "/aCar/{id}", produces = "application/json")
    public ResponseEntity<Car> getACar(@PathVariable Integer  id){
        Car car = carService.getACar(id);
        return new ResponseEntity<Car>(car,HttpStatus.OK);
    }

    @PostMapping(path = "/create/car", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Car> createUser(@RequestBody Car car){
        if (car==null){
            return ResponseEntity.internalServerError().body(car);
        }
        Car newUser = carService.addCar(car);

        return newUser == null ? ResponseEntity.internalServerError().body(newUser) : new ResponseEntity<Car>(newUser, HttpStatus.CREATED);
    }
}
