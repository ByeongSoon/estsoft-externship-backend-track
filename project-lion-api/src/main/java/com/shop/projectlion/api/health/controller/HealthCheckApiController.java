package com.shop.projectlion.api.health.controller;

import com.shop.projectlion.api.health.client.HealthFeignClient;
import com.shop.projectlion.api.health.dto.HealthCheckResponseDto;
import com.shop.projectlion.api.health.service.HealthCheckService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/health")
public class HealthCheckApiController {

    private final HealthCheckService healthCheckService;

    private final HealthFeignClient healthFeignClientTest;

    @GetMapping
    @ApiOperation(value = "서버 Health Check", notes = "서버의 Health Check를 위한 API")
    public ResponseEntity<HealthCheckResponseDto> healthCheck() {
        return ResponseEntity.ok(healthCheckService.healthCheck());
    }


    @GetMapping("/feign")
    public ResponseEntity<HealthCheckResponseDto> healthCheckFeignTest() {
        return healthFeignClientTest.getServerStatus();
    }

}
