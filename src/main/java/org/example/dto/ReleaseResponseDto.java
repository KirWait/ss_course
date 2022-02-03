package org.example.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.ProjectEntity;
import org.example.entity.TaskEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for showing json of release entity")
public class ReleaseResponseDto {

    @JsonIgnore
    @Schema(description = "Field that stores tasks of the release")
    List<TaskEntity> tasks;

    @Schema(description = "Field that stores version of the release")
    String version;

    @Schema(description = "Field that stores dead line of the release")
    String endTime;

    @Schema(description = "Field that stores creation time of the release")
    String creationTime;

    @Schema(description = "Field that stores id of the release")
    Long id;

    @Schema(description = "Field that stores project of the release")
    ProjectEntity project;

}
