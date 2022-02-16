package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageableResponseDto<T> {

    @Schema(description = "Max page field")
    private int maxPage;
    @Schema(description = "Max page field")
    private int totalFound;
    @Schema(description = "Result list field")
    private List<T> resultList;


}
