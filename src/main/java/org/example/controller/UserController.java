package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.example.dto.mapper.ProjectMapper;
import org.example.dto.mapper.TaskMapper;
import org.example.dto.mapper.VersionMapper;
import org.example.dto.project.ProjectResponseDto;

import org.example.dto.task.TaskRequestDto;
import org.example.dto.task.TaskResponseDto;
import org.example.dto.version.VersionRequestDto;
import org.example.dto.version.VersionResponseDto;
import org.example.entity.TaskEntity;
import org.example.entity.TaskVersionEntity;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.service.TaskVersionService;
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
    private final TaskVersionService taskVersionService;

    public UserController(TaskService taskService, ProjectService projectService,TaskVersionService taskVersionService) {

        this.projectService = projectService;
        this.taskVersionService = taskVersionService;
        this.taskService = taskService;
    }

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);
    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);
    private final VersionMapper versionMapper = Mappers.getMapper(VersionMapper.class);


      @Operation(summary = "Gets all projects")
      @GetMapping("/projects")
      public ResponseEntity<List<ProjectResponseDto>> getAllProjects(){

          List<ProjectResponseDto> responseDtoList = projectService.getAll().stream().map(projectMapper::projectEntityToProjectResponseDTO).collect(Collectors.toList());

          return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
      }

      @Operation(summary = "Gets project by id")
      @GetMapping("/projects/{id}")
      public ResponseEntity<ProjectResponseDto> getProject(@PathVariable Long id) throws NotFoundException {

          ProjectResponseDto responseDto = projectMapper.projectEntityToProjectResponseDTO(projectService.findById(id));

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
      }

      @Operation(summary = "Gets tasks by project id")
      @GetMapping("/projects/{id}/tasks")
        public ResponseEntity<List<TaskResponseDto>> getProjectTasks(@PathVariable Long id){

          List<TaskResponseDto> responseDtoList = taskService.getAllByProjectId(id).stream().map(taskMapper::taskEntityToTaskResponseDto).collect(Collectors.toList());

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
      }

      @Operation(summary = "Changes status of task by id")
      @PostMapping("/tasks/{id}/change/status")
        public ResponseEntity<String> changeTaskStatus(@PathVariable Long id) throws Exception {

             TaskEntity task = taskService.findById(id);

              if (projectService.isProjectAvailableToChangeTaskStatus(task.getProjectId())) taskService.changeStatus(id);

          return new ResponseEntity<>(String.format("Task(id: %d) status has been changed to %s successfully!", id, task.getStatus().name()), HttpStatus.OK);
      }
        @Operation(summary = "Changes tasks version")
        @PostMapping("/tasks/{id}/change/version")
        public ResponseEntity<String> changeTaskVersion(@PathVariable Long id, @RequestBody VersionRequestDto requestDto) throws Exception {

          TaskEntity task = taskService.findById(id);

          taskVersionService.setUpRequestDto(requestDto, task);

          TaskVersionEntity taskVersionEntity = versionMapper.taskVersionRequestDtoToTaskVersionEntity(requestDto);

          taskVersionService.changeVersionOrThrowException(taskVersionEntity, task);

          VersionResponseDto responseDto = versionMapper.taskVersionEntityToTaskVersionResponseDto(taskVersionEntity);

          return new ResponseEntity<>(String.format("Task (id = %d) version has been changed to: %s successfully!", responseDto.getTask().getId(), responseDto.getVersion()), HttpStatus.OK);
      }

    @Operation(summary = "Gets all versions of the task")
    @GetMapping("/tasks/{id}/versions")
    public ResponseEntity<List<VersionResponseDto>> viewTaskVersion(@PathVariable Long id) throws NotFoundException {

        TaskEntity task = taskService.findById(id);

        List<TaskVersionEntity> taskVersionEntityList = taskVersionService.findAllByTaskOrderById(task);

        List<VersionResponseDto> responseDtoList = taskVersionEntityList.stream().map(versionMapper::taskVersionEntityToTaskVersionResponseDto).collect(Collectors.toList());

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @Operation(summary = "Finds task by filter")
    @PostMapping("/tasks/filter_search")
    public ResponseEntity<List<TaskResponseDto>> changeTaskVersion(@RequestBody TaskRequestDto requestDto) throws NotFoundException {

          List<TaskEntity> resultEntity = taskService.searchByFilter(requestDto);

          List<TaskResponseDto> resultResponseDto = resultEntity.stream().map(taskMapper::taskEntityToTaskResponseDto).collect(Collectors.toList());

        return new ResponseEntity<>(resultResponseDto, HttpStatus.OK);
      }





}


