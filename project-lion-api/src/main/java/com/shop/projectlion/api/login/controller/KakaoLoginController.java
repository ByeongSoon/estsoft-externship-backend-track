package com.shop.projectlion.api.login.controller;

import com.shop.projectlion.api.login.dto.LoginRequestDto;
import com.shop.projectlion.api.login.service.KakaoLoginService;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.global.error.HeaderValidator;
import com.shop.projectlion.global.error.exception.BusinessException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.shop.projectlion.global.error.exception.ErrorCode.NOT_EXISTS_AUTHORIZATION;
import static com.shop.projectlion.global.error.exception.ErrorCode.NOT_VALID_BEARER_GRANT_TYPE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class KakaoLoginController {

    private final KakaoLoginService loginService;

    private final HeaderValidator headerValidator;

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "기존 회원이라면 로그인, 아니라면 회원가입을 수행하는 API")
    public ResponseEntity<TokenDto> login(@RequestHeader("Authorization") String authorization, @RequestBody LoginRequestDto loginRequestDto) {
        headerValidator.check(authorization);

        TokenDto tokenDto = loginService.registeredLogin(authorization, loginRequestDto);

        return ResponseEntity.ok(tokenDto);
    }

}
