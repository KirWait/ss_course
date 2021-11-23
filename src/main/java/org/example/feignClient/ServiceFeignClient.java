package org.example.feignClient;

import org.example.dto.TransactionRequestDto;
import org.example.dto.TransactionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "TransactionService", url = "${feign.url}")
public interface ServiceFeignClient {

    @PostMapping("/pay/{projectId}")
    ResponseEntity<TransactionResponseDto> payForProject(@PathVariable(value = "projectId") Long projectId,
                                                                @RequestBody TransactionRequestDto requestDto);

    @GetMapping("/user_history/{userId}")
    ResponseEntity<List<TransactionResponseDto>> getTransactionHistory(@PathVariable(value = "userId") Long userId);

    @GetMapping("/project_history/{projectId}")
    Long getPaidSum(@PathVariable(value = "projectId") Long project_id);

}
