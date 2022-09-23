package com.shop.projectlion.api.token.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenDto {

    private String grantType;

    private String accessToken;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date accessTokenExpireTime;


    public static AccessTokenDto of(TokenDto tokenDto) {
        return AccessTokenDto.builder()
            .grantType("Bearer")
            .accessToken(tokenDto.getAccessToken())
            .accessTokenExpireTime(tokenDto.getAccessTokenExpireTime())
            .build();
    }

}
