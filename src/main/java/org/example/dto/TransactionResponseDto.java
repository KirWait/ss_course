package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for showing json of transaction entity")
public class TransactionResponseDto {

    @Schema(description = "Field that stores amount of payment")
    private Long amount;

    @Schema(description = "Field that stores project id of payment")
    private Long projectId;

    @Schema(description = "Field that stores user id of payment")
    private Long userId;
}
