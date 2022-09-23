package com.shop.projectlion.web.kakaotoken.controller;

import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenResponseDto;
import com.shop.projectlion.web.kakaotoken.service.KakaoTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class KakaoTokenController {

    private final KakaoTokenService kakaoTokenService;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Value("${kakao.client.id}")
    private String clientId;

    @GetMapping("/login")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String loginCallback(String code){
        KakaoTokenResponseDto kakaoToken = kakaoTokenService.getKakaoTokenDto(code, clientId, clientSecret);
        return "kakao token : " + kakaoToken;
    }

}