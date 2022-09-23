package com.shop.projectlion.api.token.service;

import com.shop.projectlion.api.token.dto.AccessTokenDto;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.exception.NotValidTokenException;
import com.shop.projectlion.domain.member.exception.TokenNotFoundException;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

import static com.shop.projectlion.global.error.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenReissueService {

    private final MemberService memberService;

    private final TokenManager tokenManager;

    public AccessTokenDto getAccessTokenDto(String authorization) throws BusinessException{
        String refreshToken = authorization.split(" ")[1];

        if (!tokenManager.validateToken(refreshToken)) {
            throw new NotValidTokenException(NOT_VALID_TOKEN);
        }

        Member findMember = memberService.findMemberByRefreshToken(refreshToken)
            .orElseThrow(() -> new TokenNotFoundException(REFRESH_TOKEN_NOT_FOUND));

        Date refreshDate = Timestamp.valueOf(findMember.getTokenExpirationTime());
        if (tokenManager.isTokenExpired(refreshDate)) {
            throw new BusinessException(REFRESH_TOKEN_EXPIRED);
        }

        TokenDto tokenDto = tokenManager.createTokenDto(findMember.getEmail(), findMember.getRole());
        return AccessTokenDto.of(tokenDto);
    }
}
