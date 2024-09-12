package com.example.todo.service;

import com.example.todo.dto.ResponseDto;
import com.example.todo.entity.Task;

public interface TaskService {

    ResponseDto findAll();
    ResponseDto findById(long id);
    ResponseDto save(Task task);
    ResponseDto update(Task task);
    ResponseDto delete(long id);
}
