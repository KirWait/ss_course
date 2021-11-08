package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.example.dto.mapper.ProjectMapper;
import org.example.dto.mapper.TaskMapper;
import org.example.dto.project.ProjectResponseDto;
import org.example.dto.task.TaskRequestDto;
import org.example.dto.task.TaskResponseDto;
import org.example.entity.TaskEntity;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Tag(name = "user-controller", description = "The ROLE_USER API")
@RequestMapping("/api")
public class UserController {

    private final TaskService taskService;
    private final ProjectService projectService;

    public UserController(TaskService taskService, ProjectService projectService) {

        this.projectService = projectService;
        this.taskService = taskService;
    }

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);
    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

      @Operation(summary = "Gets all projects")
      @GetMapping("/projects")
      public ResponseEntity<List<ProjectResponseDto>> getAllProjects(){

          List<ProjectResponseDto> responseDtoList = projectService.getAll().stream()
                  .map(projectMapper::projectEntityToProjectResponseDto).collect(Collectors.toList());

          return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
      }

      @Operation(summary = "Gets project by id")
      @GetMapping("/projects/{id}")
      public ResponseEntity<ProjectResponseDto> getProject(@PathVariable Long id) throws NotFoundException {

          ProjectResponseDto responseDto = projectMapper.projectEntityToProjectResponseDto(projectService.findById(id));

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
      }

      @Operation(summary = "Gets tasks by project id")
      @GetMapping("/projects/{id}/tasks")
        public ResponseEntity<List<TaskResponseDto>> getProjectTasks(@PathVariable Long id){

          List<TaskResponseDto> responseDtoList = taskService.findAllByProjectId(id).stream()
                  .map(taskMapper::taskEntityToTaskResponseDto).collect(Collectors.toList());

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
      }

      @Operation(summary = "Changes status of task by id")
      @PostMapping("/tasks/{id}/change/status")
        public ResponseEntity<String> changeTaskStatus(@PathVariable Long id) throws Exception {

             TaskEntity task = taskService.findById(id);

              if (projectService.isProjectAvailableToChangeTaskStatus(task.getProjectId())) {
                  taskService.changeStatus(id);
              }

          return new ResponseEntity<>(String.format("Task(id: %d) status has been changed to %s successfully!", id, task.getStatus().name()), HttpStatus.OK);
      }

    @Operation(summary = "Finds task by filter")
    @PostMapping("/tasks/filter_search")
    public ResponseEntity<List<TaskResponseDto>> changeTaskVersion(@RequestBody TaskRequestDto requestDto) throws NotFoundException {

          List<TaskEntity> resultEntity = taskService.searchByFilter(requestDto);

          List<TaskResponseDto> resultResponseDto = resultEntity.stream().map(taskMapper::taskEntityToTaskResponseDto).collect(Collectors.toList());

        return new ResponseEntity<>(resultResponseDto, HttpStatus.OK);
      }

    @Operation(summary = "Counts unfinished tasks by release version")
    @GetMapping("/project/{projectId}/tasks/")
    public ResponseEntity<List<TaskResponseDto>> findUnfinishedTasks(@PathVariable Long projectId, @RequestParam(value = "releaseVersion") String releaseVersion) {

        List<TaskEntity> taskEntityList = taskService.findUnfinishedAndExpiredTasksByReleaseVersion(projectId, releaseVersion);

        List<TaskResponseDto> resultResponseDto = taskEntityList.stream().map(taskMapper::taskEntityToTaskResponseDto).collect(Collectors.toList());

        return new ResponseEntity<>(resultResponseDto, HttpStatus.OK);
    }
}


