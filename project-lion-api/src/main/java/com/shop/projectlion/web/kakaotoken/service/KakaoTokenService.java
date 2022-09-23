package com.shop.projectlion.web.kakaotoken.service;

import com.shop.projectlion.web.kakaotoken.client.KakaoFeignClient;
import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenRequestDto;
import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class KakaoTokenService {

    private final KakaoFeignClient kakaoFeignClient;

    public KakaoTokenResponseDto getKakaoTokenDto(String code, String clientId, String clientSecret) {
        KakaoTokenRequestDto kakaoTokenRequest = KakaoTokenRequestDto.of(code, clientId, clientSecret);

        return kakaoFeignClient.getKakaoToken(kakaoTokenRequest);
    }

}
