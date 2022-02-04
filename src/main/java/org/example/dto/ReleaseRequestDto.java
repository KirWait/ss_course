package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.example.entity.ProjectEntity;
import org.example.entity.TaskEntity;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for storing json input for further transformation into release entity")
public class ReleaseRequestDto {

    @Schema(description = "Field that stores project of the release")
    private ProjectEntity project;

    @Schema(description = "Field that stores version of the release")
    private String version;

    @Schema(description = "Field that stores deadline of the release")
    private String endTime;

    @Schema(description = "Field that stores creation time of the release")
    private String creationTime;

    @Schema(description = "Field that stores id of the release")
    private Long id;

    @Schema(description = "Field that stores tasks of the release")
    @Singular
    private List<TaskEntity> tasks;

}
