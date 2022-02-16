package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.ProjectEntity;
import org.example.entity.ReleaseEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Status;
import org.example.enumeration.Type;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for storing json input for further transformation into task entity")
public class TaskRequestDto {

    @Schema(description = "Field that stores author username of the task")
    private String authorUsername;

    @Schema(description = "Field that stores responsible username of the task")
    private String responsibleUsername;

    @Schema(description = "Field that stores id of the task")
    private Long id;

    @Schema(description = "Field that stores project of the task")
    private ProjectEntity project;

    @Schema(description = "Field that stores status of the task")
    private Status status;

    @Schema(description = "Field that stores name of the task")
    private String name;

    @Schema(description = "Field that stores description of the task")
    private String description;

    @Schema(description = "Field that stores author of the task")
    private UserEntity author;

    @Schema(description = "Field that stores responsible of the task")
    private UserEntity responsible;

    @Schema(description = "Field that stores release of the task")
    private ReleaseEntity release;

    @Schema(description = "Field that stores type of the task")
    private Type type;

    @Schema(description = "Field that stores release version of the release of the task")
    private String releaseVersion;

    @Schema(description = "Field that stores release start time of the release of the task")
    private Date startTime;

    @Schema(description = "Field that stores release end time of the release of the task")
    private Date endTime;

    @Schema(description = "Field that stores release creation time of the release of the task")
    private Date creationTime;

}

