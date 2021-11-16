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

@FeignClient(name = "TransactionService", url = "http://localhost:8075")
public interface ServiceFeignClient {

    @PostMapping("/pay/{projectId}")
    public ResponseEntity<TransactionResponseDto> payForProject(@PathVariable(value = "projectId") Long projectId,
                                                                @RequestBody TransactionRequestDto requestDto);

    @GetMapping("/user_history/{userId}")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionHistory(@PathVariable(value = "userId") Long userId);

    @GetMapping("/project_history/{projectId}")
    public Long getPaidSum(@PathVariable(value = "projectId") Long project_id);
}
