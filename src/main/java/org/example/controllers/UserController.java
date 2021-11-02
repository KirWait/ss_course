package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
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
import java.util.List;



@RestController
@Tag(name = "user-controller", description = "The ROLE_USER API")
@RequestMapping("/api")
public class UserController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final TaskVersionService taskVersionService;

    public UserController(TaskService taskService, ProjectService projectService,TaskVersionService taskVersionService) {

        this.projectService = projectService;
        this.taskVersionService = taskVersionService;
        this.taskService = taskService;
    }


      @Operation(summary = "Gets all projects")
      @GetMapping("/projects")
      public ResponseEntity<Object> viewAllProjects(){
          return new ResponseEntity<>(projectService.getAll().stream().map(ProjectResponseDto::new), HttpStatus.OK);
      }

      @Operation(summary = "Gets project by id")
      @GetMapping("/projects/{id}")
      public ResponseEntity<Object> viewProject(@PathVariable Long id){
        return new ResponseEntity<>(new ProjectResponseDto(projectService.findById(id)), HttpStatus.OK);
      }

      @Operation(summary = "Gets tasks by project id")
      @GetMapping("/projects/{id}/tasks")
        public ResponseEntity<Object> viewProjectTasks(@PathVariable Long id){
        return new ResponseEntity<>(taskService.getAllByProjectId(id).stream().map(TaskResponseDto::new), HttpStatus.OK);
      }

      @Operation(summary = "Changes status of task by id")
      @PostMapping("/tasks/{id}/change/status")
        public ResponseEntity<Object> changeTaskStatus(@PathVariable Long id) throws Exception {
            TaskEntity task = taskService.findById(id);

              projectService.checkIfProjectInProgress(task.getProjectId());

              //return ResponseEntity.badRequest().body("Bad Request: [ The project is only in 'BACKLOG' stage! ]");


              taskService.changeStatus(id);


              //return ResponseEntity.badRequest().body("Bad Request: [ The task has already been done! ]");

          return ResponseEntity.ok().body("Task(id: "+ id+") status has been changed to "+task.getStatus().name()+" successfully!");
      }
        @Operation(summary = "Changes tasks version")
        @PostMapping("/tasks/{id}/change/version")
        public ResponseEntity<String> changeTaskVersion(@PathVariable Long id, @RequestBody VersionRequestDto version) throws Exception {

            version.setStartTime(Calendar.getInstance());
            TaskEntity task = taskService.findById(id);

            version.setTask(task);
          List<TaskVersionEntity> versions = task.getVersions();
          versions.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
            if (versions.get(versions.size() - 1).getVersion() >= version.getVersion()) return ResponseEntity.badRequest().body("Bad Request: [ Can't set version that is less than current version of the task. Current version is: "+versions.get(versions.size() - 1).getVersion()+" ]");

              taskVersionService.changeVersion(version.convertToTaskVersionEntity(), task);

              //return ResponseEntity.badRequest().body("Bad Request: [ Can't change version of the task with 'BACKLOG' or 'DONE' status! ]");


          return ResponseEntity.ok().body("Task (id = "+ task.getId() +") version has been changed to: "+ version.getVersion() +" successfully!");
      }

    @Operation(summary = "Gets all versions of the task")
    @GetMapping("/tasks/{id}/versions")
    public ResponseEntity<Object> viewTaskVersion(@PathVariable Long id) throws NotFoundException {
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
