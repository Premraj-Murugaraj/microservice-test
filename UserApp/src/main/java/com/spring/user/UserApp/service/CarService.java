package com.spring.user.UserApp.service;

import com.spring.user.UserApp.exception.CarNotFoundException;
import com.spring.user.UserApp.model.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CarService {
    public static List<Car> carLst = new ArrayList<>();
    private static int id=0;
    static {
        carLst.add(Car.builder().id(++id).price(25000000).mfgYear(2022).modelType("MPV").model("Chrysta").build());
        carLst.add(Car.builder().id(++id).price(25000000).mfgYear(2022).modelType("Zidane").model("Corolla Altis").build());
        carLst.add(Car.builder().id(++id).price(25000000).mfgYear(2022).modelType("SUV").model("Fortuner").build());
    }

    public List<Car> retrieveAllCars(){
        return carLst;
    }

    public Car getACar(){
        return carLst.stream()
                .skip(new Random().nextInt(carLst.size()))
                .findFirst()
                .orElse(null);
    }
}
