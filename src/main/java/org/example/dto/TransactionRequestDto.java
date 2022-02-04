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
@Schema(description = "DTO for storing json input for further transformation into transaction entity after sending request via feign client")
public class TransactionRequestDto {

    @Schema(description = "Field that stores amount of payment")
    private Long amount;

    @Schema(description = "Field that stores project id of transaction")
    private Long projectId;

    @Schema(description = "Field that stores user id of transaction")
    private Long userId;

}
