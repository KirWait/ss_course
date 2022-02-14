package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Schema(description = "DTO for showing json of task entity")
public class TaskResponseDto {

    @Schema(description = "Field that stores id of the task")
    private Long id;

    @Schema(description = "Field that stores project of the task")
    @JsonIgnore
    private ProjectEntity project;

    @Schema(description = "Field that stores status of the task")
    private Status status;

    @Schema(description = "Field that stores name of the task")
    private String name;

    @Schema(description = "Field that stores description of the task")
    private String description;

    @JsonIgnore
    @Schema(description = "Field that stores author of the task")
    private UserEntity author;

    @JsonIgnore
    @Schema(description = "Field that stores responsible of the task")
    private UserEntity responsible;

    @Schema(description = "Field that stores release of the task")
    private ReleaseEntity release;

    @Schema(description = "Field that stores type of the task")
    private Type type;

    @Schema(description = "Field that stores start time of the task")
    private Date startTime;

    @Schema(description = "Field that stores end time of the task")
    private Date endTime;

    @Schema(description = "Field that stores creation time of the task")
    private Date creationTime;
}
