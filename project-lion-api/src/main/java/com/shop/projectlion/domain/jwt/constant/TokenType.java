package com.shop.projectlion.domain.jwt.constant;

public enum TokenType {

    ACCESS, REFRESH;

    public static boolean isAccessToken(String tokenType) {
        return ACCESS.name().equals(tokenType);
    }

}