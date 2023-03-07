package com.spring.user.UserApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {

    private int code;
    private List<String> message;
    private String details;
    private LocalDateTime timeStamp;
}
