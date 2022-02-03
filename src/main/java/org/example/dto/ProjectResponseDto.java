package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.UserEntity;
import org.example.enumeration.Status;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for showing json of project entity")
public class ProjectResponseDto {

    @Schema(description = "Field that stores id of the project")
    Long id;

    @Schema(description = "Field that stores name of the project")
    String name;

    @Schema(description = "Field that stores customer of the project")
    UserEntity customer;

    @Schema(description = "Field that stores status of the project")
    Status status;

    @Schema(description = "Field that stores information is project paid or not")
    boolean paid;

    @Schema(description = "Field that stores price of the project")
    Long price;

}
