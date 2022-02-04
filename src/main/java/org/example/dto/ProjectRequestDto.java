package org.example.dto;

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
@Schema(description = "DTO for storing json input for further transformation into project entity")
public class ProjectRequestDto {

    @Schema(description = "Field for defining customer by login")
    private String customerName;

    @Schema(description = "Field that stores name of the project")
    private String name;

    @Schema(description = "Field that stores id of the project")
    private Long id;

    @Schema(description = "Field that stores customer of the project")
    private UserEntity customer;

    @Schema(description = "Field that stores status of the project")
    private Status status;

    @Schema(description = "Field that stores information is project paid or not")
    private boolean paid;

    @Schema(description = "Field that stores price of the project")
    private Long price;
}
