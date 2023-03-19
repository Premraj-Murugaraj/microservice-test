package com.spring.car.repository;

import com.spring.car.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarJPARepository extends JpaRepository<Car,Integer> {
    public Car findByModelName(String modelName);
}
