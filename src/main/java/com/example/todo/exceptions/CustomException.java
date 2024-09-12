package com.example.todo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{

    private int status;
    private String message;

    public CustomException(HttpStatus status, String message){
        this.status = status.value();
        this.message = message;
    }
}
