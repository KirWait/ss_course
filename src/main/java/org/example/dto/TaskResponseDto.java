package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.ProjectEntity;
import org.example.entity.ReleaseEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Status;
import org.example.enumeration.Type;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for showing json of task entity")
public class TaskResponseDto {

    @Schema(description = "Field that stores id of the task")
    Long id;

    @Schema(description = "Field that stores project of the task")
    ProjectEntity project;

    @Schema(description = "Field that stores status of the task")
    Status status;

    @Schema(description = "Field that stores name of the task")
    String name;

    @Schema(description = "Field that stores description of the task")
    String description;

    @JsonIgnore
    @Schema(description = "Field that stores author of the task")
    UserEntity author;

    @JsonIgnore
    @Schema(description = "Field that stores responsible of the task")
    UserEntity responsible;

    @Schema(description = "Field that stores release of the task")
    ReleaseEntity release;

    @Schema(description = "Field that stores type of the task")
    Type type;

    @Schema(description = "Field that stores start time of the task")
    String startTime;

    @Schema(description = "Field that stores end time of the task")
    String endTime;

    @Schema(description = "Field that stores creation time of the task")
    String creationTime;
}
