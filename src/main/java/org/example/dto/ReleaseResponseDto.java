package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.ProjectEntity;
import org.example.entity.TaskEntity;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for showing json of release entity")
public class ReleaseResponseDto {

    @JsonIgnore
    @Schema(description = "Field that stores tasks of the release")
    private List<TaskEntity> tasks;

    @Schema(description = "Field that stores version of the release")
    private String version;

    @Schema(description = "Field that stores dead line of the release")
    private String endTime;

    @Schema(description = "Field that stores creation time of the release")
    private String creationTime;

    @Schema(description = "Field that stores id of the release")
    private Long id;

    @Schema(description = "Field that stores project of the release")
    private ProjectEntity project;

}
