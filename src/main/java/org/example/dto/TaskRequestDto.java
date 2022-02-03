package org.example.dto;

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
@Schema(description = "DTO for storing json input for further transformation into task entity")
public class TaskRequestDto {

    @Schema(description = "Field that stores author username of the task")
    String authorUsername;

    @Schema(description = "Field that stores responsible username of the task")
    String responsibleUsername;

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

    @Schema(description = "Field that stores author of the task")
    UserEntity author;

    @Schema(description = "Field that stores responsible of the task")
    UserEntity responsible;

    @Schema(description = "Field that stores release of the task")
    ReleaseEntity release;

    @Schema(description = "Field that stores type of the task")
    Type type;

    @Schema(description = "Field that stores release version of the release of the task")
    String releaseVersion;

    @Schema(description = "Field that stores release start time of the release of the task")
    String startTime;

    @Schema(description = "Field that stores release end time of the release of the task")
    String endTime;

    @Schema(description = "Field that stores release creation time of the release of the task")
    String creationTime;

}

