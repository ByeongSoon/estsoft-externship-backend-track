package com.shop.projectlion.domain.member.constant;

public enum Role {
    USER, ADMIN
    ;

    public static boolean isAdmin(String role) {
        return ADMIN.name().equals(role);
    }
}