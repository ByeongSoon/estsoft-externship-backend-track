package com.shop.projectlion.api.logout.controller;

import com.shop.projectlion.api.logout.service.LogoutService;
import com.shop.projectlion.global.error.HeaderValidator;
import com.shop.projectlion.global.error.exception.BusinessException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shop.projectlion.global.error.exception.ErrorCode.NOT_EXISTS_AUTHORIZATION;
import static com.shop.projectlion.global.error.exception.ErrorCode.NOT_VALID_BEARER_GRANT_TYPE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logout")
public class LogoutController {

    private final LogoutService logoutService;

    private final HeaderValidator headerValidator;

    @PostMapping
    @ApiOperation(value = "로그아웃", notes = "액세스 토큰을 기반으로 로그아웃 진행, 리프레시 토큰 만료 처리")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorization) {
        headerValidator.check(authorization);

        return ResponseEntity.ok(logoutService.logout(authorization));
    }

}
