package com.shop.projectlion.web.kakaotoken.client;

import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenRequestDto;
import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "kakaoFeignClient", url = "https://kauth.kakao.com")
public interface KakaoFeignClient {

    @PostMapping(value = "/oauth/token", consumes = "application/x-www-form-urlencoded;charset=utf-8")
    KakaoTokenResponseDto getKakaoToken(@SpringQueryMap KakaoTokenRequestDto request);

}
