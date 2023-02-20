package com.spring.user.UserApp.model;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record User(int id, String name, LocalDate dob) {
}
