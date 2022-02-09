package org.example.service.impl;

import javassist.NotFoundException;
import org.example.dto.ProjectRequestDto;
import org.example.dto.ProjectStatisticsResponseDto;
import org.example.entity.ProjectEntity;
import org.example.entity.ReleaseEntity;
import org.example.entity.TaskEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Status;
import org.example.exception.DeletedException;
import org.example.exception.InvalidStatusException;
import org.example.exception.UnpaidException;
import org.example.feignClient.ServiceFeignClient;
import org.example.mapper.TaskMapper;
import org.example.repository.ProjectRepository;
import org.example.repository.ReleaseRepository;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.example.translator.TranslationService;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * This is the class that implements business-logic of projects in this app.
 * @author Kirill Zhdanov
*/
@Service
public class ProjectServiceImpl implements ProjectService {

    private final EntityManager entityManager;
    private final ProjectRepository projectRepository;
    private final TaskService taskService;
    private final UserService userService;
    private final ServiceFeignClient feignClient;
    private final TranslationService translationService;
    private final ReleaseRepository releaseRepository;
    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    public ProjectServiceImpl(EntityManager entityManager, ProjectRepository projectRepository,
                              TaskService taskService, UserService userService,
                              ServiceFeignClient feignClient, TranslationService translationService, ReleaseRepository releaseRepository) {
        this.entityManager = entityManager;
        this.projectRepository = projectRepository;
        this.taskService = taskService;
        this.userService = userService;
        this.feignClient = feignClient;
        this.translationService = translationService;
        this.releaseRepository = releaseRepository;
    }

    /**
     * Saves task to the database using Spring JPA Repository
     * @param projectEntity Entity of a project to save
     */
    @Override
    @Transactional
    public void save(ProjectEntity projectEntity) {
        projectRepository.save(projectEntity);
    }

    /**
     * Changes task status of project and saving it in database
     * @param id Project id
     */
    @Override
    public void changeStatus(Long id) throws InvalidStatusException, NotFoundException  {

        ProjectEntity projectEntity = findById(id);

        Status projectEntityStatus = projectEntity.getStatus();

        if (projectEntityStatus == Status.DONE) {
            throw new InvalidStatusException(translationService.getTranslation("The project has already been done!"));
        }
        if (projectEntityStatus == Status.IN_PROGRESS){
            if(taskService.checkForTasksInProgressAndBacklog(projectEntity.getId())) {
                projectEntity.setStatus(Status.DONE);
            }
            else{
                throw new InvalidStatusException(
                        translationService.getTranslation("Can't finish the project: there are unfinished tasks!"));
            }
        }
        if (projectEntityStatus == Status.BACKLOG) {
            Long paidSum = feignClient.getPaidSum(id);

            if(paidSum >= projectEntity.getPrice()){
                projectEntity.setPaid(true);
                projectRepository.save(projectEntity);
            }
            if (projectEntity.isPaid()) {
                projectEntity.setStatus(Status.IN_PROGRESS);
            }
            else{
                throw new UnpaidException(String.format(
                        translationService.getTranslation("The project with id: %d haven't even been paid!"), id));
            }
        }
        projectRepository.save(projectEntity);

    }

    /**
     * Checks availability of changing task status
     * @param id Project id
     */
    @Override
    public boolean isProjectAvailableToChangeTaskStatus(Long id) throws NotFoundException {

        ProjectEntity project = findById(id);
        if (project.getStatus() == Status.BACKLOG) {
            throw new InvalidStatusException(
                    translationService.getTranslation("The project is only in BACKLOG stage"));
        }

        if (project.getStatus() == Status.DONE) {
            throw new InvalidStatusException(
                    translationService.getTranslation(
                            "Can't manipulate with tasks of the project which has already been done!"));
        }

        return true;
    }


    /**
     * Finds project by name
     * @param name Project name
     */
    @Override
    public ProjectEntity findByProjectName(String name) throws NotFoundException {

        return projectRepository.findByNameAndDeleted(name, false)
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("No such project with name: %s!"), name)));
    }


    /**
     * Initializing some fields that shouldn't be defined manually(example: creationTime)
     * @param requestDto Json from HTTP request that mapped into request dto
     */
    @Override
    public void setUpRequestDto(ProjectRequestDto requestDto) throws NotFoundException, IllegalArgumentException{

        if (requestDto.getCustomerName() == null){

            UserEntity currentSessionUser = userService.getCurrentSessionUser();

            requestDto.setCustomer(currentSessionUser);
        }
        else {
            requestDto.setCustomer(userService.findByUsername(requestDto.getCustomerName()));
        }

        requestDto.setStatus(Status.BACKLOG);

    }
    /**
     * Checks availability to create task in project with stated id
     * @param id Project id
     */
    @Override
    public void ifProjectAvailableToCreateTaskOrThrowException(Long id) throws NotFoundException {
        ProjectEntity project = findById(id);
        if (project.getStatus() == Status.DONE) {
            throw new InvalidStatusException(translationService.getTranslation("The project has already been done!"));
        }

    }


    /**
     * Checks availability to create release of project with stated id
     * @param id Project id
     */
    @Override
    public boolean ifProjectAvailableToCreateReleaseOrThrowException(Long id) throws NotFoundException {
        ProjectEntity project = findById(id);
        if (project.getStatus() != Status.DONE) {
            return true;
        }
        else {
            throw new InvalidStatusException(
                    String.format(translationService.getTranslation("Can't create release: the project with id: %d has already been done!"), id));
        }
    }
    /**
     * Gets all the projects by customer id
     */
    @Override
    public List<ProjectEntity> findAllByCustomerId(Long customerId) {
        return projectRepository.findAllByCustomerIdAndDeleted(customerId, false);
    }

    @Override
    public ProjectStatisticsResponseDto getStatistic(Long projectId) throws NotFoundException, ParseException {

        List<TaskEntity> deletedTasks = taskService.findAllByProjectIdAndDeleted(projectId, true);
        for (TaskEntity deletedTask : deletedTasks) {
            long duration = taskService.countTaskTime(deletedTask);
            deletedTask.setTimeSpent(String.format("%02d hrs, %02d min",
                    TimeUnit.MILLISECONDS.toHours(duration),
                    TimeUnit.MILLISECONDS.toMinutes(duration) -
                            TimeUnit.MILLISECONDS.toHours(duration) * 60));
        }

        ProjectStatisticsResponseDto responseDto = new ProjectStatisticsResponseDto();
        responseDto.setDeletedTasks(deletedTasks.stream()
                        .map(taskMapper::taskEntityToTaskStatResponseDto).collect(Collectors.toList()));
        responseDto.setDeletedTasksCount(deletedTasks.size());
        long totalTimeSpent = 0;
        int unfinishedTasksCount = 0;
        int expiredTasksCount = 0;

        UserEntity customer = findById(projectId).getCustomer();
        responseDto.setCustomer(customer);

        List<TaskEntity> undeletedTasks = taskService.findAllByProjectIdAndDeleted(projectId, false);
        responseDto.setTasksCount(undeletedTasks.size());

        List<ReleaseEntity> releases = releaseRepository.findAllByProjectIdAndDeletedOrderByCreationTime(projectId, false)
                .orElse(
                        new ArrayList<>() {}
                );
        responseDto.setReleases(releases);
        responseDto.setReleasesCount(releases.size());


        long totalTimeSpentByRelease;
        for (ReleaseEntity release : releases) {

            totalTimeSpentByRelease = 0;
            for (TaskEntity task : release.getTasks()) {
                if (!task.isDeleted()){
                    long duration = taskService.countTaskTime(task);

                    task.setTimeSpent(String.format("%02d hrs, %02d min",
                TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration) -
                        TimeUnit.MILLISECONDS.toHours(duration) * 60));
                    totalTimeSpentByRelease += duration;
                }
            }
            List<List<TaskEntity>> unfinishedAndExpiredTasks =
                    taskService.findUnfinishedAndExpiredTasksByReleaseVersion(projectId, release.getVersion());

            unfinishedTasksCount += unfinishedAndExpiredTasks.get(0).size();
            expiredTasksCount += unfinishedAndExpiredTasks.get(1).size();
            responseDto.getUnfinishedTasks().add(unfinishedAndExpiredTasks.get(0)
                    .stream().map(taskMapper::taskEntityToTaskStatResponseDto).collect(Collectors.toList()));
            responseDto.getExpiredTasks().add(unfinishedAndExpiredTasks.get(1)
                    .stream().map(taskMapper::taskEntityToTaskStatResponseDto).collect(Collectors.toList()));
            totalTimeSpent += totalTimeSpentByRelease;
            String totalTimeSpentByReleaseString = String.format("Release version: %s - %02d hrs, %02d min",
                    release.getVersion(),
                    TimeUnit.MILLISECONDS.toHours(totalTimeSpentByRelease),
                    TimeUnit.MILLISECONDS.toMinutes(totalTimeSpentByRelease) -
                            TimeUnit.MILLISECONDS.toHours(totalTimeSpentByRelease) * 60);

            responseDto.getTotalTimeSpentByRelease().add(totalTimeSpentByReleaseString);
        }

        String totalTimeSpentString = String.format("%02d hrs, %02d min",
                TimeUnit.MILLISECONDS.toHours(totalTimeSpent),
                TimeUnit.MILLISECONDS.toMinutes(totalTimeSpent) -
                        TimeUnit.MILLISECONDS.toHours(totalTimeSpent) * 60
        );
        responseDto.setTotalTimeSpent(totalTimeSpentString);
        long averageTimeSpentOnTask;
        if (undeletedTasks.size() != 0) {
            averageTimeSpentOnTask = totalTimeSpent / undeletedTasks.size();
        } else {
            averageTimeSpentOnTask = 0;
        }
        String averageTimeSpentOnTaskString = String.format("%02d hrs, %02d min",
                    TimeUnit.MILLISECONDS.toHours(averageTimeSpentOnTask),
                    TimeUnit.MILLISECONDS.toMinutes(averageTimeSpentOnTask) -
                            TimeUnit.MILLISECONDS.toHours(averageTimeSpentOnTask) * 60
            );
        responseDto.setAverageTimeSpentOnTask(averageTimeSpentOnTaskString);
        responseDto.setExpiredTasksCount(expiredTasksCount);
        responseDto.setUnfinishedTasksCount(unfinishedTasksCount);

        return responseDto;
    }



    /**
     * Gets all the projects
     */
    @Override
    public List<ProjectEntity> getAll(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedProjectFilter");
        filter.setParameter("isDeleted", isDeleted);
        List<ProjectEntity> projects =  projectRepository.findAll();
        session.disableFilter("deletedProjectFilter");
        return projects;
    }

    /**
     * Finds a project by id
     * @param id Project id
     */
    @Override
    public ProjectEntity findById(Long id) throws NotFoundException{

        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("No such project with id: %d"), id)));

        if (project.isDeleted()) {
            throw new DeletedException(String.format("The project with id: %d has already been deleted", id));
        }
            return project;
        }


    /**
     * Deletes a project by id
     * @param id Project id
     */
    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        findById(id);
        projectRepository.deleteById(id);
    }


}
