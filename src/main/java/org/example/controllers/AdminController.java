package org.example.controllers;

import org.example.DTOs.ProjectRequestDto;
import org.example.DTOs.TaskRequestDto;
import org.example.entities.ProjectEntity;
import org.example.entities.TaskVersionEntity;

import org.example.entities.UserEntity;
import org.example.entities.enums.Status;
import org.example.services.ProjectService;
import org.example.services.TaskService;
import org.example.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;

    public AdminController(TaskService taskService, ProjectService projectService, UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @PostMapping("/projects")
    public ResponseEntity createProject(@RequestBody ProjectRequestDto requestDto){

        String currentSessionUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentSessionUser = userService.findByUsername(currentSessionUserName);

        requestDto.setCustomerId(currentSessionUser.getUser_id());
        projectService.save(requestDto.convertToProjectEntity());
        return ResponseEntity.ok().body("The project with name:"+ requestDto.getName() +" and author: "+currentSessionUserName +"has been submitted successfully!");
    }


    @PostMapping("/projects/{id}/task")
    public ResponseEntity<Object> createTaskInProject(@PathVariable Long id, @RequestBody TaskRequestDto taskRequestDto){

        String currentSessionUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentSessionUser = userService.findByUsername(currentSessionUserName);

        taskRequestDto.setAuthorId(currentSessionUser.getUser_id());
        taskRequestDto.setProjectId(id);
        taskRequestDto.setVersion(List.of(new TaskVersionEntity("1.0", Calendar.getInstance())));
        taskRequestDto.setStatus(Status.BACKLOG.name());

            try {
                taskService.save(taskRequestDto.convertToTaskEntity());
            }

           catch(Exception e){
           return ResponseEntity.badRequest().body("Bad Request: [ The project doesn't exist or already been done! ]");
       }

        return ResponseEntity.ok().body("The task with name: "+ taskRequestDto.getName() +" and author: "+currentSessionUserName +" has been submitted successfully!");
}

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Object> deleteTaskById(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.ok().body("The task with id: "+id +" has been deleted successfully!");
    }

    @PostMapping("/projects/{id}/change")
    public ResponseEntity<Object> changeProjectStatus(@PathVariable Long id){
        ProjectEntity project = projectService.findById(id);
        if ((project.getStatus() == Status.IN_PROGRESS && taskService.checkForTasksInProgressAndBacklog(id))
                || (project.getStatus() == Status.BACKLOG)
                || (project.getStatus() == Status.DONE)) {

            try {
                projectService.changeStatus(id);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Bad Request: [ The project has already been done! ]");
            }

        }
        else return ResponseEntity.badRequest().body(
                "Bad Request: [ Can't finish the project: there are unfinished tasks! ]"
        );
        return ResponseEntity.ok().body("The project status with id: "+id +" and name: "+project.getProjectName()+" has been changed to "+projectService.findById(id).getStatus()+" successfully!");
    }
}
