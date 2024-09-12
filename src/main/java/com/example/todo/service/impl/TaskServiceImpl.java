package com.example.todo.service.impl;

import com.example.todo.dto.ResponseDto;
import com.example.todo.entity.Task;
import com.example.todo.exceptions.CustomException;
import com.example.todo.repository.TaskRepository;
import com.example.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.example.todo.enums.ResponseStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public ResponseDto findAll() {
        List<Task> taskList = taskRepository.findAll();
        if (taskList.isEmpty()) {
            String message = "No tasks found";
            throw new CustomException(NOT_FOUND, message);
        }
        return new ResponseDto(OK, taskList);
    }

    @Override
    public ResponseDto findById(long id) {
        String message = String.format("Task with id %s not found", id);
        Task task = taskRepository.findById(id).orElseThrow(() -> new CustomException(NOT_FOUND, message));
        return new ResponseDto(OK, Collections.singletonList(task));
    }

    @Override
    public ResponseDto save(Task task) {
        taskRepository.save(task);
        return new ResponseDto(SAVED.getCode(), SAVED.getMessage());
    }

    @Override
    public ResponseDto update(Task task) {
        String message = String.format("Task with id %s not found for update", task.getId());
        taskRepository.findById(task.getId()).orElseThrow(() -> new CustomException(NOT_FOUND, message));
        taskRepository.save(task);
        return new ResponseDto(UPDATED.getCode(), UPDATED.getMessage());
    }

    @Override
    public ResponseDto delete(long id) {
        taskRepository.deleteById(id);
        return new ResponseDto(DELETED.getCode(), DELETED.getMessage());
    }
}
