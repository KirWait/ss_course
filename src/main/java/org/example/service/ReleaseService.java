package org.example.service;

import javassist.NotFoundException;
import org.example.dto.ReleaseRequestDto;
import org.example.entity.ReleaseEntity;

import java.text.ParseException;
import java.util.List;

public interface ReleaseService {

    ReleaseEntity findByVersionAndProjectId(String version, Long projectId) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    List<ReleaseEntity> getAll(boolean isDeleted);

    void save(ReleaseEntity version);

    void setUpRequestDto(ReleaseRequestDto version, Long projectId) throws ParseException, NotFoundException;
}
