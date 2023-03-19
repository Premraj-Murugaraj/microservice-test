package com.spring.car.service;

import com.spring.car.model.Car;
import com.spring.car.repository.CarJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    CarJPARepository carJPARepository;

    public Car getCar(int id){
        return Car.builder().id(id).price("2500000").build();
    }

    public Car getACar(int id){
        Optional<Car> cars = carJPARepository.findById(id);
        if (cars.isEmpty())
            return null;
        return cars.get();
    }

    public List<Car> retrieveAllCars(){
        return carJPARepository.findAll();
    }

    public Car addCar(Car car){
        return carJPARepository.save(car);
    }
}
