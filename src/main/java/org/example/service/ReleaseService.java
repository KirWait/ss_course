package org.example.service;

import org.example.dto.version.ReleaseRequestDto;
import org.example.entity.ReleaseEntity;

import java.text.ParseException;

public interface ReleaseService {

    ReleaseEntity findByVersionAndProjectId(String version, Long projectId);

    void save(ReleaseEntity version);

    void setUpRequestDto(ReleaseRequestDto version, Long projectId) throws ParseException;
}
