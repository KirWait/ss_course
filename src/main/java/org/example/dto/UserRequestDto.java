package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.enumeration.Active;
import org.example.enumeration.Roles;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for storing json input for further transformation into user entity")
public class UserRequestDto {

    @Schema(description = "Field that stores id of the user")
    Long id;

    @Schema(description = "Field that stores username of the user")
    String username;

    @Schema(description = "Field that stores password of the user")
    String password;

    @Schema(description = "Field that stores roles of the user")
    Roles roles;

    @Schema(description = "Field that stores active status of the user")
    Active active;

}
