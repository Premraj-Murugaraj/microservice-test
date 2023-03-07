package com.spring.user.UserApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserAppApplication {

	//http://localhost:8080/swagger-ui.html --- for swagger
	//http://localhost:8080/h2-console --  for h2

	public static void main(String[] args) {
		SpringApplication.run(UserAppApplication.class, args);
	}

}
