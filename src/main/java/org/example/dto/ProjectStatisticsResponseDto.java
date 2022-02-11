package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.ReleaseEntity;
import org.example.entity.UserEntity;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatisticsResponseDto {

    private UserEntity customer;
    private int releasesCount;
    @JsonIgnore
    private List<ReleaseEntity> releases;
    private int tasksCount;
    private String totalTimeSpent;
    private String averageTimeSpentOnTask;
    private List<String> totalTimeSpentByRelease;
    private int expiredTasksCount;

    private List<TaskStatResponseDto> expiredTasks;
    private int unfinishedTasksCount;
    private List<TaskStatResponseDto> unfinishedTasks;
    private int deletedTasksCount;
    private List<TaskStatResponseDto> deletedTasks;

}
