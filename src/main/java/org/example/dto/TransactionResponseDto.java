package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for showing json of transaction entity")
public class TransactionResponseDto {

    @Schema(description = "Field that stores amount of payment")
    Long amount;

    @Schema(description = "Field that stores project id of payment")
    Long projectId;

    @Schema(description = "Field that stores user id of payment")
    Long userId;
}
