package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.example.dto.PageableResponseDto;
import org.example.dto.ProjectResponseDto;
import org.example.dto.TaskResponseDto;
import org.example.entity.TaskEntity;
import org.example.filters.TaskFilter;
import org.example.mapper.ProjectMapper;
import org.example.mapper.TaskMapper;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.specification.TaskSpecificationBuilder;
import org.example.translator.TranslationService;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@Tag(name = "user-controller", description = "The ROLE_USER API")
@RequestMapping("/api/user")
public class UserController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final TranslationService translationService;

    public UserController(TaskService taskService, ProjectService projectService, TranslationService translationService) {

        this.projectService = projectService;
        this.taskService = taskService;
        this.translationService = translationService;
    }

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);
    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

      @Operation(summary = "Gets project by id")
      @GetMapping("/projects/{id}")
      public ResponseEntity<ProjectResponseDto> getProject(@PathVariable Long id) throws NotFoundException {

          ProjectResponseDto responseDto = projectMapper.projectEntityToProjectResponseDto(projectService.findById(id));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
      }

      @Operation(summary = "Gets tasks by project id")
      @GetMapping("/projects/{id}/tasks")
        public ResponseEntity<List<TaskResponseDto>> getProjectTasks(@PathVariable Long id) throws NotFoundException {

          projectService.findById(id);
          List<TaskResponseDto> responseDtoList = taskService.findAllByProjectIdAndDeleted(id, false).stream()
                  .map(taskMapper::taskEntityToTaskResponseDto).collect(Collectors.toList());

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
      }

      @Operation(summary = "Changes status of task by id")
      @PostMapping("/tasks/{id}/change/status")
        public ResponseEntity<String> changeTaskStatus(@PathVariable Long id) throws Exception {

             TaskEntity task = taskService.findById(id);

              if (projectService.isProjectAvailableToChangeTaskStatus(task.getProject().getId())) {
                  taskService.changeStatus(id);
              }

          return new ResponseEntity<>(String.format(
                  translationService.getTranslation(
                          "Task with id: %d status has been changed to %s successfully!"
                  ), id, task.getStatus().name()), HttpStatus.OK);
      }

    @Operation(summary = "Counts unfinished tasks by release version")
    @GetMapping("/project/{projectId}/tasks/")
    public ResponseEntity<List<TaskResponseDto>> findUnfinishedTasks(@PathVariable Long projectId, @RequestParam(value = "releaseVersion") String releaseVersion) throws NotFoundException {

        List<TaskEntity> expiredTasks = taskService.findExpiredTasksByReleaseVersion(projectId, releaseVersion);
        List<TaskEntity> unfinishedTasks = taskService.findUnfinishedTasksByReleaseVersion(projectId, releaseVersion);

        Stream<TaskEntity> concatLists = Stream.concat(expiredTasks.stream(), unfinishedTasks.stream());
        List<TaskResponseDto> resultResponseDto = concatLists.map(taskMapper::taskEntityToTaskResponseDto).collect(Collectors.toList());

        return new ResponseEntity<>(resultResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "Finds task by filter using JPA Specifications")
    @GetMapping("/tasks/filter_search2")
    public ResponseEntity<Page<TaskResponseDto>> filterSearch2(@RequestBody TaskFilter filter) {
          filter.setSearch(filter.getSearch().concat(",deleted:false"));
        System.out.println(filter.getSearch());
        TaskSpecificationBuilder builder = new TaskSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
        Matcher matcher = pattern.matcher(filter.getSearch() + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<TaskEntity> spec = builder.build();

        Page<TaskResponseDto> result = taskService.findAll(spec, filter.getPage(), filter.getPageSize());

        return new ResponseEntity<>(result, HttpStatus.OK);
      }
}


