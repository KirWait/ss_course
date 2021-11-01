package org.example.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.DTOs.ProjectResponseDto;
import org.example.DTOs.TaskResponseDto;
import org.example.DTOs.VersionRequestDto;
import org.example.DTOs.VersionResponseDto;
import org.example.entities.TaskEntity;
import org.example.entities.TaskVersionEntity;
import org.example.services.ProjectService;
import org.example.services.TaskService;
import org.example.services.TaskVersionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;


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

      @GetMapping("/projects/{id}")
      public ResponseEntity<Object> viewProject(@PathVariable Long id){
        return new ResponseEntity<>(new ProjectResponseDto(projectService.findById(id)), HttpStatus.OK);
      }

      @GetMapping("/projects/{id}/tasks")
        public ResponseEntity<Object> viewProjectTask(@PathVariable Long id){
        return new ResponseEntity<>(taskService.getAllByProjectId(id).stream().map(TaskResponseDto::new), HttpStatus.OK);
      }

      @PostMapping("/tasks/{id}/change/status")
        public ResponseEntity<Object> changeTaskStatus(@PathVariable Long id){
            Long projectId = taskService.findById(id).getProjectId();
          try {
              projectService.checkIfProjectInProgress(projectId);
          } catch (Exception e) {
              return ResponseEntity.badRequest().body("Bad Request: [ The project is only in 'BACKLOG' stage! ]");
          }
          try {
              taskService.changeStatus(id);

          } catch (Exception exception) {
              return ResponseEntity.badRequest().body("Bad Request: [ The task has already been done! ]");
          }
          return ResponseEntity.ok().build();
      }

      @PostMapping("/tasks/{id}/change/version")
        public ResponseEntity<Object> changeTaskVersion(@PathVariable Long id, @RequestBody VersionRequestDto version){
        version.setStartTime(Calendar.getInstance());
        TaskEntity task = taskService.findById(id);
        version.setTask(task);
          try {
              taskVersionService.changeVersion(version.convertToTaskVersionEntity(), task);
          } catch (Exception e) {
              return ResponseEntity.badRequest().body("Bad Request: [ Can't change version of the task with 'BACKLOG' status! ]");
          }

          return ResponseEntity.ok().build();
      }

    @GetMapping("/tasks/{id}/versions")
    public ResponseEntity<Object> viewTaskVersion(@PathVariable Long id){
        TaskEntity task = taskService.findById(id);
        taskVersionService.findAllByTaskOrderById(task);

        return new ResponseEntity<>(taskVersionService.findAllByTaskOrderById(task).stream().map(VersionResponseDto::new), HttpStatus.OK);
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
