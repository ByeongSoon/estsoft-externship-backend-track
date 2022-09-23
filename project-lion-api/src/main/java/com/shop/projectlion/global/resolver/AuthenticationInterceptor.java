package com.shop.projectlion.global.resolver;

import com.shop.projectlion.domain.jwt.constant.TokenType;
import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.global.error.exception.AuthorizationException;
import com.shop.projectlion.global.error.exception.BusinessException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

import static com.shop.projectlion.global.error.exception.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws BusinessException {
        String authorization = request.getHeader("Authorization");

        if (isNull(authorization)) {
            throw new AuthorizationException(NOT_EXISTS_AUTHORIZATION); // 비었을 때
        } else if (!"Bearer".equals(authorization.split(" ")[0])) {
            throw new AuthorizationException(NOT_VALID_BEARER_GRANT_TYPE); // Bearer이 아닐때
        }

        String accessToken = authorization.split(" ")[1];

        if (!tokenManager.validateToken(accessToken)) {
            throw new AuthorizationException(NOT_VALID_TOKEN); // 유효하지 않은 토큰
        }

        Claims claims = tokenManager.getTokenClaims(accessToken);
        Date expireTime = claims.getExpiration();

        String tokenType = claims.getSubject();
        if (!TokenType.isAccessToken(tokenType)) {
            throw new AuthorizationException(NOT_ACCESS_TOKEN_TYPE);
        }

         if (tokenManager.isTokenExpired(expireTime)) {
            throw new AuthorizationException(ACCESS_TOKEN_EXPIRED); // access 토큰 만료
        }

        return true;
    }

    private boolean isNull(String authorization) {
        return authorization == null || authorization.isEmpty();
    }

}
