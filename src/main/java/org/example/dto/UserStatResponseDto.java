package org.example.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserStatResponseDto {
    String username;
    String totalTimeWorked;
    String averageTimeSpentOnTask;
    int tasksDoneCount;
    List<TaskStatResponseDto> tasksDone;
}
