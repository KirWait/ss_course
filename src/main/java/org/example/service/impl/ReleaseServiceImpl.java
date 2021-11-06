package org.example.service.impl;

import org.example.dto.version.ReleaseRequestDto;
import org.example.entity.ReleaseEntity;
import org.example.exception.InvalidDateFormatException;
import org.example.service.DateFormatter;
import org.example.repository.ReleaseRepository;
import org.example.service.ReleaseService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;


@Service
@Transactional
public class ReleaseServiceImpl implements ReleaseService {

    private final ReleaseRepository releaseRepository;

    public ReleaseServiceImpl(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }

    @Override
    public ReleaseEntity findByVersionAndProjectId(String version, Long projectId) {
        return releaseRepository.findByVersionAndProjectId(version, projectId);
    }

    @Override
    public void save(ReleaseEntity version) {
        releaseRepository.save(version);
    }

    @Override
    public void setUpRequestDto(ReleaseRequestDto releaseRequestDto, Long projectId) throws ParseException {

        List<ReleaseEntity> currentProjectReleases = releaseRepository.findAllByProjectIdOrderByCreationTime(projectId);

        if (releaseRequestDto.getEndTime() == null){
            throw new IllegalArgumentException("Enter the end date!");
        }
        if (!releaseRequestDto.getEndTime().matches("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$")) {
            throw new InvalidDateFormatException("The date should be is 'yyyy-mm-dd' format!");
        }
        if (!currentProjectReleases.isEmpty()){
            long lastReleaseEndTimeInMillis = DateFormatter.formatterWithoutTime.parse(currentProjectReleases.get(currentProjectReleases.size() - 1).getEndTime()).getTime();

            long requestDtoEndTimeInMillis = DateFormatter.formatterWithoutTime.parse(releaseRequestDto.getEndTime()).getTime();

            long requestDtoStartTimeInMillis = new GregorianCalendar().getTimeInMillis();

            if (!(lastReleaseEndTimeInMillis < requestDtoStartTimeInMillis && requestDtoStartTimeInMillis < requestDtoEndTimeInMillis)){
                throw new IllegalArgumentException("You shouldn't enter past dates or current release haven't finished! ");
            }
        }
        if (releaseRequestDto.getVersion() == null){
            throw new IllegalArgumentException("Enter the version!");
        }

        String releaseRequestDtoVersion = releaseRequestDto.getVersion();

        if (currentProjectReleases.stream().anyMatch(entity -> Objects.equals(entity.getVersion(), releaseRequestDtoVersion))){
            throw new IllegalArgumentException(String.format("Project with id: %d already have %s version!", projectId, releaseRequestDtoVersion));
        }

        releaseRequestDto.setCreationTime(DateFormatter.formatterWithTime.format(new GregorianCalendar().getTime()));

        releaseRequestDto.setProjectId(projectId);
    }
}
