package org.example.controllers;

import org.example.DTOs.TaskRequestDto;
import org.example.entities.TaskVersionEntity;
import org.example.repositories.TaskRepository;
import org.example.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final TaskService taskService;

    public AdminController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/projects/{id}/task")
    public ResponseEntity<Object> createTaskInProject(@PathVariable Long id, @RequestBody TaskRequestDto taskRequestDto){


        taskRequestDto.setProjectId(id);
        taskRequestDto.setVersion(List.of(new TaskVersionEntity("1.0", Calendar.getInstance())));




        taskService.save(taskRequestDto.convertToTaskEntity());

    return ResponseEntity.ok().build();
}

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Object> deleteTaskById(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
