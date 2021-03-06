package org.example.service;

import javassist.NotFoundException;
import org.example.dto.ProjectRequestDto;
import org.example.exception.InvalidStatusException;
import org.example.entity.ProjectEntity;

import java.util.List;

public interface ProjectService{

    void save(ProjectEntity projectEntity);

    void changeStatus(Long id) throws InvalidStatusException, NotFoundException;

    List<ProjectEntity> getAll();

    ProjectEntity findById(Long id) throws NotFoundException;

    void delete(Long id);

    boolean isProjectAvailableToChangeTaskStatus(Long id) throws NotFoundException, InvalidStatusException;

    ProjectEntity findByProjectName(String name) throws NotFoundException;

    void setUpRequestDto(ProjectRequestDto requestDto) throws NotFoundException;

    void ifProjectAvailableToCreateTaskOrThrowException(Long id) throws NotFoundException ;

    boolean ifProjectAvailableToCreateReleaseOrThrowException(Long id) throws NotFoundException;

    List<ProjectEntity> findAllByCustomerId(Long customerId);
}
