package com.shop.projectlion.api.health.client;

import com.shop.projectlion.api.health.dto.HealthCheckResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "http://localhost:8080", name = "healthFeignClient")
public interface HealthFeignClient {

    @GetMapping(value = "/api/health", consumes = "application/json")
    ResponseEntity<HealthCheckResponseDto> getServerStatus();

}
