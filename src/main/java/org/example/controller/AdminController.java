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
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);
    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);
    private final ReleaseMapper releaseMapper = Mappers.getMapper(ReleaseMapper.class);

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public AdminController(TaskService taskService, ProjectService projectService, ReleaseService releaseService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.releaseService = releaseService;
    }

    @PostMapping("/projects")
    @Operation(summary = "Creates project")
    public ResponseEntity<ProjectResponseDto> createProject(@RequestBody ProjectRequestDto requestDto) throws NotFoundException {

        projectService.setUpRequestDto(requestDto);

        logger.info("Successfully set up ProjectRequestDto");

        ProjectEntity project = projectMapper.projectRequestDtoToProjectEntity(requestDto);

        projectService.save(project);

        logger.info("Successfully saved project to database");

        ProjectResponseDto responseDto = projectMapper.projectEntityToProjectResponseDto(project);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @PostMapping("/projects/{projectId}/task")
    @Operation(summary = "Creates task in project with path id")
    public ResponseEntity<TaskResponseDto> createTaskInProject(@PathVariable Long projectId, @RequestBody TaskRequestDto requestDto) throws NotFoundException {

        ProjectEntity project = projectService.findById(projectId);

        projectService.ifProjectAvailableToCreateTaskOrThrowException( project.getId() ); //else InvalidStatusException will be thrown

        logger.info(String.format("Project with id: %d is available to create task", projectId));

        taskService.setUpRequestDto(requestDto, projectId);

        logger.info("Successfully set up TaskRequestDto");

        TaskEntity taskEntity = taskMapper.taskRequestDtoToTaskEntity(requestDto);

        taskService.save(taskEntity);

        logger.info("Successfully saved task to database");

        TaskResponseDto responseDto = taskMapper.taskEntityToTaskResponseDto(taskEntity);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{taskId}")
    @Operation(summary = "Deletes task by id")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long taskId){

        taskService.delete(taskId);

        logger.info(String.format("Successfully deleted task with id: %d from the database", taskId));

        return ResponseEntity.ok().body(String.format("The task with id: %d has been deleted successfully!", taskId));
    }

    @PostMapping("/projects/{projectId}/change")
    @Operation(summary = "Changes status of project by id")
    public ResponseEntity<String> changeProjectStatus(@PathVariable Long projectId) throws NotFoundException, InvalidStatusException {

        ProjectEntity project = projectService.findById(projectId);

        projectService.projectChangeStatusOrThrowException(project.getId());

        logger.info(String.format("Successfully changed status of the project with id: %d, to %s", projectId, project.getStatus()));

        return new ResponseEntity<>(String.format("The project status with id: %d and name: %s has been changed to %s successfully!",
                projectId, project.getName(), project.getStatus().name() ), HttpStatus.OK);
    }

    @PostMapping("/projects/{projectId}/release")
    @Operation(summary = "Creates new release of project")
    public ResponseEntity<ReleaseResponseDto> createRelease(@PathVariable Long projectId, @RequestBody ReleaseRequestDto requestDto) throws ParseException, NotFoundException {

        if (projectService.ifProjectAvailableToCreateReleaseOrThrowException(projectId)) {
            logger.info(String.format("Project with id: %d is available to create release", projectId));

            releaseService.setUpRequestDto(requestDto, projectId);

            logger.info("Successfully set up ReleaseRequestDto");
        }

        ReleaseEntity releaseEntity = releaseMapper.releaseRequestDtoToReleaseEntity(requestDto);

        releaseService.save(releaseEntity);

        logger.info("Successfully saved release to the database");

        ReleaseResponseDto responseDto = releaseMapper.releaseEntityToReleaseResponseDto(releaseEntity);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
