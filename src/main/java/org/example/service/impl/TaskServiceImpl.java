package org.example.service.impl;

import javassist.NotFoundException;
import org.example.dto.task.TaskRequestDto;
import org.example.entity.ReleaseEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Type;
import org.example.exception.InvalidStatusException;
import org.example.entity.TaskEntity;
import org.example.enumeration.Status;
import org.example.repository.TaskRepository;
import org.example.service.DateFormatter;
import org.example.service.ReleaseService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ReleaseService releaseService;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, ReleaseService releaseService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.releaseService = releaseService;
    }

    @Override
    public void save(TaskEntity taskEntity) {
        taskRepository.save(taskEntity);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void changeStatus(Long id) throws NotFoundException {
        TaskEntity task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("No such task with id: %d!", id)));

        if (task.getStatus() == Status.DONE) {

            throw new InvalidStatusException("The task has already been done!");
        }
        if (task.getStatus() == Status.IN_PROGRESS) {

            task.setEndTime(DateFormatter.formatterWithTime.format(new GregorianCalendar().getTime()));

            task.setStatus(Status.DONE);

        }
        if (task.getStatus() == Status.BACKLOG) {

            task.setStartTime(DateFormatter.formatterWithTime.format(new GregorianCalendar().getTime()));

            task.setStatus(Status.IN_PROGRESS);
        }

        taskRepository.save(task);
    }

    @Override
    public List<TaskEntity> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<TaskEntity> getAllByProjectId(Long project_id) {
        return taskRepository.findAllByProjectId(project_id);
    }

    @Override
    public TaskEntity findById(Long id) throws NotFoundException {

        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("No such task with id: %d!", id)));
    }

    @Override
    public boolean checkForTasksInProgressAndBacklog(Long projectId){

         return taskRepository.findAllByProjectId(projectId).stream().allMatch(
                 task -> (task.getStatus() == Status.DONE));
    }

    @Override
    public void setUpRequestDto(TaskRequestDto requestDto, Long projectId) throws NotFoundException,IllegalArgumentException{

        if (requestDto.getCreationTime() != null){
            throw new IllegalArgumentException("Creation time shouldn't be defined manually!");
        }
        if (requestDto.getStartTime() != null){
            throw new IllegalArgumentException("Start time shouldn't be defined manually!");
        }
        if (requestDto.getEndTime() != null){
            throw new IllegalArgumentException("End time shouldn't be defined manually!");
        }
        if (requestDto.getReleaseVersion() == null){
            throw new IllegalArgumentException("Define the release!");
        }
        if (requestDto.getName() == null){
            throw new IllegalArgumentException("Enter the name of the task!");
        }
        if (requestDto.getType() == null){
            throw new IllegalArgumentException(String.format("Define the type of the task! Possible types: %s",Arrays.toString(Type.values())));
        }
        if (requestDto.getResponsibleUsername() == null) {
            throw new IllegalArgumentException("Enter the username of the responsible person!");
        }
        else {
            requestDto.setResponsibleId(userService.findByUsername(requestDto.getResponsibleUsername()).getId());
        }

        requestDto.setCreationTime(DateFormatter.formatterWithTime.format(Calendar.getInstance().getTime()));

        String currentSessionUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity currentSessionUser = userService.findByUsername(currentSessionUserName);

        requestDto.setAuthorId(currentSessionUser.getId());

        requestDto.setProjectId(projectId);

        requestDto.setStatus(Status.BACKLOG);

        ReleaseEntity currentRelease = releaseService.findByVersionAndProjectId(requestDto.getReleaseVersion(), projectId);

        requestDto.setRelease(currentRelease);
    }

    @Override
    public List<TaskEntity> searchByFilter(TaskRequestDto filterDto) throws NotFoundException {

        List<TaskEntity> result = taskRepository.findAll();

        if (filterDto.getAuthorUsername() != null) {
            UserEntity author = userService.findByUsername(filterDto.getAuthorUsername());
            result = result.stream().filter(task -> Objects.equals(task.getAuthorId(), author.getId())).collect(Collectors.toList());
        }
        if (filterDto.getResponsibleUsername() != null) {
            UserEntity responsible = userService.findByUsername(filterDto.getResponsibleUsername());
            result = result.stream().filter(task -> Objects.equals(task.getResponsibleId(), responsible.getId())).collect(Collectors.toList());
        }
        if (filterDto.getId() != null){
           result = taskRepository.findAll().stream().filter(task -> task.getId().toString().contains(filterDto.getId().toString())).collect(Collectors.toList());
        }
        if (filterDto.getStatus() != null){
            result = result.stream().filter(task -> task.getStatus() == filterDto.getStatus()).collect(Collectors.toList());
        }
        if (filterDto.getType() != null){
            result = result.stream().filter(task -> task.getType() == filterDto.getType()).collect(Collectors.toList());
        }
        if (filterDto.getAuthorId() != null){
            result = result.stream().filter(task -> task.getAuthorId().toString().contains(filterDto.getAuthorId().toString())).collect(Collectors.toList());
        }
        if (filterDto.getResponsibleId() != null){
            result = result.stream().filter(task -> task.getResponsibleId().toString().contains(filterDto.getResponsibleId().toString())).collect(Collectors.toList());
        }
        if (filterDto.getProjectId() != null){
            result = result.stream().filter(task -> task.getProjectId().toString().contains(filterDto.getProjectId().toString())).collect(Collectors.toList());
        }
        if (filterDto.getName() != null){
            result = result.stream().filter(task -> task.getName().contains(filterDto.getName())).collect(Collectors.toList());
        }
        if (filterDto.getDescription() != null){
            result = result.stream().filter(task -> task.getDescription().contains(filterDto.getDescription())).collect(Collectors.toList());
        }
        if (filterDto.getReleaseVersion() != null){
            System.out.println("Not null");
            result = result.stream().filter(
                    task -> task.getRelease().getVersion().contains(filterDto.getReleaseVersion())).collect(Collectors.toList());
        }
        if (filterDto.getStartTime() != null){
            result = result.stream().filter(
                    task -> task.getStartTime().contains(filterDto.getStartTime())).collect(Collectors.toList());
        }
        if (filterDto.getCreationTime() != null){
            result = result.stream().filter(
                    task -> task.getCreationTime().contains(filterDto.getCreationTime())).collect(Collectors.toList());
        }
        if (filterDto.getEndTime() != null){
            result = result.stream().filter(
                    task -> task.getEndTime().contains(filterDto.getEndTime())).collect(Collectors.toList());
        }

        return result;
    }


}
