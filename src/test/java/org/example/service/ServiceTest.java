package org.example.service;

import javassist.NotFoundException;
import org.example.dto.TransactionRequestDto;
import org.example.entity.ProjectEntity;
import org.example.entity.ReleaseEntity;
import org.example.entity.TaskEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Status;
import org.example.exception.InvalidStatusException;
import org.example.exception.UnpaidException;
import org.example.feignClient.ServiceFeignClient;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.example.constants.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTest {
/*
* This test emulating life-time of a whole project on the service level:
* 1)User and customer registration
* 2)Creating new project
* 3)Creation of a release
* 4)Creation of the task along with release
* 5)Attempt to change task status while project is only in BACKLOG
* 6)Changing project status to IN_PROGRESS
* 7)Changing task status to IN_PROGRESS
* 8)Attempt to change project status to DONE while there are unfinished tasks
* 9)Changing task status to DONE
* 10)Changing project status to DONE
* 11)Attempt to create a new task -> exception is thrown
* 12)Attempt to create a new release -> exception is thrown
* */
    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ReleaseService releaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceFeignClient feignClient;


    @Test
    @Order(1)
    public void registerShouldSaveUserAndCustomer() throws NotFoundException {

        assertThrows(NotFoundException.class, () -> userService.findByUsername(USER_USERNAME));

        assertThrows(NotFoundException.class, () -> userService.findByUsername(CUSTOMER_USERNAME));

        userService.register(USER);

        userService.register(CUSTOMER);

        UserEntity userAfterRegistration = userService.findByUsername(USER_USERNAME);

        assertThat(userAfterRegistration).isNotNull();

        assertThat(userAfterRegistration.getUsername()).isEqualTo(USER_USERNAME);

        UserEntity customerAfterRegistration = userService.findByUsername(CUSTOMER_USERNAME);

        assertThat(customerAfterRegistration).isNotNull();

        assertThat(customerAfterRegistration.getId()).isEqualTo(CUSTOMER_ID);
    }

    @Test
    @Order(2)
    public void saveProjectShouldSaveProject() throws NotFoundException {

        assertThrows(NotFoundException.class, () -> projectService.findByProjectName(PROJECT_NAME));

        projectService.save(PROJECT);

        ProjectEntity savedProject = projectService.findByProjectName(PROJECT_NAME);

        assertThat(savedProject).isNotNull();

        assertThat(savedProject.getName()).isEqualTo(PROJECT_NAME);

    }

    @Test
    @Order(3)
    public void saveReleaseShouldSaveRelease() throws NotFoundException {

        ProjectEntity project = projectService.findByProjectName(PROJECT_NAME);

        assertThrows(NotFoundException.class, () -> releaseService.findByVersionAndProjectId(RELEASE_VERSION, project.getId()));

        RELEASE.setProject(project);

        releaseService.save(RELEASE);

        ReleaseEntity savedRelease = releaseService.findByVersionAndProjectId(RELEASE_VERSION, project.getId());

        assertThat(savedRelease).isNotNull();

        assertThat(savedRelease.getVersion()).isEqualTo(RELEASE_VERSION);

    }


    @Test
    @Order(4)
    public void saveTaskShouldSaveTask() throws NotFoundException {

        assertThrows(NotFoundException.class, () -> taskService.findByName(TASK_NAME));

        taskService.save(TASK);

        TaskEntity savedTask = taskService.findByName(TASK_NAME);

        assertThat(savedTask).isNotNull();

        assertThat(savedTask.getName()).isEqualTo(TASK_NAME);
        assertThat(savedTask.getRelease().getVersion()).isEqualTo(RELEASE_VERSION);

    }

    @Test
    @Order(5)
    public void changeTaskStatusToInProgressShouldThrowInvalidStatusException() {

        assertThrows(InvalidStatusException.class, () -> projectService.isProjectAvailableToChangeTaskStatus(PROJECT_ID));
    }

    @Test
    @Order(6)
    public void changeProjectStatusToInProgressShouldChangeStatus() throws NotFoundException {

        ProjectEntity project = projectService.findByProjectName(PROJECT_NAME);

        Long projectId = project.getId();

        assertThat(project.getStatus()).isEqualTo(Status.BACKLOG);

        projectService.changeStatus(projectId);

        assertThat(projectService.findById(PROJECT_ID).getStatus()).isEqualTo(Status.IN_PROGRESS);
    }
    @Test
    @Order(7)
    public void isProjectAvailableToChangeTaskStatusShouldReturnTrueAndTaskShouldChangeStatusToInProgressAndSetStartTime() throws NotFoundException {

        assertThat(projectService
                .isProjectAvailableToChangeTaskStatus(projectService.findByProjectName(PROJECT_NAME).getId())).isTrue();

        taskService.changeStatus(TASK_ID);

        TaskEntity task = taskService.findByName(TASK_NAME);

        assertThat(task.getStatus()).isEqualTo(Status.IN_PROGRESS);

        assertThat(task.getStartTime()).isNotNull();

    }

    @Test
    @Order(8)
    public void projectChangeStatusOrThrowExceptionShouldThrowException() {
        assertThrows(InvalidStatusException.class, () -> projectService.changeStatus(PROJECT_ID));
    }

    @Test
    @Order(9)
    public void isProjectAvailableToChangeTaskStatusShouldReturnTrueAndTaskShouldChangeStatusToDoneAndSetEndTime() throws NotFoundException {
        assertThat(projectService.isProjectAvailableToChangeTaskStatus(PROJECT_ID)).isTrue();

        taskService.changeStatus(TASK_ID);

        TaskEntity task = taskService.findByName(TASK_NAME);

        assertThat(task.getStatus()).isEqualTo(Status.DONE);

        assertThat(task.getEndTime()).isNotNull();
    }

    @Test
    @Order(10)
    public void projectChangeStatusOrThrowExceptionShouldChangeStatusToDone() throws NotFoundException {

        projectService.changeStatus(PROJECT_ID);

        ProjectEntity project = projectService.findByProjectName(PROJECT_NAME);

        assertThat(project.getStatus()).isEqualTo(Status.DONE);

    }

    @Test
    @Order(11)
    public void ifProjectAvailableToCreateTaskOrThrowExceptionShouldThrowInvalidStatusException() {

        assertThrows(InvalidStatusException.class, () ->
                projectService.ifProjectAvailableToCreateTaskOrThrowException(PROJECT_ID));
    }

    @Test
    @Order(12)
    public void createReleaseShouldThrowInvalidStatusException() {

        assertThrows(InvalidStatusException.class, () ->
                projectService.ifProjectAvailableToCreateReleaseOrThrowException(PROJECT_ID));
    }
}






