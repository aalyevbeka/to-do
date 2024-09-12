package com.example.todo.controller;

import com.example.todo.dto.ResponseDto;
import com.example.todo.entity.Task;
import com.example.todo.exceptions.CustomException;
import com.example.todo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("api/to-do/")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Получить все задачи", description = "Получить всех задач не зависимо от статуса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задачи найдены", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Задачи не найдены", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    @GetMapping("get-all")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Найти задачу по ID", description = "Возвращает задачу с указанным ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача найдена", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    @GetMapping("get")
    public ResponseEntity<?> findTask(
            @Parameter(description = "ID задачи", required = true)
            @RequestParam long id){
        return new ResponseEntity<>(taskService.findById(id), HttpStatus.OK);
    }



    @Operation(summary = "Создать новую задачу", description = "Создание задач")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Задача создана (HttpStatus)", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Задача не создана", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    @PostMapping("add")
    public ResponseEntity<?> addTask(
            @Parameter(description = "Тело задачи", required = true)
            @Valid @RequestBody Task task){
        return new ResponseEntity<>(taskService.save(task), HttpStatus.CREATED);
    }



    @Operation(summary = "Обновить задачу", description = "Обновление задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача обновлена", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Задача не обновлена", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    @PutMapping("update")
    public ResponseEntity<?> updateTask(
            @Parameter(description = "Тело задачи которую хотим обновить", required = true)
            @Valid @RequestBody Task task){
        return new ResponseEntity<>(taskService.update(task), HttpStatus.OK);
    }



    @Operation(summary = "Удалить задачу", description = "Удаление задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача удалена", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteTask(
            @Parameter(description = "ID задачи", required = true)
            @RequestParam long id){
        return new ResponseEntity<>(taskService.delete(id), HttpStatus.OK);
    }
}
