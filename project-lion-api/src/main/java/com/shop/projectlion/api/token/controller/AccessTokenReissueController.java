package com.shop.projectlion.api.token.controller;

import com.shop.projectlion.api.token.dto.AccessTokenDto;
import com.shop.projectlion.api.token.service.TokenReissueService;
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
@RequestMapping("/api/token")
public class AccessTokenReissueController {

    private final TokenReissueService tokenReissueService;

    private final HeaderValidator headerValidator;

    @PostMapping
    @ApiOperation(value = "액세스 토큰 재발급", notes = "리프레시 토큰을 통해서 액세스 토큰 재발급을 해주는 API")
    public ResponseEntity<AccessTokenDto> accessTokenReissue(@RequestHeader("Authorization") String authorization) throws BusinessException{
        headerValidator.check(authorization);

        AccessTokenDto accessTokenDto;

        try {
            accessTokenDto = tokenReissueService.getAccessTokenDto(authorization);
        } catch (BusinessException e) {
            throw new BusinessException(e.getStatus(),e.getMessage());
        }

        return ResponseEntity.ok(accessTokenDto);
    }

}
