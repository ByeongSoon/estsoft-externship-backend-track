package com.shop.projectlion.api.health.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HealthCheckResponseDto {

    private boolean status;

    private String health;

    public static HealthCheckResponseDto createResponse() {
        return HealthCheckResponseDto.builder()
            .status(true)
            .health("ok - 포트폴리오")
            .build();
    }

}
