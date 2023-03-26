package com.spring.car.contract;

import com.spring.car.controller.CarController;
import com.spring.car.model.Car;
import com.spring.car.service.CarService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ContractBaseTest {

    @Autowired
    CarController carController;

    @MockBean
    CarService carService;

    @BeforeEach
    void setUp(){

        List<Car> car = Arrays.asList(
                Car.builder().id(123).modelName("Toyota_Chrysta").price("2300000").manufactureYear(LocalDate.now().minusYears(2)).build()
        );

        RestAssuredMockMvc.standaloneSetup(carController);
        Mockito.when(carService.retrieveAllCars()).thenReturn(car);
    }
}
