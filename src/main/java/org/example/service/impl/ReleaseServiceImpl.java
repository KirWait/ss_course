package org.example.service.impl;

import javassist.NotFoundException;
import org.example.dto.ReleaseRequestDto;
import org.example.entity.ProjectEntity;
import org.example.entity.ReleaseEntity;
import org.example.exception.DeletedException;
import org.example.repository.ProjectRepository;
import org.example.repository.ReleaseRepository;
import org.example.service.ReleaseService;
import org.example.translator.TranslationService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

/**
 *
 * This is the class that implements business-logic of releases in this app.
 * @author Kirill Zhdanov
 */
@Service
public class ReleaseServiceImpl implements ReleaseService {


    private final ReleaseRepository releaseRepository;
    private final TranslationService translationService;
    private final ProjectRepository projectRepository;


    public ReleaseServiceImpl(ReleaseRepository releaseRepository, TranslationService translationService,
                              ProjectRepository projectRepository) {
        this.releaseRepository = releaseRepository;
        this.translationService = translationService;
        this.projectRepository = projectRepository;
    }


    /**
     * Finds release by version and project id
     * @param version Version of the release
     * @param projectId Project id
     *
     */
    @Override
    public ReleaseEntity findByVersionAndProjectId(String version, Long projectId) throws NotFoundException {

        return releaseRepository.findByVersionAndProjectIdAndDeleted(version, projectId, false)
                .orElseThrow(() -> new NotFoundException(
                        String.format(translationService.getTranslation(
                                "No such release with version: %s, and project id: %d!"), version, projectId))
                );
    }


    /**
     * Deletes release by id
     * @param id Release id
     */
    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        releaseRepository.findById(id).orElseThrow(()->
                new NotFoundException(String.format("No such release with id: %d", id)));
        releaseRepository.deleteById(id);
    }


    /**
     * Saves release using JPA Repository
     * @param releaseEntity Entity of release
     */
    @Override
    @Transactional
    public void save(ReleaseEntity releaseEntity) {
        releaseRepository.save(releaseEntity);
    }

    /**
     * Initializing some fields that shouldn't be defined manually(example: creationTime)
     * @param projectId Project id
     * @param releaseRequestDto Json from HTTP request that mapped into request dto
     */
    @Override
    public void setUpRequestDto(ReleaseRequestDto releaseRequestDto, Long projectId)
            throws ParseException, NotFoundException {

        List<ReleaseEntity> currentProjectReleases =
                releaseRepository.findAllByProjectIdAndDeletedOrderByCreationTime(projectId, false)
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("Project with id: %d have no releases"), projectId)));

        if (releaseRequestDto.getEndTime() == null){
            throw new IllegalArgumentException(
                    translationService.getTranslation("Enter the end date!"));
        }
        if (!currentProjectReleases.isEmpty()){
            long lastReleaseEndTimeInMillis = currentProjectReleases.get(currentProjectReleases.size() - 1).getEndTime().getTime();

            long requestDtoEndTimeInMillis = releaseRequestDto.getEndTime().getTime();

            long requestDtoStartTimeInMillis = new GregorianCalendar().getTimeInMillis();

            if (!(lastReleaseEndTimeInMillis < requestDtoStartTimeInMillis && requestDtoStartTimeInMillis < requestDtoEndTimeInMillis)){
                throw new IllegalArgumentException(
                        translationService.getTranslation("You shouldn't enter past dates or current release haven't finished!"));
            }
        }
        if (releaseRequestDto.getVersion() == null){
            throw new IllegalArgumentException("Enter the version!");
        }

        String releaseRequestDtoVersion = releaseRequestDto.getVersion();

        if (currentProjectReleases.stream().anyMatch(entity -> Objects.equals(entity.getVersion(), releaseRequestDtoVersion))){
            throw new IllegalArgumentException(String.format(translationService.getTranslation(
                            "Project with id: %d already have %s version!"), projectId, releaseRequestDtoVersion));
        }

        releaseRequestDto.setCreationTime(new GregorianCalendar().getTime());
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() ->
                new NotFoundException(String.format("No such project with id: %d", projectId)));
        if (project.isDeleted()){
            throw new DeletedException(String.format("The project with id: %d has already been deleted!", projectId));
        }
        releaseRequestDto.setProject(project);

    }
}
