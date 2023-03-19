package com.spring.car.unit;

import com.spring.car.controller.CarController;
import com.spring.car.model.Car;
import com.spring.car.service.CarService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyInt;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CarService carService;

    @Test
    @SneakyThrows
    public void should_return_a_car(){
        Car car = Car.builder().build();
        Mockito.when(carService.getCar(anyInt())).thenReturn(car);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/car/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("modelName").value("Toyota_Chrysta"))
                .andDo(MockMvcResultHandlers.print());
    }
}
