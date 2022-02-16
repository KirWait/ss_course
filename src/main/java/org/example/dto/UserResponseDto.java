package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enumeration.Active;
import org.example.enumeration.Roles;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for showing json of user entity")
public class UserResponseDto {

    @Schema(description = "Field that stores id of the user")
    private Long id;

    @Schema(description = "Field that stores username of the user")
    private String username;

    @JsonIgnore
    @Schema(description = "Field that stores password of the user")
    private String password;

    @Schema(description = "Field that stores roles of the user")
    private Roles roles;

    @JsonIgnore
    @Schema(description = "Field that stores active status of the user")
    private Active active;

}
