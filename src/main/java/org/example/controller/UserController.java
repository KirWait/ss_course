package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.example.mapper.ProjectMapper;
import org.example.mapper.TaskMapper;
import org.example.dto.ProjectResponseDto;
import org.example.dto.TaskRequestDto;
import org.example.dto.TaskResponseDto;
import org.example.entity.TaskEntity;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.specification.TaskSpecificationBuilder;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        public ResponseEntity<List<TaskResponseDto>> getProjectTasks(@PathVariable Long id) throws NotFoundException {

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
    public ResponseEntity<List<TaskResponseDto>> filterSearch(@RequestBody TaskRequestDto requestDto) throws NotFoundException {
          List<TaskEntity> resultEntity = taskService.searchByFilter(requestDto);


          List<TaskResponseDto> resultResponseDto = resultEntity.stream()
                  .map(taskMapper::taskEntityToTaskResponseDto).collect(Collectors.toList());

        return new ResponseEntity<>(resultResponseDto, HttpStatus.OK);
      }

    @Operation(summary = "Counts unfinished tasks by release version")
    @GetMapping("/project/{projectId}/tasks/")
    public ResponseEntity<List<TaskResponseDto>> findUnfinishedTasks(@PathVariable Long projectId, @RequestParam(value = "releaseVersion") String releaseVersion) throws NotFoundException {

        List<TaskEntity> taskEntityList = taskService.findUnfinishedAndExpiredTasksByReleaseVersion(projectId, releaseVersion);

        List<TaskResponseDto> resultResponseDto = taskEntityList.stream()
                .map(taskMapper::taskEntityToTaskResponseDto).collect(Collectors.toList());

        return new ResponseEntity<>(resultResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "Finds task by filter using JPA Specifications")
    @GetMapping("/tasks/filter_search2")
    public ResponseEntity<List<TaskResponseDto>> filterSearch2(@RequestParam(value = "search") String search){

        TaskSpecificationBuilder builder = new TaskSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<TaskEntity> spec = builder.build();

        List<TaskEntity> result = taskService.findAll(spec);

        List<TaskResponseDto> resultResponseDto = result.stream()
                .map(taskMapper::taskEntityToTaskResponseDto).collect(Collectors.toList());


        return new ResponseEntity<>(resultResponseDto, HttpStatus.OK);
      }
}


