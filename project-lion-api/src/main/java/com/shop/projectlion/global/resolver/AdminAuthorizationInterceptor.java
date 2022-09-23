package com.shop.projectlion.global.resolver;

import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.global.error.exception.AuthorizationException;
import com.shop.projectlion.global.error.exception.BusinessException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.shop.projectlion.global.error.exception.ErrorCode.NOT_ADMIN_ROLE_EXISTS;

@Component
@RequiredArgsConstructor
public class AdminAuthorizationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws BusinessException {
        // todo 인증 인터셉터에서 빈 값이나 널 값을 검증하고 있는데, 여기서도 한 번 더 검증을 해주는 것이 좋은가.

        String accessToken = request.getHeader("Authorization").split(" ")[1];
        Claims claims = tokenManager.getTokenClaims(accessToken);
        String tokenRole = (String) claims.get("role");

        if (Role.isAdmin(tokenRole)) {
            return true;
        }

        throw new AuthorizationException(NOT_ADMIN_ROLE_EXISTS);
    }

}
