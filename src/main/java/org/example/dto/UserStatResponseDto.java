package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserStatResponseDto {
    String username;
    String totalTimeWorked;
    String averageTimeSpentOnTask;
    int tasksDoneCount;
    List<TaskStatResponseDto> tasksDone;
}
