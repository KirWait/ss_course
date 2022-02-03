package org.example.service.impl;

import javassist.NotFoundException;
import org.example.dto.ReleaseRequestDto;
import org.example.entity.ProjectEntity;
import org.example.entity.ReleaseEntity;
import org.example.exception.InvalidDateFormatException;
import org.example.repository.ProjectRepository;
import org.example.service.DateFormatConstants;
import org.example.repository.ReleaseRepository;
import org.example.service.ReleaseService;
import org.example.translator.TranslationService;
import org.springframework.stereotype.Service;
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

    private static final String CORRECT_DATE_REGEX = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";

    private final ReleaseRepository releaseRepository;
    private final TranslationService translationService;
    private final ProjectRepository projectService;


    public ReleaseServiceImpl(ReleaseRepository releaseRepository, TranslationService translationService,
                              ProjectRepository projectService) {
        this.releaseRepository = releaseRepository;
        this.translationService = translationService;
        this.projectService = projectService;
    }


    /**
     * Finds release by version and project id
     * @param version Version of the release
     * @param projectId Project id
     *
     */
    @Override
    public ReleaseEntity findByVersionAndProjectId(String version, Long projectId) throws NotFoundException {

        return releaseRepository.findByVersionAndProjectId(version, projectId)
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
    public void delete(Long id){
        releaseRepository.deleteById(id);
//        logger.info(String.format("Successfully deleted release with id: %d to the database", id));
    }


    /**
     * Saves release using JPA Repository
     * @param releaseEntity Entity of release
     */
    @Override
    @Transactional
    public void save(ReleaseEntity releaseEntity) {
        releaseRepository.save(releaseEntity);
//        logger.info("Successfully saved release to the database");
    }

    /**
     * Initializing some fields that shouldn't be defined manually(example: creationTime)
     * @param projectId Project id
     * @param releaseRequestDto Json from HTTP request that mapped into request dto
     */
    @Override
    public void setUpRequestDto(ReleaseRequestDto releaseRequestDto, Long projectId) throws ParseException, NotFoundException {

        List<ReleaseEntity> currentProjectReleases = releaseRepository.findAllByProjectIdOrderByCreationTime(projectId)
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("Project with id: %d have no releases"), projectId)));

        if (releaseRequestDto.getEndTime() == null){
            throw new IllegalArgumentException(
                    translationService.getTranslation("Enter the end date!"));
        }
        if (!releaseRequestDto.getEndTime().matches(CORRECT_DATE_REGEX)) {
            throw new InvalidDateFormatException(
                    translationService.getTranslation("The date should be in 'yyyy-mm-dd' format!"));
        }
        if (!currentProjectReleases.isEmpty()){
            long lastReleaseEndTimeInMillis = DateFormatConstants.formatterWithoutTime
                    .parse(currentProjectReleases.get(currentProjectReleases.size() - 1).getEndTime()).getTime();

            long requestDtoEndTimeInMillis = DateFormatConstants.formatterWithoutTime
                    .parse(releaseRequestDto.getEndTime()).getTime();

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

        releaseRequestDto.setCreationTime(DateFormatConstants.formatterWithTime.format(new GregorianCalendar().getTime()));
        ProjectEntity project = projectService.findById(projectId).orElseThrow(() -> new NotFoundException("No such project with id: %d"));
        releaseRequestDto.setProject(project);

    }
}
