package com.shop.projectlion.api.health.service;

import com.shop.projectlion.api.health.dto.HealthCheckResponseDto;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    public HealthCheckResponseDto healthCheck() {
        return HealthCheckResponseDto.createResponse();
    }

}
