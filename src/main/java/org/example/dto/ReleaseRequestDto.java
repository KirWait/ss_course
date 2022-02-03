package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.ProjectEntity;
import org.example.entity.TaskEntity;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for storing json input for further transformation into release entity")
public class ReleaseRequestDto {

    @Schema(description = "Field that stores project of the release")
    ProjectEntity project;

    @Schema(description = "Field that stores version of the release")
    String version;

    @Schema(description = "Field that stores deadline of the release")
    String endTime;

    @Schema(description = "Field that stores creation time of the release")
    String creationTime;

    @Schema(description = "Field that stores id of the release")
    Long id;

    @Schema(description = "Field that stores tasks of the release")
    @Singular
    List<TaskEntity> tasks;

}
