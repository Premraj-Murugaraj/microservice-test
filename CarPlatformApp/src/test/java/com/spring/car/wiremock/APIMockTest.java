package com.spring.car.wiremock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.spring.car.model.Car;
import com.spring.car.model.proxy.UsersResponse;
import io.restassured.RestAssured;
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
    void create_stub_create_car(){
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

    @Test
    void stub_delay_simulation(){
        stubFor(get("/api/cars")
                .withHeader("x-auth-user",containing("admin"))

                .willReturn(aResponse().withStatus(200)
//                        .withFixedDelay(5000)
//                        .withUniformRandomDelay(2000,5000)
                        .withChunkedDribbleDelay(5,5000)
                .withBodyFile("Car.json")));

        String response =
                RestAssured.given()
                        .baseUri("http://localhost:8083")
                        .when()
                        .header("x-auth-user","admin")
                        .get("/api/cars")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .asString();

    }

    @Test
    void stub_fault_simulation_400(){
        stubFor(post("/api/cars")
                .willReturn(badRequest().withStatus(400)
                        .withStatusMessage("Bad Request...!")));
        RestAssured.given().log().all().baseUri("http://localhost:8083")
                .when()
                .post("/api/cars")
                .then()
                .statusCode(400);
    }

    @Test
    void stub_fault_simulation_503(){
        stubFor(post("/api/cars")
                .willReturn(serviceUnavailable().withStatus(503)
                        .withStatusMessage("Service unavailable...!")));
        RestAssured.given().log().all().baseUri("http://localhost:8083")
                .when()
                .post("/api/cars")
                .then()
                .statusCode(503);
    }

    @Test
    void stub_proxy(){
        stubFor(get("/api/users")
                .willReturn(aResponse().withStatus(200)
                        .proxiedFrom("https://reqres.in")));

        UsersResponse usersResponse = RestAssured.given().log().all()
                .baseUri("http://localhost:8083")
                .get("/api/users")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(UsersResponse.class);

        Assertions.assertEquals(1,usersResponse.getPage());
    }

    @Test
    void template_transformation_test(){
        stubFor(get("/api/employee")
                .willReturn(aResponse().withStatus(200)
                        .withBodyFile("employee.json")
                        .withTransformers("response-template")));

        String response =
                RestAssured.given().baseUri("http://localhost:8083")
                        .get("/api/employee")
                        .then()
                        .statusCode(200)
                        .extract().response()
                        .asString();

        System.out.println(response);
    }



}
