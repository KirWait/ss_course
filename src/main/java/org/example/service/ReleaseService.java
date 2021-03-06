package org.example.service;

import javassist.NotFoundException;
import org.example.dto.ReleaseRequestDto;
import org.example.entity.ReleaseEntity;

import java.text.ParseException;

public interface ReleaseService {

    ReleaseEntity findByVersionAndProjectId(String version, Long projectId) throws NotFoundException;

    void delete(Long id);

    void save(ReleaseEntity version);

    void setUpRequestDto(ReleaseRequestDto version, Long projectId) throws ParseException, NotFoundException;
}
