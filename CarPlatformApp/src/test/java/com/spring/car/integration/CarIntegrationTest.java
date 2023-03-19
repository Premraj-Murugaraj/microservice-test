package com.spring.car.integration;

import com.spring.car.model.Car;
import com.spring.car.repository.CarJPARepository;
import com.spring.car.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    CarJPARepository carJPARepository;

    @Autowired
    CarService carService;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    @Sql(statements = "insert into car_details (ID,MANUFACTURE_YEAR,MODEL_NAME,PRICE) values(1,'2022-07-08','chrysta','2500000');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from car_details where MODEL_NAME='chrysta';",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_return_all_cars(){
        ResponseEntity<List<Car>> exchange = testRestTemplate.exchange("http://localhost:" + port + "/api/cars",
                HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Car>>() {
                });

        //Assert
        Assertions.assertEquals(1,exchange.getBody().size());
        Assertions.assertEquals(1,carJPARepository.findAll().size());
        Assertions.assertEquals("chrysta",carJPARepository.findByModelName("chrysta").getModelName());
        Assertions.assertTrue(exchange.getBody().stream().anyMatch(car -> car.getModelName().equals("chrysta")));
    }

    @Test
    @Sql(statements = "insert into car_details (ID,MANUFACTURE_YEAR,MODEL_NAME,PRICE) values(1,'2022-07-08','chrysta','2500000');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from car_details where MODEL_NAME='chrysta';",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_get_specific_car(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","application/json");
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Car> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/api/aCar/{id}",
                HttpMethod.GET,httpEntity,Car.class,1);

        //Assertions
        Assertions.assertEquals(200,responseEntity.getStatusCode().value());
        Assertions.assertEquals("chrysta",responseEntity.getBody().getModelName());
    }

    @Test
    @Sql(statements = "delete from car_details where MODEL_NAME='chrysta';",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_save_a_car(){

        Car car = Car.builder().modelName("chrysta").price("3500000").manufactureYear(LocalDate.now().minusYears(3)).build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","application/json");
        HttpEntity<Object> httpEntity = new HttpEntity<>(car,httpHeaders);
        ResponseEntity<Car> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/api/create/car",
                HttpMethod.POST,httpEntity,Car.class);

        //Assertions
        Assertions.assertEquals(201,responseEntity.getStatusCode().value());
        Assertions.assertEquals("chrysta",responseEntity.getBody().getModelName());
    }
}
