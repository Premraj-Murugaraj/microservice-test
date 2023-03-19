package com.spring.car.wiremock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.spring.car.model.Car;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.Base64;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest(httpPort = 8083)
public class APIMockTest {

    @Test
    void create_stub_get_car(){
        WireMock.stubFor(
                WireMock.get("/api/wmock/car")
                        .withHeader("Content-Type",equalTo("application/json"))

                        .willReturn(aResponse()
                                .withStatus(200)
                                .withStatusMessage("ok")
                                .withBody("new car launched in Toyota..."))
        );

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","application/json");
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> exchange = testRestTemplate.exchange("http://localhost:8083/api/wmock/car",
                HttpMethod.GET,httpEntity,String.class);

        Assertions.assertEquals("new car launched in Toyota...",exchange.getBody());
    }

    @SneakyThrows
    @Test
    public void create_stub_create_car(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Car car = Car.builder().id(1).modelName("Lexus").price("3500000").manufactureYear(LocalDate.now().minusYears(1)).build();

        WireMock.stubFor(post("/api/create/car")
                .withHeader("Content-Type",equalTo("application/json"))
                .withBasicAuth("admin","root")
                .withRequestBody(matchingJsonPath(("$.modelName"), equalTo("Lexus")))

                .willReturn(aResponse().withStatus(201)
                        .withStatus(HttpStatus.CREATED.value())
                        .withBodyFile("car.json"))
        );

        String credentials= "admin:root";
        Base64.Encoder encoder = Base64.getEncoder();
        String encodeToString = encoder.encodeToString(credentials.getBytes());

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","application/json");
        httpHeaders.add("Authorization","Basic "+encodeToString);

        HttpEntity<Object> httpEntity = new HttpEntity<>(car,httpHeaders);

        ResponseEntity<String> exchange = testRestTemplate.exchange("http://localhost:8083/api/create/car",
                HttpMethod.POST,httpEntity,String.class);
        Car newCar = objectMapper.readValue(exchange.getBody(), Car.class);
        Assertions.assertEquals("Lexus",newCar.getModelName());
    }
}
