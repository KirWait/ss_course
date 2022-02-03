package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for storing json input for further transformation into transaction entity after sending request via feign client")
public class TransactionRequestDto {

    @Schema(description = "Field that stores amount of payment")
    Long amount;

    @Schema(description = "Field that stores project id of transaction")
    Long projectId;

    @Schema(description = "Field that stores user id of transaction")
    Long userId;

}
