package com.shop.projectlion.global.error;

import com.shop.projectlion.global.error.exception.BusinessException;
import org.springframework.stereotype.Component;

import static com.shop.projectlion.global.error.exception.ErrorCode.NOT_EXISTS_AUTHORIZATION;
import static com.shop.projectlion.global.error.exception.ErrorCode.NOT_VALID_BEARER_GRANT_TYPE;

@Component
public class HeaderValidator {

    public void check(String authorization) {
        if (authorization.isEmpty()) {
            throw new BusinessException(NOT_EXISTS_AUTHORIZATION);
        } else if (!"Bearer".equals(authorization.split(" ")[0])) {
            throw new BusinessException(NOT_VALID_BEARER_GRANT_TYPE);
        }
    }

}
