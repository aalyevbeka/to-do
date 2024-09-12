package com.example.todo.service.impl;

import com.example.todo.dto.ResponseDto;
import com.example.todo.entity.Task;
import com.example.todo.exceptions.CustomException;
import com.example.todo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    @BeforeEach
    public void setup() {
        task = new Task(1L, "Test Task", "This is a test task description", false);
    }

    @Test
    void findAll() {
        List<Task> tasks = List.of(
                new Task(1L, "test", "djfsjdfgj", false),
                new Task(2L, "test1", "djfsjdfgj", false),
                new Task(3L, "test2", "djfsjdfgj", true),
                new Task(4L, "test3", "djfsjdfgj", false),
                new Task(5L, "test4", "djfsjdfgj", true),
                new Task(6L, "test5", "djfsjdfgj", false));

        Mockito.when(taskRepository.findAll()).thenReturn(tasks);
        ResponseDto responseDto = taskService.findAll();
        assertNotNull(responseDto);
        assertEquals(6, responseDto.getTasks().size());
    }

    @Test
    void findById() {
         Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
         ResponseDto responseDto = taskService.findById(task.getId());
         assertNotNull(responseDto);
         assertEquals(1, responseDto.getTasks().size());
         assertEquals("test", responseDto.getTasks().get(0).getTitle());
    }

    @Test
    void findById_whenTaskNotFound() {
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> taskService.findById(task.getId()));
        assertNotNull(exception);
        assertEquals(404, exception.getStatus());
    }

    @Test
    void save() {
        Mockito.when(taskRepository.save(task)).thenReturn(task);
        ResponseDto responseDto = taskService.save(task);
        assertNotNull(responseDto);
        assertEquals(200, responseDto.getStatus());
    }

    @Test
    void update() {
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(task)).thenReturn(task);
        ResponseDto responseDto = taskService.update(task);
        assertNotNull(responseDto);
        assertEquals(200, responseDto.getStatus());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void update_whenTaskNotFound() {
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> taskService.update(task));
        assertNotNull(exception);

        String message = String.format("Task with id %s not found for update", task.getId());
        assertEquals(message, exception.getMessage());

        assertEquals(404, exception.getStatus());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void delete() {

        ResponseDto response = taskService.delete(task.getId());

        // Проверяем результат
        assertNotNull(response);
        assertEquals("Task successfully deleted", response.getMessage());  // Предполагаем, что это ваше сообщение
        assertEquals(200, response.getStatus());  // Убедитесь, что код успешного удаления соответствует

        // Проверяем, что taskRepository.deleteById был вызван 1 раз с правильным ID
        verify(taskRepository, times(1)).deleteById(task.getId());
    }
}