package com.example.todo.dto;

import com.example.todo.entity.Task;
import com.example.todo.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {

    private int status;
    private String message;
    private List<Task> tasks;

    public ResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseDto(ResponseStatus responseStatus, List<Task> tasks) {
        this.status = responseStatus.getCode();
        this.message = responseStatus.getMessage();
        this.tasks = tasks;
    }
}
