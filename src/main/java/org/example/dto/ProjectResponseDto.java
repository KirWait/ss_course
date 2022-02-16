package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.UserEntity;
import org.example.enumeration.Status;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "DTO for showing json of project entity")
public class ProjectResponseDto {

    @Schema(description = "Field that stores id of the project")
    private Long id;

    @Schema(description = "Field that stores name of the project")
    private String name;

    @JsonIgnore
    @Schema(description = "Field that stores customer of the project")
    private UserEntity customer;

    @Schema(description = "Field that stores status of the project")
    private Status status;

    @Schema(description = "Field that stores information is project paid or not")
    private boolean paid;

    @Schema(description = "Field that stores price of the project")
    private Long price;

}
