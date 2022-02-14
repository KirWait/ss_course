package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.example.dto.*;
import org.example.entity.ProjectEntity;
import org.example.entity.ReleaseEntity;
import org.example.entity.TaskEntity;
import org.example.exception.InvalidStatusException;
import org.example.exception.PageException;
import org.example.mapper.ProjectMapper;
import org.example.mapper.ReleaseMapper;
import org.example.mapper.TaskMapper;
import org.example.service.ProjectService;
import org.example.service.ReleaseService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.example.translator.TranslationService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "admin-controller", description = "The ROLE_ADMIN API")
public class AdminController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final ReleaseService releaseService;
    private final TranslationService translationService;
    private final UserService userService;

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);
    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);
    private final ReleaseMapper releaseMapper = Mappers.getMapper(ReleaseMapper.class);


    public AdminController(TaskService taskService, ProjectService projectService, ReleaseService releaseService,
                           TranslationService translationService, UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.releaseService = releaseService;
        this.translationService = translationService;
        this.userService = userService;
    }

    @Operation(summary = "Gets all projects")
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects(@RequestParam(name = "isDeleted", defaultValue = "false")
                                                                               boolean isDeleted,
                                                                   @RequestParam(name = "pageSize", defaultValue = "2", required = false)
                                                                           int pageSize,
                                                                   @RequestParam(name = "page", defaultValue = "1", required = false)
                                                                           int page) throws PageException {

        List<ProjectResponseDto> responseDtoList = projectService.getAllByPage(page, pageSize, isDeleted).stream()
                .map(projectMapper::projectEntityToProjectResponseDto).collect(Collectors.toList());
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @PostMapping("/projects")
    @Operation(summary = "Creates project")
    public ResponseEntity<ProjectResponseDto> createProject(@RequestBody ProjectRequestDto requestDto) throws NotFoundException {

        projectService.setUpRequestDto(requestDto);
        ProjectEntity project = projectMapper.projectRequestDtoToProjectEntity(requestDto);
        projectService.save(project);
        ProjectResponseDto responseDto = projectMapper.projectEntityToProjectResponseDto(project);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @PostMapping("/projects/{projectId}/task")
    @Operation(summary = "Creates task in project with path id")
    public ResponseEntity<TaskResponseDto> createTaskInProject(@PathVariable Long projectId, @RequestBody TaskRequestDto requestDto) throws NotFoundException {

        ProjectEntity project = projectService.findById(projectId);
        projectService.ifProjectAvailableToCreateTaskOrThrowException( project.getId() ); //else InvalidStatusException will be thrown
        taskService.setUpRequestDto(requestDto, projectId);
        TaskEntity taskEntity = taskMapper.taskRequestDtoToTaskEntity(requestDto);
        taskService.save(taskEntity);
        TaskResponseDto responseDto = taskMapper.taskEntityToTaskResponseDto(taskEntity);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{taskId}")
    @Operation(summary = "Deletes task by id")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long taskId) throws NotFoundException {

        taskService.delete(taskId);

        return ResponseEntity.ok().body(String.format(
                translationService.getTranslation("The task with id: %d has been deleted successfully!"), taskId));
    }

    @PostMapping("/projects/{projectId}/change")
    @Operation(summary = "Changes status of project by id")
    public ResponseEntity<String> changeProjectStatus(@PathVariable Long projectId) throws NotFoundException, InvalidStatusException {

        ProjectEntity project = projectService.findById(projectId);
        projectService.changeStatus(project.getId());

        return new ResponseEntity<>(String.format(
                translationService.getTranslation(
                        "The project status with id: %d and name: %s has been changed to %s successfully!"),
                projectId, project.getName(), project.getStatus().name() ), HttpStatus.OK);
    }

    @PostMapping("/projects/{projectId}/release")
    @Operation(summary = "Creates new release of project")
    public ResponseEntity<ReleaseResponseDto> createRelease(@PathVariable Long projectId, @RequestBody ReleaseRequestDto requestDto) throws ParseException, NotFoundException {

        if (projectService.ifProjectAvailableToCreateReleaseOrThrowException(projectId)) {
            releaseService.setUpRequestDto(requestDto, projectId);
        }
        ReleaseEntity releaseEntity = releaseMapper.releaseRequestDtoToReleaseEntity(requestDto);
        releaseService.save(releaseEntity);
        ReleaseResponseDto responseDto = releaseMapper.releaseEntityToReleaseResponseDto(releaseEntity);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/projects/{projectId}")
    @Operation(summary = "Deletes project by id")
    public ResponseEntity<String> deleteProjectById(@PathVariable Long projectId) throws NotFoundException {

        projectService.delete(projectId);

        return ResponseEntity.ok().body(String.format(
                translationService.getTranslation("The project with id: %d has been deleted successfully!"),
                projectId));
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "Deletes user by id")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) throws NotFoundException {

        userService.delete(userId);

        return ResponseEntity.ok().body(String.format(
                translationService.getTranslation("The user with id: %d has been deleted successfully!"),
                userId));
    }

    @DeleteMapping("/releases/{releaseId}")
    @Operation(summary = "Deletes release by id")
    public ResponseEntity<String> deleteReleaseById(@PathVariable Long releaseId) throws NotFoundException {

        releaseService.delete(releaseId);

        return ResponseEntity.ok().body(String.format(
                translationService.getTranslation("The release with id: %d has been deleted successfully!"),
                releaseId));
    }
    @GetMapping("projects/{projectId}/statistics")
    @Operation(summary = "Gets statistic of a projects")
    public ResponseEntity<ProjectStatisticsResponseDto> getStatistics(@PathVariable Long projectId)
            throws NotFoundException, ParseException {
        ProjectStatisticsResponseDto responseDto = projectService.getStatistic(projectId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}

