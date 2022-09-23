package com.shop.projectlion.api.login.client;

import com.shop.projectlion.api.login.dto.KakaoUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "https://kapi.kakao.com", name = "kakaoLoginFeignClient")
public interface LoginFeignClient {

    @PostMapping(value = "/v2/user/me")
    KakaoUserInfo getKakaoUserInfo(@RequestHeader("Authorization") String authorization);

}
