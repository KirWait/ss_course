package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.example.mapper.ProjectMapper;
import org.example.mapper.ReleaseMapper;
import org.example.mapper.TaskMapper;
import org.example.dto.ProjectRequestDto;
import org.example.dto.ProjectResponseDto;
import org.example.dto.TaskRequestDto;
import org.example.dto.TaskResponseDto;
import org.example.dto.ReleaseRequestDto;
import org.example.dto.ReleaseResponseDto;
import org.example.entity.ReleaseEntity;
import org.example.entity.TaskEntity;
import org.example.entity.ProjectEntity;
import org.example.exception.InvalidStatusException;
import org.example.service.ProjectService;
import org.example.service.ReleaseService;
import org.example.service.TaskService;
import org.example.translator.TranslationService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;


@RestController
@RequestMapping("/api/admin")
@Tag(name = "admin-controller", description = "The ROLE_ADMIN API")
public class AdminController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final ReleaseService releaseService;
    private final TranslationService translationService;

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);
    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);
    private final ReleaseMapper releaseMapper = Mappers.getMapper(ReleaseMapper.class);

    public AdminController(TaskService taskService, ProjectService projectService, ReleaseService releaseService,
    TranslationService translationService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.releaseService = releaseService;
        this.translationService = translationService;
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
    public ResponseEntity<String> deleteTaskById(@PathVariable Long taskId){

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
}
