package com.shop.projectlion.web.kakaotoken.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class KakaoTokenRequestDto {

    private String grant_type;

    private String client_id;

    private String redirect_uri;

    private String code;

    private String client_secret;

    public static KakaoTokenRequestDto of(String code, String clientId, String clientSecret) {
        return KakaoTokenRequestDto.builder()
            .grant_type("authorization_code")
            .client_id(clientId)
            .redirect_uri("http://localhost:8080/auth/kakao/callback")
            .code(code)
            .client_secret(clientSecret)
            .build();
    }

}
