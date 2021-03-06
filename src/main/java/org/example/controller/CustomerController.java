package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.example.dto.ProjectResponseDto;
import org.example.dto.TaskResponseDto;
import org.example.dto.TransactionRequestDto;
import org.example.dto.TransactionResponseDto;
import org.example.entity.ProjectEntity;
import org.example.entity.TaskEntity;
import org.example.exception.InvalidAccessException;
import org.example.feignClient.ServiceFeignClient;
import org.example.mapper.ProjectMapper;
import org.example.mapper.TaskMapper;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.example.translator.TranslationService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Tag(name = "customer-controller", description = "The ROLE_CUSTOMER API")
@RequestMapping("/api/customer")
public class CustomerController {

    private final ProjectService projectService;
    private final UserService userService;
    private final TaskService taskService;
    private final ServiceFeignClient feignClient;
    private final TranslationService translationService;

    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);
    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    public CustomerController(ProjectService projectService, UserService userService,
                              TaskService taskService, ServiceFeignClient feignClient,
                              TranslationService translationService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
        this.feignClient = feignClient;
        this.translationService = translationService;
    }

    @GetMapping("/projects")
    @Operation(summary = "Gets all projects where author is current session user")
    public ResponseEntity<List<ProjectResponseDto>> getCustomerProjects() throws NotFoundException {

        String currentSessionUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Long currentSessionUserId = userService.findByUsername(currentSessionUserName).getId();
        List<ProjectEntity> customerProjects = projectService.findAllByCustomerId(currentSessionUserId);
        List<ProjectResponseDto> responseDto = customerProjects.stream()
                .map(projectMapper::projectEntityToProjectResponseDto).collect(Collectors.toList());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/projects/{projectId}")
    @Operation(summary = "Gets all tasks of project with stated id where author is current session user")
    public ResponseEntity<List<TaskResponseDto>> getCustomerProjects(@PathVariable Long projectId) throws NotFoundException, InvalidAccessException {

        projectService.findById(projectId);
        String currentSessionUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Long currentSessionUserId = userService.findByUsername(currentSessionUserName).getId();
        List<ProjectEntity> customerProjects = projectService.findAllByCustomerId(currentSessionUserId);
        boolean isCustomer = customerProjects.stream().anyMatch(project -> Objects.equals(project.getId(), projectId));

        if (isCustomer){
            List<TaskEntity> taskEntityList = taskService.findAllByProjectId(projectId);
            List<TaskResponseDto> responseDtoList = taskEntityList.stream().
                    map(taskMapper::taskEntityToTaskResponseDto).collect(Collectors.toList());
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        }
        else {
            throw new InvalidAccessException(String.format(
                    translationService.getTranslation("You are not a customer of a project with id: %d"), projectId));
        }
    }

    @PostMapping("/projects/{projectId}")
    @Operation(summary = "Pays for the project with stated id via FeignClient")
    public ResponseEntity<TransactionResponseDto> payForProject(@PathVariable Long projectId, @RequestBody TransactionRequestDto requestDto) throws NotFoundException {

        projectService.findById(projectId);
        String currentSessionUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Long currentSessionUserId = userService.findByUsername(currentSessionUserName).getId();
        requestDto.setUserId(currentSessionUserId);

        return feignClient.payForProject(projectId, requestDto);
    }

    @GetMapping("/transaction_history")
    @Operation(summary = "Gets the transaction history of current session user")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionHistory() throws NotFoundException {
        String currentSessionUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Long currentSessionUserId = userService.findByUsername(currentSessionUserName).getId();

        return feignClient.getTransactionHistory(currentSessionUserId);
    }

}
