package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.entity.ReleaseEntity;
import org.example.entity.UserEntity;
import java.util.ArrayList;
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
    private List<String> totalTimeSpentByRelease = new ArrayList<>();
    private int expiredTasksCount;

    private List<List<TaskStatResponseDto>> expiredTasks = new ArrayList<>();
    private int unfinishedTasksCount;
    private List<List<TaskStatResponseDto>> unfinishedTasks = new ArrayList<>();
    private int deletedTasksCount;
    private List<TaskStatResponseDto> deletedTasks;

}
