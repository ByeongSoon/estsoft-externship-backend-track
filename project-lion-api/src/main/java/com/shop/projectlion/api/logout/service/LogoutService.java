package com.shop.projectlion.api.logout.service;

import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.BusinessException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.shop.projectlion.global.error.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LogoutService {

    private final TokenManager tokenManager;

    private final MemberService memberService;


    @Transactional
    public String logout(String authorization) {
        String accessToken = authorization.split(" ")[1];

        if (!tokenManager.validateToken(accessToken)) {
            throw new BusinessException(NOT_ACCESS_TOKEN_TYPE);
        }

        Claims claims = tokenManager.getTokenClaims(accessToken);
        Date accessTokenExpireTime = claims.getExpiration();

        if (tokenManager.isTokenExpired(accessTokenExpireTime)) {
            throw new BusinessException(ACCESS_TOKEN_EXPIRED);
        }

        String email = claims.getAudience();

        Member findMember = memberService.findMemberByEmail(email)
            .orElseThrow(() -> new BusinessException(MEMBER_NOT_EXISTS));

        findMember.expireRefreshToken();

        return "logout : success";
    }
}
