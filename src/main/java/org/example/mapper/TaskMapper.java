package org.example.mapper;

import org.example.dto.TaskRequestDto;
import org.example.dto.TaskResponseDto;
import org.example.dto.TaskStatResponseDto;
import org.example.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface TaskMapper {
    TaskEntity taskRequestDtoToTaskEntity(TaskRequestDto requestDto);
    TaskResponseDto taskEntityToTaskResponseDto(TaskEntity entity);

    @Mappings({
    @Mapping(source = "release.version", target = "releaseVersion"),
    @Mapping(source = "responsible.username", target = "responsibleUsername"),
    })
    TaskStatResponseDto taskEntityToTaskStatResponseDto(TaskEntity entity);

//    @AfterMapping
//    @Named("timeSpent")
//    default void setBookAuthor(@MappingTarget TaskStatResponseDto responseDto, TaskEntity task) throws ParseException {
//        if (task.getStartTime() == null) {
//            responseDto.setTimeSpent("00 hrs, 00 min");
//            return;
//        }
//        long start = formatterWithTime.parse(task.getStartTime()).getTime();
//        long end;
//        if (task.getEndTime() == null) {
//            end = System.currentTimeMillis();
//        } else {
//            end = formatterWithTime.parse(task.getEndTime()).getTime();
//        }
//        long duration = end - start;
//        responseDto.setTimeSpent(String.format("%02d hrs, %02d min",
//                TimeUnit.MILLISECONDS.toHours(duration),
//                TimeUnit.MILLISECONDS.toMinutes(duration) -
//                        TimeUnit.MILLISECONDS.toHours(duration) * 60
//        ));
//    }
//    @Named("responsibleToResponsibleUsername")
//    static String responsibleToResponsibleUsername(UserEntity responsible){
//        return responsible.getUsername();
//    }

//    @Named("releaseToReleaseVersion")
//    static String releaseToReleaseVersion(ReleaseEntity release){
//        return release.getVersion();
//    }

//    @Named("startTimeAndEndTimeToTimeSpent")
//    static String startTimeAndEndTimeToTimeSpent(String startTime, String endTime) throws ParseException {
//
//    }

}
