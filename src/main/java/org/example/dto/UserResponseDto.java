package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Schema(description = "DTO for showing json of user entity")
public class UserResponseDto {

    @Schema(description = "Field that stores id of the user")
    Long id;

    @Schema(description = "Field that stores username of the user")
    String username;

    @JsonIgnore
    @Schema(description = "Field that stores password of the user")
    String password;

    @Schema(description = "Field that stores roles of the user")
    Roles roles;

    @JsonIgnore
    @Schema(description = "Field that stores active status of the user")
    Active active;

}
