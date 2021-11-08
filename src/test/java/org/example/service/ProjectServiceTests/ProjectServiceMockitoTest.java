package org.example.service.ProjectServiceTests;

import javassist.NotFoundException;
import org.example.dto.project.ProjectRequestDto;
import org.example.entity.UserEntity;
import org.example.exception.InvalidStatusException;
import org.example.entity.ProjectEntity;
import org.example.enumeration.Status;
import org.example.repository.ProjectRepository;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceMockitoTest {



    @Autowired
    private ProjectService projectService;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @MockBean
    private ProjectRepository projectRepositoryMock;

    private static final Long CUSTOMER_ID = 1L;
    private static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    private static final String PROJECT_EXIST_NAME = "EXIST";
    private static final String PROJECT_NON_EXIST_NAME = "NON_EXIST";
    private static final List<ProjectEntity> EXISTING_PROJECT_LIST = List.of(new ProjectEntity("PROJECT1"),new ProjectEntity("PROJECT2"));
    private static final List<ProjectEntity> NON_EXISTING_PROJECT_LIST = List.of(new ProjectEntity("PROJECT3"),new ProjectEntity("PROJECT4"));
    private static final Long EXISTING_ID_FOR_BACKLOG_PROJECT = 100000L;
    private static final Long EXISTING_ID_FOR_IN_PROGRESS_PROJECT = 100001L;
    private static final Long EXISTING_ID_FOR_DONE_PROJECT = 100002L;
    private static final Long NON_EXISTING_ID = 900000L;

    @Before
    public void setUp() throws NotFoundException {
        given(projectRepositoryMock.findByName(PROJECT_EXIST_NAME)).willReturn(new ProjectEntity(PROJECT_EXIST_NAME));
        given(projectRepositoryMock.findByName(PROJECT_NON_EXIST_NAME)).willReturn(null);
        given(projectRepositoryMock.findAll()).willReturn(EXISTING_PROJECT_LIST);
        given(projectRepositoryMock.findById(EXISTING_ID_FOR_BACKLOG_PROJECT)).willReturn(Optional.of(new ProjectEntity(EXISTING_ID_FOR_BACKLOG_PROJECT, PROJECT_EXIST_NAME, 1L, Status.BACKLOG)));
        given(projectRepositoryMock.findById(EXISTING_ID_FOR_IN_PROGRESS_PROJECT)).willReturn(Optional.of(new ProjectEntity(EXISTING_ID_FOR_IN_PROGRESS_PROJECT, PROJECT_EXIST_NAME, 1L, Status.IN_PROGRESS)));
        given(projectRepositoryMock.findById(EXISTING_ID_FOR_DONE_PROJECT)).willReturn(Optional.of(new ProjectEntity(EXISTING_ID_FOR_DONE_PROJECT, PROJECT_EXIST_NAME, 1L, Status.DONE)));
        given(taskService.checkForTasksInProgressAndBacklog(EXISTING_ID_FOR_IN_PROGRESS_PROJECT)).willReturn(true);
        given(userService.findByUsername(CUSTOMER_NAME)).willReturn(new UserEntity(CUSTOMER_ID));
    }


    @Test(expected = InvalidStatusException.class)
    public void isProjectAvailableToChangeTaskStatusShouldThrowInvalidStatusException() throws NotFoundException {
        projectService.isProjectAvailableToChangeTaskStatus(EXISTING_ID_FOR_BACKLOG_PROJECT);
    }

    @Test
    public void isProjectAvailableToChangeTaskStatusShouldReturnTrue() throws InvalidStatusException, NotFoundException{
        assertThat(projectService.isProjectAvailableToChangeTaskStatus(EXISTING_ID_FOR_IN_PROGRESS_PROJECT)).isTrue();
    }

    @Test
    public void findByProjectNameShouldFindProject() throws NotFoundException {
        ProjectEntity project = projectService.findByProjectName(PROJECT_EXIST_NAME);
        assertThat(project).isNotNull();
        assertThat(project.getName()).isEqualTo(PROJECT_EXIST_NAME);
    }

    @Test(expected = NotFoundException.class)
    public void findByProjectNameShouldThrowNotFoundException() throws NotFoundException {
        projectService.findByProjectName(PROJECT_NON_EXIST_NAME);
    }

    @Test
    public void getAllShouldReturnExistingProjectList() {
        assertThat(projectService.getAll()).isEqualTo(EXISTING_PROJECT_LIST);
    }

    @Test
    public void getAllShouldNotBeEqualToNonExistingProjectList() {
        assertThat(projectService.getAll()).isNotEqualTo(NON_EXISTING_PROJECT_LIST);
    }
    @Test
    public void findByIdShouldFindProject() throws NotFoundException {
        assertThat(projectService.findById(EXISTING_ID_FOR_BACKLOG_PROJECT)).isNotNull();
    }

    @Test(expected = NotFoundException.class)
    public void findByIdShouldThrowNotFoundException() throws NotFoundException {
        projectService.findById(NON_EXISTING_ID);
    }

    @Test(expected = InvalidStatusException.class)
    public void changeStatusShouldThrowInvalidStatusException() throws InvalidStatusException, NotFoundException {
        projectService.changeStatus(EXISTING_ID_FOR_DONE_PROJECT);
    }
    @Test
    public void changeStatusShouldChangeStatusToInProgress() throws InvalidStatusException, NotFoundException {
        projectService.changeStatus(EXISTING_ID_FOR_BACKLOG_PROJECT);
        assertThat(projectService.findById(EXISTING_ID_FOR_BACKLOG_PROJECT).getStatus()).isEqualTo(Status.IN_PROGRESS);
    }

    @Test
    public void changeStatusShouldChangeStatusToDone() throws InvalidStatusException, NotFoundException {
        projectService.changeStatus(EXISTING_ID_FOR_IN_PROGRESS_PROJECT);
        assertThat(projectService.findById(EXISTING_ID_FOR_IN_PROGRESS_PROJECT).getStatus()).isEqualTo(Status.DONE);
    }

    @Test
    public void setUpRequestDtoShouldSetUpCorrectly() throws NotFoundException {
        ProjectRequestDto requestDto = new ProjectRequestDto(CUSTOMER_NAME,PROJECT_EXIST_NAME);
        projectService.setUpRequestDto(requestDto);
        assertThat(requestDto.getCustomerId()).isEqualTo(CUSTOMER_ID);
        assertThat(requestDto.getStatus()).isEqualTo(Status.BACKLOG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUpRequestDtoShouldThrowIllegalArgumentException() throws IllegalArgumentException, NotFoundException {
        ProjectRequestDto requestDto = new ProjectRequestDto(PROJECT_EXIST_NAME, CUSTOMER_ID);
        projectService.setUpRequestDto(requestDto);
    }
}
