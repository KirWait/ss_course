package org.example.controllers;

import org.example.DTOs.TaskRequestDto;
import org.example.entities.TaskVersionEntity;

import org.example.entities.enums.Status;
import org.example.services.ProjectService;
import org.example.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final TaskService taskService;
    private final ProjectService projectService;

    public AdminController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @PostMapping("/projects/{id}/task")
    public ResponseEntity<Object> createTaskInProject(@PathVariable Long id, @RequestBody TaskRequestDto taskRequestDto){

        taskRequestDto.setStatus(Status.BACKLOG.name());
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

    @PostMapping("/projects/{id}/change")
    public ResponseEntity<Object> changeProjectStatus(@PathVariable Long id){
        try {
            projectService.changeStatus(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad Request: [ The project has already been done! ]");
        }
        return ResponseEntity.ok().build();
    }
}
