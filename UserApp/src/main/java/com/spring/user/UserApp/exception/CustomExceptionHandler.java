package com.spring.user.UserApp.exception;

import com.spring.user.UserApp.model.Error;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode code, WebRequest webReq){

        Error errorResp = Error.builder().code(code.value()).timeStamp(LocalDateTime.now())
                //.message(ex.getMessage())
                //.message(ex.getFieldErrors().stream().findFirst().get().getDefaultMessage())
                .message(ex.getFieldErrors().stream().map(err ->err.getDefaultMessage()).collect(Collectors.toList()))
                .details(webReq.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResp, HttpStatus.BAD_REQUEST);
    }
}
