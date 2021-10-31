package org.example.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.DTOs.ProjectResponseDto;
import org.example.DTOs.TaskResponseDto;
import org.example.services.ProjectService;
import org.example.services.TaskService;
import org.example.services.TaskVersionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name="")
@RestController()
public class UserController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final TaskVersionService taskVersionService;

    public UserController(TaskService taskService, ProjectService projectService,TaskVersionService taskVersionService) {

        this.projectService = projectService;
        this.taskVersionService = taskVersionService;
        this.taskService = taskService;
    }

      @GetMapping("/projects")
      public ResponseEntity<Object> viewAllProjects(){
          return new ResponseEntity<>(projectService.getAll().stream().map(ProjectResponseDto::new), HttpStatus.OK);
      }

      @GetMapping("/projects/{project_id}")
      public ResponseEntity<Object> viewProject(@PathVariable Long project_id){
        return new ResponseEntity<>(new ProjectResponseDto(projectService.findById(project_id)), HttpStatus.OK);
      }

      @GetMapping("/projects/{project_id}/tasks")
        public ResponseEntity<Object> viewProjectTask(@PathVariable Long project_id){
        return new ResponseEntity<>(taskService.getAllByProjectId(project_id).stream().map(TaskResponseDto::new), HttpStatus.OK);
      }
//    @PostMapping("/versions/{id}")
//    public ResponseEntity<Object> postTask(@PathVariable Long id, @RequestBody TaskRequestDto taskRequestDto){
//
//        taskService.save(taskRequestDto.convertToTaskEntity());
//
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping()
//    public ResponseEntity<Object> postTask(@RequestBody TaskRequestDto taskRequestDto){
//
//        taskService.save(taskRequestDto.convertToTaskEntity());
//
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping()
//    public ResponseEntity<Object> getAllTasks(){
//        return new ResponseEntity<>(taskService.getAll().stream().map(TaskResponseDto::new), HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Object> getTask(@PathVariable Long id){
//
//        return new ResponseEntity<>(new TaskResponseDto(taskService.findById(id)), HttpStatus.OK);
//    }
//
//    @PutMapping()
//    public ResponseEntity<Object> putTask(@RequestBody TaskRequestDto taskRequestDto){
//
//        taskService.save(taskRequestDto.convertToTaskEntity());
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> deleteTask(@PathVariable Long id){
//        taskService.delete(id);
//        return ResponseEntity.ok().build();
//    }
}
