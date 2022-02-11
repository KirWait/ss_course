package org.example.service;

import javassist.NotFoundException;
import org.example.dto.ProjectRequestDto;
import org.example.dto.ProjectStatisticsResponseDto;
import org.example.entity.ProjectEntity;
import org.example.exception.InvalidStatusException;
import org.example.exception.PageException;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface ProjectService{

    void save(ProjectEntity projectEntity);

    void changeStatus(Long id) throws InvalidStatusException, NotFoundException;

    Page<ProjectEntity> getAllByPage(int page, int pageSize, boolean isDeleted) throws PageException;

    List<ProjectEntity> getAll(boolean isDeleted);

    ProjectEntity findById(Long id) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    boolean isProjectAvailableToChangeTaskStatus(Long id) throws NotFoundException, InvalidStatusException;

    ProjectEntity findByProjectName(String name) throws NotFoundException;

    void setUpRequestDto(ProjectRequestDto requestDto) throws NotFoundException;

    void ifProjectAvailableToCreateTaskOrThrowException(Long id) throws NotFoundException ;

    boolean ifProjectAvailableToCreateReleaseOrThrowException(Long id) throws NotFoundException;

    List<ProjectEntity> findAllByCustomerId(Long customerId);

    ProjectStatisticsResponseDto getStatistic(Long projectId) throws NotFoundException, ParseException;
}
